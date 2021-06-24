package Code;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.jfoenix.controls.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Main extends Application {


    public static ObjectInputStream dis;
    public static ObjectOutputStream dos;
    public static Socket socket;
    @Override
    public void start(Stage primaryStage) throws Exception{

        //////client
    
        String serverAdd = "127.0.0.1";
        int port=8080;

         socket = new Socket(serverAdd, port);
        dos = new ObjectOutputStream(socket.getOutputStream());
         dis = new ObjectInputStream(socket.getInputStream());


        /////cline*****

        primaryStage.getIcons().add(new Image("file:/Users/alinour/IdeaProjects/SBU%20GRAM/pics/87390.png"));

        Parent root = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
        primaryStage.setTitle("Hello World");

        Scene scene = new Scene(root, 400, 600);

        primaryStage.setScene(scene);
        scene.getStylesheets().add("file:/Users/alinour/IdeaProjects/SBU%20GRAM/style/style.css");

        primaryStage.getIcons().add(new Image("file:/Users/alinour/IdeaProjects/SBU%20GRAM/pics/87390.png"));

        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
