package controllers;

import Code.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Feed extends Main implements Initializable {

    @FXML
    public ImageView manu_button;
    public Circle circle1;
    public Text txt_test;
    public GridPane post_grid;
    public VBox posts_box;
    int current_user_id;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            current_user_id = Integer.parseInt(dis.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(current_user_id);
        Rectangle clip = new Rectangle(100, 200);


        try {
            dos.writeUTF("feed");
            dos.flush();
            dos.writeUTF(String.valueOf(current_user_id));
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    int count = 0;
        try {
             count = Integer.parseInt(dis.readUTF());

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(count>0){
            for(int i=1;i<=count;i++){
                try {
                    String img = dis.readUTF();
                    String caption = dis.readUTF();
                    String date = dis.readUTF();
                    String time = dis.readUTF();
                    String the_username = dis.readUTF();
                    String avatar_path = dis.readUTF();
                    int post_id = Integer.parseInt(dis.readUTF());

                    add_post(the_username,avatar_path,img,caption,date,time,post_id);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void add_post(String username, String avatar,String img,String caption,String date, String time,int post_id){
        GridPane grid = new GridPane();
        grid.setMaxHeight(Region.USE_COMPUTED_SIZE);
        grid.setMinHeight(Region.USE_COMPUTED_SIZE);
        grid.setPrefWidth(400);
        grid.setPrefHeight(413);
        grid.setPadding(new Insets(10, 0, 0, 0));


        Circle circle_avatar = new Circle();
        circle_avatar.setFill(new ImagePattern(new Image(avatar)));
        circle_avatar.setRadius(19);


        Text txt_username = new Text(username);
        txt_username.setFont(Font.font("Ubuntu", FontWeight.BOLD, 15));
        txt_username.setFontSmoothingType(FontSmoothingType.LCD);
        txt_username.setFill(Color.BLACK);

        GridPane pane1 = new GridPane();
        pane1.setMinWidth(400);
        pane1.setMaxWidth(400);
        pane1.setPrefHeight(Region.USE_COMPUTED_SIZE);
        pane1.add(circle_avatar, 0, 0);
        pane1.add(txt_username, 1, 0);
        pane1.setHgap(8);
        pane1.setPadding(new Insets(5, 5, 5, 20));
        grid.add(pane1, 0, 0);



        ImageView imag1 = new ImageView(img);
        imag1.setFitWidth(400);
        imag1.setFitHeight(0);
        imag1.setPreserveRatio(true);
        grid.add(imag1, 0, 1);



        Text txt_desc = new Text(caption);
        txt_desc.setFont(Font.font("Ubuntu", FontWeight.THIN, 14));
        txt_desc.setFontSmoothingType(FontSmoothingType.LCD);
        txt_desc.setFill(Color.BLACK);

        GridPane pane2 = new GridPane();
        pane2.setMinWidth(400);
        pane2.setMaxWidth(400);
        pane2.setPrefHeight(Region.USE_COMPUTED_SIZE);
        pane2.add(txt_desc, 0, 0);
        pane2.setHgap(2);
        pane2.setPadding(new Insets(5, 5, 5, 10));
        grid.add(pane2, 0, 2);

        ImageView img_like = new ImageView("file:/Users/alinour/IdeaProjects/SBU%20GRAM/pics/icons/like.png");

        ImageView img_repost = new ImageView("file:/Users/alinour/IdeaProjects/SBU%20GRAM/pics/icons/repost.png");
        ImageView img_comment = new ImageView("file:/Users/alinour/IdeaProjects/SBU%20GRAM/pics/icons/comment.png");
        img_like.setFitWidth(24);
        img_repost.setFitWidth(24);
        img_comment.setFitWidth(30);
        img_comment.setPreserveRatio(true);
        img_repost.setPreserveRatio(true);
        img_like.setPreserveRatio(true);
        img_comment.setFitHeight(0);
        img_like.setFitHeight(0);
        img_repost.setFitHeight(0);

        GridPane pane_buttons = new GridPane();
        pane_buttons.setMinWidth(400);
        pane_buttons.setMaxWidth(400);
        pane_buttons.setPrefHeight(Region.USE_COMPUTED_SIZE);
        pane_buttons.add(img_like, 0, 0);
        pane_buttons.add(img_repost, 1, 0);
        pane_buttons.add(img_comment, 2, 0);
        pane_buttons.setHgap(10);
        pane_buttons.setPadding(new Insets(5, 5, 5, 10));

        img_like.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                like(current_user_id,post_id);
            }
        });


        grid.add(pane_buttons, 0, 3);


        GridPane pane_post_time = new GridPane();
        Text post_date = new Text(date);
        Text post_time = new Text(time);
        post_date.setFont(Font.font("Ubuntu", FontWeight.THIN, 11));
        post_date.setFontSmoothingType(FontSmoothingType.LCD);
        post_date.setFill(Color.GRAY);
        post_time.setFont(Font.font("Ubuntu", FontWeight.THIN, 11));
        post_time.setFontSmoothingType(FontSmoothingType.LCD);
        post_time.setFill(Color.GRAY);
        pane_post_time.add(post_date,0,0);
        pane_post_time.add(post_time,1,0);
        pane_post_time.setHgap(10);
        pane_post_time.setPadding(new Insets(4,10,10,14));
        grid.add(pane_post_time,0,4);



        posts_box.setPrefHeight(Region.USE_COMPUTED_SIZE);

        posts_box.getChildren().add(grid);
    }


    public void like(int user_id,int post_id){

    }
}
