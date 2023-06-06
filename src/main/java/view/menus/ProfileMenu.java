package view.menus;

import controller.AppController;
import controller.MultiMenuFunctions;
import controller.viewControllers.ProfileMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.game.Color;
import model.user.User;
import view.enums.Result;
import view.enums.Command;
import view.enums.Message;

import java.net.URL;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu extends Application {
    private final AppController appController;
    private final ProfileMenuController profileMenuController;
    private final Scanner scanner;

    //avatar
    public Circle avatar;

    //labels
    @FXML
    private Label slogan;
    @FXML
    private Label email;
    @FXML
    private Label nickName;
    @FXML
    private Label username;


    public ProfileMenu() {
        this.appController = AppController.getInstance();
        this.scanner = new Scanner(System.in);
        this.profileMenuController = ProfileMenuController.getInstance();
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/profileMenu.fxml");
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(url));
        anchorPane.setPrefHeight(anchorPane.getPrefHeight() + 30);
        MultiMenuFunctions.setBackground(anchorPane, "registration-bg.jpg");

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        this.initializeLabels();
        this.initializeAvatar();
    }

    private void initializeAvatar() {
        avatar.setFill(new ImagePattern(User.getCurrentUser().getAvatar()));
    }

    private void initializeLabels() {
        //password is hashed, how can I show it???
        User.setCurrentUser(new User("mehrshad", "Mehr1283", "sdf",
                "epf@sg.cdsf", "ddf", "sdfdf", "slogan"));//for test
        username.setText(User.getCurrentUser().getUsername());
        nickName.setText(User.getCurrentUser().getNickName());
        slogan.setText(User.getCurrentUser().getSlogan());
        email.setText(User.getCurrentUser().getEmail());

        username.setTextFill(Color.GREEN.getColor());
        nickName.setTextFill(Color.RED.getColor());
        slogan.setTextFill(Color.YELLOW.getColor());
        email.setTextFill(Color.BLUE.getColor());
    }

    public Result run() {
        String command;
        Matcher matcher;

        while (true) {
            command = this.scanner.nextLine();
            if ((matcher = Command.CHANGE_USERNAME.getMatcher(command)) != null) {
                System.out.println(this.profileMenuController.changeUsername(matcher.group("username")));
            } else if ((matcher = Command.CHANGE_NICKNAME.getMatcher(command)) != null) {
                System.out.println(this.profileMenuController.changeNickName(matcher.group("nickname")));
            } else if ((matcher = Command.CHANGE_PASSWORD.getMatcher(command)) != null) {
                changePassword(matcher);
            } else if ((matcher = Command.CHANGE_EMAIL.getMatcher(command)) != null) {
                System.out.println(this.profileMenuController.changeEmail(matcher.group("email")));
            } else if ((matcher = Command.CHANGE_SLOGAN.getMatcher(command)) != null) {
                System.out.println(this.profileMenuController.changeSlogan(matcher.group("slogan")));
            } else if (command.matches(Command.REMOVE_SLOGAN.toString())) {
                System.out.println(this.profileMenuController.removeSlogan());
            } else if (command.matches(Command.DISPLAY_HIGHSCORE.toString())) {
                System.out.println(this.profileMenuController.showScore());
            } else if (command.matches(Command.DISPLAY_RANK.toString())) {
                System.out.println(this.profileMenuController.showRank());
            } else if (command.matches(Command.DISPLAY_SLOGAN.toString())) {
                System.out.println(this.profileMenuController.showSlogan());
            } else if (command.matches(Command.DISPLAY_PROFILE.toString())) {
                System.out.println(this.profileMenuController.showProfile());
            } else if (command.matches(Command.BACK.toString())) {
                System.out.println(Message.BACK_MAIN_MENU);
                return Result.ENTER_MAIN_MENU;
            } else
                System.out.println(Message.INVALID_COMMAND);
        }
    }

    private void changePassword(Matcher matcher) {
        String message = this.profileMenuController.changePassword(matcher.group("oldPassword"), matcher.group("newPassword"));
        System.out.println(message);

        if (Message.ENTER_PASSWORD_AGAIN.equals(message)) {
            String password;
            flag:
            do {
                password = this.scanner.nextLine();
                message = this.profileMenuController.checkPasswordAgain(password);
                System.out.println(message);
            } while (!Message.CHANGE_PASSWORD.equals(message) && !Message.CANCEL.equals(message));
        }
    }

    public void changeUsername(MouseEvent mouseEvent) {
    }

    public void changePassword(MouseEvent mouseEvent) {
    }

    public void changeNickname(MouseEvent mouseEvent) {
    }

    public void changeEmail(MouseEvent mouseEvent) {
    }

    public void changeSlogan(MouseEvent mouseEvent) {

    }
}
