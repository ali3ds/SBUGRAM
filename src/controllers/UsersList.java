package controllers;

import Code.Main;
import controllers.Feed;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsersList extends Main implements Initializable {

    @FXML
    private VBox box_list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            dos.writeUTF("users_list");dos.flush();
            dos.writeUTF(String.valueOf(Feed.current_user_id));dos.flush();
            int count = Integer.parseInt(dis.readUTF());

            for(int i=0;i<count;i++){
                String id= dis.readUTF();
                String username=  dis.readUTF();
                String avatar = dis.readUTF();
                String is_following = dis.readUTF();
                add_user(id,username,avatar,is_following);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add_user(String id,String username,String avatar,String is_following) throws IOException {



        GridPane grid = new GridPane();
        grid.setMaxHeight(Region.USE_COMPUTED_SIZE);
        grid.setMinHeight(Region.USE_COMPUTED_SIZE);
        grid.setPrefWidth(300);
        grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
        grid.setPadding(new Insets(10, 0, 0, 0));


        Circle circle_avatar = new Circle();
        circle_avatar.setFill(new ImagePattern(new Image(avatar)));
        circle_avatar.setRadius(19);


        Text txt_username = new Text(username);
        txt_username.setFont(Font.font("Ubuntu", FontWeight.BOLD, 15));
        txt_username.setFontSmoothingType(FontSmoothingType.LCD);
        txt_username.setFill(Color.BLACK);

        Button following_button = new Button("follow");
        switch (is_following){
            case "yes":
                following_button.setText("unfollow");
                break;

            case "no":
                following_button.setText("follow");
                break;
        }



        following_button.setFont(Font.font("Ubuntu", FontWeight.BOLD, 13));
        following_button.setTextFill(Color.WHITE);

        following_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(following_button.getText().equals("follow"))following_button.setText("unfollow");
                else following_button.setText("follow");
                try {
                    dos.writeUTF("follow");dos.flush();
                    dos.writeUTF(String.valueOf(Feed.current_user_id));dos.flush();
                    dos.writeUTF(username);dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        GridPane pane1 = new GridPane();
        pane1.setPrefHeight(Region.USE_COMPUTED_SIZE);
        pane1.add(circle_avatar, 0, 0);
        pane1.add(txt_username, 1, 0);
        pane1.add(following_button,2,0);
        pane1.setHgap(8);
        pane1.setPadding(new Insets(5, 5, 5, 20));
        grid.add(pane1, 0, 0);

        grid.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    dos.writeUTF("user_profile");dos.flush();
                    dos.writeUTF(username);dos.flush();
                    Feed.profile_username = username;
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("../views/profile_user.fxml"));
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initStyle(StageStyle.DECORATED);
                        stage.setTitle("Profile");
                        Scene scene = new Scene(root, 400, 700);

                        stage.setScene(scene);
                        scene.getStylesheets().add("file:/Users/alinour/IdeaProjects/SBU%20GRAM/style/style.css");
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



        box_list.getChildren().add(grid);
    }
}
