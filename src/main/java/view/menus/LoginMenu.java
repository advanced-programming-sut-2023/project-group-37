package view.menus;

import controller.LoginMenuController;
import view.enums.Result;
import view.enums.Command;
import view.enums.Message;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {
    private final LoginMenuController controller = LoginMenuController.getInstance();
    private final Scanner scanner;
    private String command;
    private Message message;

    public LoginMenu(Scanner scanner) {
        this.scanner = scanner;
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
            else if (Command.ENTER_REGISTER_MENU.getMatcher(this.command) != null)
                return Result.ENTER_REGISTER_MENU;
            else if (Command.EXIT.getMatcher(this.command) != null)
                return Result.EXIT;
            else
                System.out.println(Message.INVALID_COMMAND);
        }
    }

    private boolean login(Matcher matcher) {
        this.message = this.controller.login(matcher);
        System.out.println(this.message);

        return message == Message.LOGIN_SUCCESSFUL;
    }

    private void forgotPassword(Matcher matcher) {
        this.message = this.controller.forgotPassword(matcher);
        System.out.println(this.message);

        if (this.message == Message.ASK_QUESTION) {
            System.out.println(this.controller.getUser().getSecurityQuestion());
            do {
                this.command = this.scanner.nextLine();
                this.message = this.controller.answerSecurityQuestion(this.command);

                System.out.println(this.message);

            } while (this.message != Message.ENTER_NEW_PASSWORD);

            firstLoop:
            while (true) {
                this.command = this.scanner.nextLine();
                this.message = this.controller.getNewPassword(this.command);

                System.out.println(this.message);

                if (this.message == Message.ENTER_NEW_PASSWORD_AGAIN)
                    while (true) {
                        this.command = this.scanner.nextLine();
                        this.message = this.controller.getNewPasswordAgain(this.command);
                        System.out.println(this.message);
                        if (this.message == Message.CHANGE_PASSWORD_SUCCESSFUL || this.message == Message.CANCEL)
                            break firstLoop;
                    }
                else if (this.message == Message.CANCEL)
                    break;
            }
        }
    }
}
