package controllers;
import Code.Main;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.Scanner;

public class signup extends Main implements Initializable {
    @FXML
    public Pane signup_pane;
    public TextField txt_username_signup;
    public TextField txt_password_signup;
    public TextField txt_firstname;
    public TextField txt_lastname;
    public TextField txt_city;
    public TextField txt_country;
    public ComboBox combo_year;
    public ComboBox combo_month;
    public ComboBox combo_day;
    public Circle img_avatar;
    @FXML
    public Button button_avatar;

    public String avatar_path;

    public void signup_done(ActionEvent actionEvent) throws IOException {

        dos.writeUTF("check_username");
        dos.flush();
        dos.writeUTF(txt_username_signup.getText());
        dos.flush();

       if(dis.readUTF().equals("ok")){

       }

        List<String> list = new ArrayList<>();
        list.add(txt_username_signup.getText());
        list.add(txt_password_signup.getText());
        list.add(txt_firstname.getText());
        list.add(txt_lastname.getText());
        list.add(txt_country.getText());
        list.add(txt_city.getText());
        list.add(String.valueOf(combo_day.getValue()));
        list.add(String.valueOf(combo_month.getValue()));
        list.add(String.valueOf(combo_year.getValue()));
        list.add(avatar_path);



        dos.writeUTF("signed_up");
        dos.flush();
        dos.writeObject(list);
        dos.flush();
       Alert alert= new Alert(Alert.AlertType.INFORMATION,"Signed up successfully");
        alert.show();



    Stage s = (Stage)txt_username_signup.getScene().getWindow();
        s.close();

    }

    @FXML
    void avatar_choose(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Avatar");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            Image map = new Image("file:"+selectedFile.getAbsolutePath());
            avatar_path="file:"+selectedFile.getAbsolutePath();
            ImagePattern pattern = new ImagePattern(map);
            //Setting the pattern
            img_avatar.setFill(pattern);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image map = new Image("file:/Users/alinour/IdeaProjects/SBU%20GRAM/pics/user.png");
        ImagePattern pattern = new ImagePattern(map);
        //Setting the pattern
        img_avatar.setFill(pattern);


        int count=2012;

        for(int i=0;i<=32;i++){
            combo_year.getItems().addAll(String.valueOf(count));
            count--;
        }

        for(int i=1;i<=30;i++){
            combo_day.getItems().addAll(String.valueOf(i));
        }
        combo_month.getItems().addAll("January","February","March","April","May","June","July","August","September","October","November","December");

    }
}
