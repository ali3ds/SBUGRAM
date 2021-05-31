package controllers;
import Code.Main;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class login extends Main {
    @FXML
    public Pane login_pane;
    public Pane signup_pane;
    public TextField txt_username;
    public TextField txt_password;

    public void login(ActionEvent actionEvent) throws IOException {
        String username = txt_username.getText();
        String password = txt_password.getText();

        dos.writeUTF("login");
        dos.flush();
        dos.writeUTF(username);
        dos.flush();
        dos.writeUTF(password);
        dos.flush();

        switch (dis.readUTF()){
            case "right":
                Alert alert= new Alert(Alert.AlertType.INFORMATION,"Logged in successfully");
                alert.show();
            break;
            case "wrong":
                 alert= new Alert(Alert.AlertType.ERROR,"username or password is wrong");
                alert.show();
        }


        socket.close();
    }

    public void open_signup(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../views/signup.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Sign Up");
            stage.setScene(new Scene(root, 500, 615));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
