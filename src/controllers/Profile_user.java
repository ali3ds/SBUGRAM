package controllers;

import Code.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Profile_user extends Main implements Initializable {

    int profile_id=0;

    @FXML
    private ScrollPane feed_scroll;

    @FXML
    private VBox posts_box;

    @FXML
    private GridPane menu_slide;

    @FXML
    private Text new_post_button;

    @FXML
    private ImageView back_button;

    @FXML
    private ImageView refresh_button;

    @FXML
    private Circle avatar_circle;

    @FXML
    private Text txt_username;

    @FXML
    private Text txt_name;

    @FXML
    private Text txt_name1;

    @FXML
    private Text txt_name11;

    @FXML
    private Text txt_name111;

    @FXML
    private Text txt_post;

    @FXML
    private Text txt_followers;

    @FXML
    private Text txt_followings;

    @FXML
    void back_click(MouseEvent event) {

    }

    @FXML
    void open_new_post(MouseEvent event) {

    }

    @FXML
    void refresh(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

            txt_username.setText(dis.readUTF());
            txt_name.setText(dis.readUTF()+" "+dis.readUTF());
            avatar_circle.setFill(new ImagePattern(new Image(dis.readUTF())));
            txt_post.setText(dis.readUTF());
            txt_followings.setText(dis.readUTF());
            txt_followers.setText(dis.readUTF());

        } catch (IOException e) {
            e.printStackTrace();
        }

        posts_box.getChildren().clear();

        Rectangle clip = new Rectangle(100, 200);


        try {
            dos.writeUTF("feed_profile_user");
            dos.flush();
            dos.writeUTF(Feed.profile_username);
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
                    String s_likes = dis.readUTF();
                    String[] likes = s_likes.split("\\s+");

                    add_post(the_username,avatar_path,img,caption,date,time,post_id,likes);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void add_post(String username, String avatar,String img,String caption,String date, String time,int post_id,String[] likes){
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

        ImageView img_like = new ImageView("file:/Users/alinour/IdeaProjects/SBU%20GRAM/pics/icons/liked.png");





        ImageView img_repost = new ImageView("file:/Users/alinour/IdeaProjects/SBU%20GRAM/pics/icons/repost.png");
        ImageView img_comment = new ImageView("file:/Users/alinour/IdeaProjects/SBU%20GRAM/pics/icons/comment.png");
        img_like.setFitWidth(22);
        img_repost.setFitWidth(24);
        img_comment.setFitWidth(25);
        img_comment.setPreserveRatio(true);
        img_repost.setPreserveRatio(true);
        img_like.setPreserveRatio(true);
        img_comment.setFitHeight(0);
        img_like.setFitHeight(0);
        img_like.setSmooth(true);
        img_repost.setFitHeight(0);

        img_like.setOpacity(0.7);
        img_repost.setOpacity(0.7);
        img_comment.setOpacity(0.7);

        Text txt_like = new Text(String.valueOf(likes.length-1));
        txt_like.setFont(Font.font("Ubuntu", FontWeight.LIGHT, 14));
        txt_like.setFontSmoothingType(FontSmoothingType.LCD);
        txt_like.setFill(Color.LIGHTCORAL);

        Text txt_repost = new Text("0");
        txt_repost.setFont(Font.font("Ubuntu", FontWeight.LIGHT, 14));
        txt_repost.setFontSmoothingType(FontSmoothingType.LCD);
        txt_repost.setFill(Color.DARKGRAY);

        Text txt_comment = new Text("12");
        txt_comment.setFont(Font.font("Ubuntu", FontWeight.LIGHT, 14));
        txt_comment.setFontSmoothingType(FontSmoothingType.LCD);
        txt_comment.setFill(Color.DARKGRAY);

        GridPane like_box = new GridPane();
        like_box.add(img_like,0,0);
        like_box.add(txt_like,1,0);
        like_box.setHgap(5);

        GridPane repost_box = new GridPane();
        repost_box.add(img_repost,0,0);
        repost_box.add(txt_repost,1,0);
        repost_box.setHgap(5);

        GridPane comment_box = new GridPane();
        comment_box.add(img_comment,0,0);
        comment_box.add(txt_comment,1,0);
        comment_box.setHgap(5);



        GridPane pane_buttons = new GridPane();
        pane_buttons.setMinWidth(400);
        pane_buttons.setMaxWidth(400);
        pane_buttons.setPrefHeight(Region.USE_COMPUTED_SIZE);
        pane_buttons.add(like_box, 0, 0);
        pane_buttons.add(repost_box, 1, 0);
        pane_buttons.add(comment_box, 2, 0);
        pane_buttons.setHgap(13);
        pane_buttons.setPadding(new Insets(5, 5, 5, 10));




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


        img_like.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                txt_like.setText("a");
                try {
                    dos.writeUTF("like");dos.flush();
                    dos.writeUTF(String.valueOf(Feed.current_user_id));dos.flush();
                    dos.writeUTF(String.valueOf(post_id));dos.flush();

                    String like_line = dis.readUTF();

                    String[] likes2 = like_line.split("\\s+");
                    txt_like.setText(String.valueOf(likes2.length-1));
                    System.out.println(like_line);
                    System.out.println(Arrays.toString(likes2));
                    System.out.println("ssss" + (likes2.length-1));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        posts_box.setPrefHeight(Region.USE_COMPUTED_SIZE);

        posts_box.getChildren().add(grid);
    }
}
