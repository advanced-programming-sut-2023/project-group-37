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

    {
        this.controller = new ProfileMenuController();
    }

    public ProfileMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public Result run() {
        String command;
        Matcher matcher;

        while (true) {
            command = this.scanner.nextLine();
            if ((matcher = Command.CHANGE_USERNAME.getMatcher(command)) != null) {
                System.out.println(this.controller.changeUsername(matcher));
            } else if ((matcher = Command.CHANGE_NICKNAME.getMatcher(command)) != null) {
                System.out.println(this.controller.changeNickName(matcher));
            } else if ((matcher = Command.CHANGE_PASSWORD.getMatcher(command)) != null) {
                changePassword(matcher);
            } else if ((matcher = Command.CHANGE_EMAIL.getMatcher(command)) != null) {
                System.out.println(this.controller.changeEmail(matcher));
            } else if ((matcher = Command.CHANGE_SLOGAN.getMatcher(command)) != null) {
                System.out.println(this.controller.changeSlogan(matcher));
            } else if ((matcher = Command.REMOVE_SLOGAN.getMatcher(command)) != null) {
                System.out.println(this.controller.removeSlogan(matcher));
            } else if (Command.DISPLAY_HIGHSCORE.getMatcher(command) != null) {
                System.out.println(this.controller.showScore());
            } else if (Command.DISPLAY_RANK.getMatcher(command) != null) {
                System.out.println(this.controller.showRank());
            } else if (Command.DISPLAY_SLOGAN.getMatcher(command) != null) {
                System.out.println(this.controller.showSlogan());
            } else if (Command.DISPLAY_PROFILE.getMatcher(command) != null) {
                System.out.println(this.controller.showProfile());
                //TODO: hardcode:
            } else if (command.matches("\\s*enter\\s+main\\s+menu\\s*")) {
                return Result.ENTER_MAIN_MENU;
            } else
                System.out.println("Invalid Command!");
        }
    }

    private void changePassword(Matcher matcher) {
        Message message = this.controller.changePassword(matcher);
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
