package view.menus;

import controller.ProfileMenuController;
import view.enums.Result;
import view.enums.Command;
import view.enums.Message;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {

    private final ProfileMenuController controller;
    private final Scanner scanner;

    public ProfileMenu(Scanner scanner, ProfileMenuController profileMenuController) {
        this.scanner = scanner;
        this.controller = profileMenuController;
    }

    public Result run() {
        String command;
        Matcher matcher;

        while (true) {
            command = this.scanner.nextLine();
            if ((matcher = Command.CHANGE_USERNAME.getMatcher(command)) != null) {
                System.out.println(this.controller.changeUsername(matcher.group("username")));
            } else if ((matcher = Command.CHANGE_NICKNAME.getMatcher(command)) != null) {
                System.out.println(this.controller.changeNickName(matcher.group("nickname")));
            } else if ((matcher = Command.CHANGE_PASSWORD.getMatcher(command)) != null) {
                changePassword(matcher);
            } else if ((matcher = Command.CHANGE_EMAIL.getMatcher(command)) != null) {
                System.out.println(this.controller.changeEmail(matcher.group("email")));
            } else if ((matcher = Command.CHANGE_SLOGAN.getMatcher(command)) != null) {
                System.out.println(this.controller.changeSlogan(matcher.group("slogan")));
            } else if (command.matches(Command.REMOVE_SLOGAN.toString())) {
                System.out.println(this.controller.removeSlogan());
            } else if (command.matches(Command.DISPLAY_HIGHSCORE.toString())) {
                System.out.println(this.controller.showScore());
            } else if (command.matches(Command.DISPLAY_RANK.toString())) {
                System.out.println(this.controller.showRank());
            } else if (command.matches(Command.DISPLAY_SLOGAN.toString())) {
                System.out.println(this.controller.showSlogan());
            } else if (command.matches(Command.DISPLAY_PROFILE.toString())) {
                System.out.println(this.controller.showProfile());
            } else if (command.matches(Command.BACK.toString())) {
                System.out.println(Message.BACK_MAIN_MENU);
                return Result.ENTER_MAIN_MENU;
            } else
                System.out.println(Message.INVALID_COMMAND);
        }
    }

    private void changePassword(Matcher matcher) {
        Message message = this.controller.changePassword(matcher.group("oldPassword"),matcher.group("newPassword"));
        System.out.println(message);

        if (message.equals(Message.ENTER_PASSWORD_AGAIN)) {
            String password;
            flag:
            while (true) {
                password = this.scanner.nextLine();
                message = this.controller.checkPasswordAgain(password);
                System.out.println(message);
                switch (message) {
                    case CHANGE_PASSWORD, CANCEL -> {
                        break flag;
                    }
                }
            }
        }
    }
}
