package controllers;

import Code.Main;
import controllers.Feed;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class NewPost extends Main {

    @FXML
    private ImageView img_post;

    @FXML
    private Button button_image;

    @FXML
    private TextArea txt_desc;

    @FXML
    private Button button_post;

    String post_image_path;

    @FXML
    void choose_image(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            Image map = new Image("file:"+selectedFile.getAbsolutePath());
            img_post.setImage(map);
            post_image_path="file:"+selectedFile.getAbsolutePath();
        }
    }

    @FXML
    void post(MouseEvent event) throws IOException {
        dos.writeUTF("new post");dos.flush();
        dos.writeUTF(String.valueOf(Feed.current_user_id));dos.flush();
        dos.writeUTF(post_image_path);dos.flush();
        dos.writeUTF(txt_desc.getText());dos.flush();


        Alert alert= new Alert(Alert.AlertType.INFORMATION,"post was made successfully");
        alert.show();


        Stage s = (Stage)button_image.getScene().getWindow();
        s.close();

    }

}
