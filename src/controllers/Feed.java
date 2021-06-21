package controllers;

import Code.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class Feed extends Main implements Initializable {

    @FXML
    public ImageView manu_button;
    public ImageView avatar_post1;
    public Circle circle1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Rectangle clip = new Rectangle(100, 200);

        avatar_post1.setFitWidth(100);
        avatar_post1.setFitHeight(100);
    }
}
