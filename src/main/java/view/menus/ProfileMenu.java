package view.menus;

import controller.AppController;
import controller.viewControllers.ProfileMenuController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.enums.Result;
import view.enums.Command;
import view.enums.Message;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu extends Application {
    private final AppController appController;
    private final ProfileMenuController profileMenuController;
    private final Scanner scanner;

    public ProfileMenu() {
        this.appController = AppController.getInstance();
        this.scanner = new Scanner(System.in);
        this.profileMenuController = ProfileMenuController.getInstance();
    }

    @Override
    public void start(Stage stage) throws Exception {

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
}
