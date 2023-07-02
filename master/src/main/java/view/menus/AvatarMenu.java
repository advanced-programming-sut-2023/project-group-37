package view.menus;

import controller.AppController;
import controller.MultiMenuFunctions;
import controller.viewControllers.ProfileMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.user.User;
import view.enums.Result;

import java.net.URL;
import java.util.Objects;

public class AvatarMenu extends Application {
    private final AppController appController;
    @FXML
    private Circle currentAvatar;
    @FXML
    private Circle avatar1;
    @FXML
    private Circle avatar2;
    @FXML
    private Circle avatar3;
    @FXML
    private Circle avatar4;
    @FXML
    private Circle avatar5;
    @FXML
    private Circle avatar6;


    public AvatarMenu() {
        this.appController = AppController.getInstance();
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/AvatarMenu.fxml");
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(url));
        anchorPane.setPrefHeight(anchorPane.getPrefHeight() + 30);
        MultiMenuFunctions.setBackground(anchorPane, "registration-bg.jpg");

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        this.initializeAvatars();
    }

    private void initializeAvatars() {
        currentAvatar.setFill(new ImagePattern(User.getCurrentUser().getAvatar()));
        avatar1.setFill(new ImagePattern(new Image(ProfileMenuController.getAllAvatarImages().get(0).getAbsolutePath())));
        avatar2.setFill(new ImagePattern(new Image(ProfileMenuController.getAllAvatarImages().get(1).getAbsolutePath())));
        avatar3.setFill(new ImagePattern(new Image(ProfileMenuController.getAllAvatarImages().get(2).getAbsolutePath())));
        avatar4.setFill(new ImagePattern(new Image(ProfileMenuController.getAllAvatarImages().get(3).getAbsolutePath())));
        avatar5.setFill(new ImagePattern(new Image(ProfileMenuController.getAllAvatarImages().get(4).getAbsolutePath())));
        avatar6.setFill(new ImagePattern(new Image(ProfileMenuController.getAllAvatarImages().get(5).getAbsolutePath())));
    }

    public void select1(MouseEvent mouseEvent) {
        User.getCurrentUser().setAvatarNumber(0);
        currentAvatar.setFill(new ImagePattern(User.getCurrentUser().getAvatar()));
    }

    public void select2(MouseEvent mouseEvent) {
        User.getCurrentUser().setAvatarNumber(1);
        currentAvatar.setFill(new ImagePattern(User.getCurrentUser().getAvatar()));
    }

    public void select3(MouseEvent mouseEvent) {
        User.getCurrentUser().setAvatarNumber(2);
        currentAvatar.setFill(new ImagePattern(User.getCurrentUser().getAvatar()));
    }

    public void select4(MouseEvent mouseEvent) {
        User.getCurrentUser().setAvatarNumber(3);
        currentAvatar.setFill(new ImagePattern(User.getCurrentUser().getAvatar()));
    }

    public void select5(MouseEvent mouseEvent) {
        User.getCurrentUser().setAvatarNumber(4);
        currentAvatar.setFill(new ImagePattern(User.getCurrentUser().getAvatar()));
    }

    public void select6(MouseEvent mouseEvent) {
        User.getCurrentUser().setAvatarNumber(5);
        currentAvatar.setFill(new ImagePattern(User.getCurrentUser().getAvatar()));
    }

    public void enterProfileMenu(MouseEvent mouseEvent) throws Exception {
        appController.runMenu(Result.ENTER_PROFILE_MENU);
    }
}
