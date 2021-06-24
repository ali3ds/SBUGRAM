package controllers;
import Code.Main;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.jfoenix.controls.*;


import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.Scanner;


public class login extends Main implements Initializable {
    @FXML
    public Pane login_pane;
    public Pane signup_pane;
    public TextField txt_username;
    public PasswordField txt_password;
    public TextField txt_password_unmask;
    public javafx.scene.image.ImageView img_mask;
    public javafx.scene.image.ImageView img_unmask;




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

                Parent root = FXMLLoader.load(getClass().getResource("../views/feed.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("Feed");
                stage.setScene(new Scene(root, 400, 760));
                stage.getIcons().add(new Image("file:/Users/alinour/IdeaProjects/SBU%20GRAM/pics/87390.png"));

                stage.show();


                Alert alert= new Alert(Alert.AlertType.INFORMATION,"Logged in successfully");
                alert.show();

               // Stage s = (Stage)txt_password.getScene().getWindow();
                //s.close();

            break;
            case "wrong":
                 alert= new Alert(Alert.AlertType.ERROR,"username or password is wrong");
                alert.show();
        }


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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void password_unmask(MouseEvent mouseEvent) {
        img_mask.setVisible(false);
        img_unmask.setVisible(true);
        txt_password.setVisible(false);
        txt_password_unmask.setText(txt_password.getText());
        txt_password_unmask.setVisible(true);

    }

    public void password_mask(MouseEvent mouseEvent) {
        img_mask.setVisible(true);
        img_unmask.setVisible(false);
        txt_password.setText(txt_password_unmask.getText());
        txt_password_unmask.setVisible(false);
        txt_password.setVisible(true);
    }
}
