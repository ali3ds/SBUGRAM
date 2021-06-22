package Code;
import javafx.fxml.Initializable;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
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


    public void new_user(List<String> list){
        Users_data.put(signeUpUsers.get(),new User(list));
        System.out.println(list.get(0) + " registered");
        signeUpUsers.incrementAndGet();
        System.out.println("New user: "+Arrays.toString(list.toArray()));
    }

    public void login(String user,String pass) throws IOException {


        for(int i = 0;i<signeUpUsers.get();i++){
            User u = Users_data.get(i);
            if(u.getUsername().equals(user) && u.getPassword().equals(pass)){
                dos.writeUTF("right");
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
                Map<String,String> userdata = new HashMap<>();
                userdata.put("id",String.valueOf(userid));
                for(int i=1;i<=11;i++){
                    userdata.put(scanner.next(),scanner.next());
                }
                User usernew = new User(userdata);
                System.out.println(usernew.toString());
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

