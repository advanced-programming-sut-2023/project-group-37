package view.menus;

import controller.LoginMenuController;
import controller.MultiMenuFunctions;
import view.enums.Result;
import view.enums.Command;
import view.enums.Message;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {
    private final LoginMenuController controller;
    private final Scanner scanner;
    private String command;
    private String message;

    public LoginMenu(Scanner scanner, LoginMenuController loginMenuController) {
        this.scanner = scanner;
        this.controller = loginMenuController;
    }

    public Result run() {
        Matcher matcher;

        while (true) {
            this.command = this.scanner.nextLine();

            if ((matcher = Command.LOGIN.getMatcher(this.command)) != null) {
                if (login(matcher))
                    return Result.LOGGED_IN;
            } else if ((matcher = Command.FORGOT_PASSWORD.getMatcher(this.command)) != null)
                forgotPassword(matcher);
            else if (Command.ENTER_REGISTER_MENU.getMatcher(this.command) != null) {
                System.out.println(Message.ENTERED_REGISTER_MENU);
                return Result.ENTER_REGISTER_MENU;
            } else if (Command.EXIT.getMatcher(this.command) != null)
                return Result.EXIT;
            else
                System.out.println(Message.INVALID_COMMAND);
        }
    }

    private boolean login(Matcher matcher) {
        this.message = this.controller.login(matcher);
        System.out.println(this.message);

        if (Message.INCORRECT_PASSWORD.equals(message)) {
            System.out.println("You can try again in " + controller.getDelayTime() + " seconds!");
            MultiMenuFunctions.wait(controller.getDelayTime() * 1000);
        }

        return Message.LOGIN_SUCCESSFUL.equals(message);
    }

    private void forgotPassword(Matcher matcher) {
        this.message = this.controller.forgotPassword(matcher);
        System.out.println(this.message);

        if (Message.ASK_QUESTION.equals(message)) {
            System.out.println(this.controller.getUser().getSecurityQuestion());
            do {
                this.command = this.scanner.nextLine();
                this.message = this.controller.answerSecurityQuestion(this.command);

                System.out.println(this.message);

            } while (Message.ENTER_NEW_PASSWORD.equals(message));

            firstLoop:
            while (true) {
                this.command = this.scanner.nextLine();
                this.message = this.controller.getNewPassword(this.command);

                System.out.println(this.message);

                if (Message.ENTER_PASSWORD_AGAIN.equals(message))
                    while (true) {
                        this.command = this.scanner.nextLine();
                        this.message = this.controller.getNewPasswordAgain(this.command);
                        System.out.println(this.message);
                        if (Message.CHANGE_PASSWORD_SUCCESSFUL.equals(message) || Message.CANCEL.equals(message))
                            break firstLoop;
                    }
                else if (Message.CANCEL.equals(message))
                    break;
            }
        }
    }
}
