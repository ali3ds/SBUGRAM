package Code;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

class ClientHandler extends Thread{
    final int id;
    final ObjectInputStream dis;
    final ObjectOutputStream dos;
    final Map<Integer,User> Users_data;
    final AtomicInteger usersCount;
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

}


public class Server {
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
}

