package Code;
import javafx.fxml.Initializable;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

class ClientHandler extends Thread{
    final int id;
    final ObjectInputStream dis;
    final ObjectOutputStream dos;
    static Map<Integer,User> Users_data;
    public static AtomicInteger usersCount;
    final AtomicInteger signeUpUsers= new AtomicInteger(0);

    public ClientHandler(Socket socket, int id, Map<Integer,User> Users_data, AtomicInteger usersCount) throws Exception {

        this.id = id;

        this.dis = new ObjectInputStream(
                socket.getInputStream());

        this.dos = new ObjectOutputStream(
                socket .getOutputStream());
        this.Users_data = Users_data;

        this.usersCount = usersCount;

    }

    public void run() {
        Load_UserDatabase();
        signeUpUsers.set(usersCount.get());
        try {
            while (true) {
                String command = dis.readUTF();
                switch (command.trim()){
                    case "signed_up":
                        System.out.println("User "+id+" Signed up");
                        List<String> list =(List<String>) dis.readObject();
                        new_user(list);
                        break;
                    case "login":
                        String user = dis.readUTF();
                        String pass = dis.readUTF();
                        System.out.println("login request user:"+user+"    pass:"+pass);
                        login(user,pass);
                        break;
                    case "test":
                        System.out.println("ss");
                        break;

                    case "check_username":
                        String username = dis.readUTF().trim();
                        checkUsername(username);
                       break;

                    case "feed":
                        String s = dis.readUTF();
                        send_feed(s);
                        break;

                    case "like":
                        String user_id = dis.readUTF();
                        String post_id = dis.readUTF();
                        like(user_id,post_id);
                        break;

                    case "new post":
                        new_post();
                        break;

                    case "my_profile":
                        send_my_profile(dis.readUTF());
                        break;

                    case "feed_profile":
                        String j = dis.readUTF();
                        send_feed_profile(j);
                        break;

                }

                dos.flush();
            }
        } catch (IOException e) {
            System.out.println("client " + this.id + " disconnected");
            this.usersCount.decrementAndGet();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void send_feed_profile(String user) throws IOException{
        int user_id = Integer.parseInt(user);



        File followings = new File("/Users/alinour/IdeaProjects/SBU GRAM/data/followings.txt");
        List<Integer> following_list = new ArrayList<Integer>();
        Scanner scanner = new Scanner(followings);
        while(scanner.hasNextLine()){
            if(user_id==Integer.parseInt(scanner.next().trim())){
                following_list.add(Integer.parseInt(scanner.next().trim()));
            }else{scanner.next();}
        }

        System.out.println("following "+following_list.toString());
        File database_posts = new File("/Users/alinour/IdeaProjects/SBU GRAM/data/database_posts.txt");
        scanner=new Scanner(database_posts);
        int count = 0;
        while (scanner.hasNextLine()){
            int the_id = Integer.parseInt(scanner.next());

                if(user_id==the_id){
                    count++;
                }

            scanner.nextLine();
            scanner.nextLine();
            scanner.nextLine();
            scanner.nextLine();
            scanner.nextLine();
            scanner.nextLine();
        }

        dos.writeUTF(String.valueOf(count));
        dos.flush();

        scanner=new Scanner(database_posts);
        while (scanner.hasNextLine()){
            boolean found=false;
            int the_id = Integer.parseInt(scanner.next());
                if(user_id==the_id){
                    String post_id="0";
                    found=true;
                    scanner.nextLine();
                    if(scanner.next().trim().equals("id")){
                        post_id=scanner.next();
                        scanner.nextLine();
                    }
                    String img = scanner.nextLine();
                    dos.writeUTF(img);dos.flush();
                    String caption = scanner.nextLine();
                    dos.writeUTF(caption);dos.flush();
                    String date = scanner.next();
                    dos.writeUTF(date);dos.flush();
                    String time = scanner.next();
                    dos.writeUTF(time);dos.flush();
                    String the_user = Users_data.get(the_id).username;
                    dos.writeUTF(the_user);dos.flush();
                    String avatar = Users_data.get(the_id).avatar_path;
                    dos.writeUTF(avatar);dos.flush();
                    dos.writeUTF(post_id);dos.flush();

                    scanner.nextLine();
                    String likes = scanner.nextLine();
                    dos.writeUTF(likes);dos.flush();


                }

            if(!found){
                scanner.nextLine();
                scanner.nextLine();
                scanner.nextLine();
                scanner.nextLine();
                scanner.nextLine();
                scanner.nextLine();
            }
        }



    }

    public void send_my_profile(String id) throws IOException{
        Load_UserDatabase();
        User me = Users_data.get(Integer.parseInt(id));
        dos.writeUTF(me.username);dos.flush();
        dos.writeUTF(me.first_name);dos.flush();
        dos.writeUTF(me.last_name);dos.flush();
        dos.writeUTF(me.avatar_path);dos.flush();

        File database_posts = new File("/Users/alinour/IdeaProjects/SBU GRAM/data/database_posts.txt");
        Scanner scanner = new Scanner(database_posts);
        int posts_count=0;
        while(scanner.hasNextLine()){
            if(scanner.nextLine().trim().equals(id)){
                posts_count++;
            }
        }

        dos.writeUTF(String.valueOf(posts_count));dos.flush();

        File followings = new File("/Users/alinour/IdeaProjects/SBU GRAM/data/followings.txt");
         scanner = new Scanner(followings);
         int followings_count=0;
         int followers_count=0;
         while(scanner.hasNextLine()){
             String line = scanner.nextLine();
             String[] line_array = line.split("\\s+");
             if(line_array[0].equals(id))followings_count++;
             if(line_array[1].equals(id))followers_count++;

         }
        dos.writeUTF(String.valueOf(followings_count));dos.flush();
        dos.writeUTF(String.valueOf(followers_count));dos.flush();

    }

    public void new_post() throws IOException {
        int user_posting_id = Integer.parseInt(dis.readUTF());
        String post_path = dis.readUTF();
        String desc= dis.readUTF();
        File database_posts = new File("/Users/alinour/IdeaProjects/SBU GRAM/data/database_posts.txt");
        Scanner scanner = new Scanner(database_posts);
        int posts_count=0;
        while(scanner.hasNextLine()){
            if(scanner.nextLine().contains("id")){
                posts_count++;
            }
        }


        System.out.println("new post: ");
        System.out.println(user_posting_id);
        System.out.println(posts_count);
        System.out.println(post_path);
        System.out.println(desc);

        FileWriter ff = new FileWriter("/Users/alinour/IdeaProjects/SBU GRAM/data/database_posts.txt",true);
        BufferedWriter posts_database = new BufferedWriter(ff);
        posts_database.write("\n");
        posts_database.write('\n');
        posts_database.write(String.valueOf(user_posting_id));
        posts_database.write('\n');
        posts_database.write("id "+String.valueOf(posts_count));
        posts_database.write('\n');
        posts_database.write(post_path);
        posts_database.write('\n');
        posts_database.write(desc);
        posts_database.write('\n');
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        posts_database.write(formatter.format(date));
        posts_database.write('\n');
        posts_database.write("likes");
        posts_database.close();
        ff.close();



    }

    public void like(String user_id, String post_id) throws IOException {
        String like_line = "";
        File database_posts = new File("/Users/alinour/IdeaProjects/SBU GRAM/data/database_posts.txt");
        Scanner scanner=new Scanner(database_posts);
        boolean hasliked = false;
        while(scanner.hasNextLine()){
            if(scanner.nextLine().contains("id "+post_id)){
                scanner.nextLine();
                scanner.nextLine();
                scanner.nextLine();
                String likes = scanner.nextLine();
                String[] likes_array = likes.split("\\s+");
                for(int i=0;i<likes_array.length;i++){
                    if(likes_array[i].equals(user_id)){
                        Path path = Paths.get(database_posts.getPath());
                        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
                        for(int j=0;j<lines.size();j++){
                            String s = lines.get(j);
                            if(s.contains("id "+post_id)){
                                 like_line = lines.get(j+4);
                                int index = like_line.indexOf(user_id);
                                like_line=like_line.substring(0,index-1)+like_line.substring(index+1,like_line.length());
                                lines.set(j+4, like_line);
                                System.out.println("disliked: "+like_line);
                                Files.write(path, lines, StandardCharsets.UTF_8);
                                break;
                            }
                        }
                        hasliked=true;
                        break;
                    }
                }

                if(!hasliked){
                    Path path = Paths.get(database_posts.getPath());
                    List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
                    for(int i=0;i<lines.size();i++){
                        String s = lines.get(i);
                        if(s.contains("id "+post_id)){
                            like_line = lines.get(i+4);
                            like_line=like_line+" "+user_id;
                            lines.set(i+4, like_line);
                            System.out.println("liked: "+like_line);
                            Files.write(path, lines, StandardCharsets.UTF_8);
                            break;
                        }
                    }
                }

                break;
            }
        }
        dos.writeUTF(like_line);dos.flush();
    }



    public void send_feed(String user) throws IOException {

        int user_id = Integer.parseInt(user);



        File followings = new File("/Users/alinour/IdeaProjects/SBU GRAM/data/followings.txt");
       List<Integer> following_list = new ArrayList<Integer>();
        Scanner scanner = new Scanner(followings);
        while(scanner.hasNextLine()){
            if(user_id==Integer.parseInt(scanner.next().trim())){
                following_list.add(Integer.parseInt(scanner.next().trim()));
            }else{scanner.next();}
        }

        System.out.println("following "+following_list.toString());
        File database_posts = new File("/Users/alinour/IdeaProjects/SBU GRAM/data/database_posts.txt");
        scanner=new Scanner(database_posts);
        int count = 0;
        while (scanner.hasNextLine()){
            int the_id = Integer.parseInt(scanner.next());
            for(int i:following_list){
                if(i==the_id){
                    count++;
                }
            }
            scanner.nextLine();
            scanner.nextLine();
            scanner.nextLine();
            scanner.nextLine();
            scanner.nextLine();
            scanner.nextLine();
        }

        dos.writeUTF(String.valueOf(count));
        dos.flush();

        scanner=new Scanner(database_posts);
        while (scanner.hasNextLine()){
            boolean found=false;
            int the_id = Integer.parseInt(scanner.next());
            for(int i:following_list){
                if(i==the_id){
                    String post_id="0";
                    found=true;
                    scanner.nextLine();
                    if(scanner.next().trim().equals("id")){
                        post_id=scanner.next();
                        scanner.nextLine();
                    }
                    String img = scanner.nextLine();
                    dos.writeUTF(img);dos.flush();
                    String caption = scanner.nextLine();
                    dos.writeUTF(caption);dos.flush();
                    String date = scanner.next();
                    dos.writeUTF(date);dos.flush();
                    String time = scanner.next();
                    dos.writeUTF(time);dos.flush();
                    String the_user = Users_data.get(the_id).username;
                    dos.writeUTF(the_user);dos.flush();
                    String avatar = Users_data.get(the_id).avatar_path;
                    dos.writeUTF(avatar);dos.flush();
                    dos.writeUTF(post_id);dos.flush();

                    scanner.nextLine();
                        String likes = scanner.nextLine();
                        dos.writeUTF(likes);dos.flush();


                }
            }
            if(!found){
                scanner.nextLine();
                scanner.nextLine();
                scanner.nextLine();
                scanner.nextLine();
                scanner.nextLine();
                scanner.nextLine();
            }
        }







    }

    private void checkUsername(String username) throws IOException {
        for(int i = 0;i<signeUpUsers.get();i++){
            User u = Users_data.get(i);
            if(u.getUsername().equals(username)){
                dos.writeUTF("used");
                dos.flush();
                return;
            }
        }
        dos.writeUTF("ok");
        dos.flush();
    }


    public void new_user(List<String> list) throws IOException {
        signeUpUsers.incrementAndGet();
        Users_data.put(signeUpUsers.get(),new User(list));
        FileWriter fw = new FileWriter("/Users/alinour/IdeaProjects/SBU GRAM/data/database_users.txt",true);
        BufferedWriter users_database = new BufferedWriter(fw);
        String s =String.valueOf( signeUpUsers.get());
        users_database.write("\n");
        users_database.write('\n');
        users_database.write(s);
        users_database.write('\n'+"username "+Users_data.get(signeUpUsers.get()).username);
        users_database.write('\n'+"password "+Users_data.get(signeUpUsers.get()).password);
        users_database.write('\n'+"first "+Users_data.get(signeUpUsers.get()).first_name);
        users_database.write('\n'+"last "+Users_data.get(signeUpUsers.get()).last_name);
        users_database.write('\n'+"Country "+Users_data.get(signeUpUsers.get()).country);
        users_database.write('\n'+"city "+Users_data.get(signeUpUsers.get()).city);
        users_database.write('\n'+"year "+Users_data.get(signeUpUsers.get()).year);
        users_database.write('\n'+"month "+Users_data.get(signeUpUsers.get()).month);
        users_database.write('\n'+"day "+Users_data.get(signeUpUsers.get()).day);
        users_database.write('\n'+"avatar "+Users_data.get(signeUpUsers.get()).avatar_path);
        users_database.write('\n'+"following 0");
        users_database.close();
        System.out.println(list.get(0) + " registered");
        System.out.println("New user: "+Arrays.toString(list.toArray()));

    }

    public void login(String user,String pass) throws IOException {


        for(int i = 0;i<signeUpUsers.get();i++){
            User u = Users_data.get(i);
            if(u.getUsername().equals(user) && u.getPassword().equals(pass)){
                dos.writeUTF("right");
                System.out.println("accepted "+u.id);
                System.out.println(u.username+" just logged in");
                dos.writeUTF(String.valueOf(u.id));
                dos.flush();
                return;
            }
        }
        dos.writeUTF("wrong");
    }

    public static void Load_UserDatabase(){
        Users_data = new ConcurrentHashMap<>();
        File users_database = new File("/Users/alinour/IdeaProjects/SBU GRAM/data/database_users.txt");
        try {
            Scanner scanner = new Scanner(users_database);
            switch (scanner.next().trim()){
                case "users_count":
                    usersCount.set(Integer.parseInt(scanner.next()));
                    break; }
            while(scanner.hasNextLine()){
                int userid = Integer.parseInt(scanner.next());
                System.out.println("test: "+userid);
                Map<String,String> userdata = new HashMap<>();
                userdata.put("id",String.valueOf(userid));
                for(int i=1;i<=11;i++){
                    userdata.put(scanner.next(),scanner.next());
                }
                User usernew = new User(userdata);
                //System.out.println(usernew.toString());
                Users_data.put(userid,usernew);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}


public class Server implements Initializable {
    public static int port;
    public static AtomicInteger usersCount;
    public static final Map<Integer,User> data = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
        //port = Integer.parseInt(args[0]);
        port=8080;
        //data = new ConcurrentHashMap<>();
        usersCount = new AtomicInteger(0);

        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            int id = usersCount.get();
            System.out.println("User "+usersCount+" Joined.");

            new ClientHandler(clientSocket, id, data, usersCount).start();
            usersCount.incrementAndGet();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



}

