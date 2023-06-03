package view.menus;

import controller.AppController;
import controller.LoginMenuController;
import controller.MultiMenuFunctions;
import javafx.application.Application;
import javafx.stage.Stage;
import view.enums.Result;
import view.enums.Command;
import view.enums.Message;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu extends Application {
    private final LoginMenuController loginMenuController;
    private final Scanner scanner;
    private String command;
    private String message;

    public LoginMenu() {
        this.loginMenuController = LoginMenuController.getInstance();
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AppController.runMenu(Result.ENTER_REGISTER_MENU);
    }

    public Result run() {
        Matcher matcher;

        while (true) {
            this.command = this.scanner.nextLine();

            if ((matcher = Command.LOGIN.getMatcher(this.command)) != null) {
                if (login(matcher))
                    return Result.ENTER_MAIN_MENU;
            } else if ((matcher = Command.FORGOT_PASSWORD.getMatcher(this.command)) != null)
                forgotPassword(matcher);
            else if (command.matches(Command.ENTER_REGISTER_MENU.toString())) {
                System.out.println(Message.ENTERED_REGISTER_MENU);
                return Result.ENTER_REGISTER_MENU;
            } else if (command.matches(Command.EXIT.toString()))
                return Result.EXIT;
            else
                System.out.println(Message.INVALID_COMMAND);
        }
    }

    private boolean login(Matcher matcher) {
        this.message = this.loginMenuController.login(matcher);
        System.out.println(this.message);

        if (Message.INCORRECT_PASSWORD.equals(message)) {
            System.out.println("You can try again in " + loginMenuController.getDelayTime() + " seconds!");
            MultiMenuFunctions.wait(loginMenuController.getDelayTime() * 1000);
        }

        return Message.LOGIN_SUCCESSFUL.equals(message);
    }

    private void forgotPassword(Matcher matcher) {
        this.message = this.loginMenuController.forgotPassword(matcher);
        System.out.println(this.message);

        if (Message.ASK_QUESTION.equals(message)) {
            System.out.println(this.loginMenuController.getUser().getRecoveryQuestion());
            do {
                this.command = this.scanner.nextLine();
                this.message = this.loginMenuController.answerSecurityQuestion(this.command);

                System.out.println(this.message);

            } while (!Message.ENTER_NEW_PASSWORD.equals(message));

            firstLoop:
            while (true) {
                this.command = this.scanner.nextLine();
                this.message = this.loginMenuController.getNewPassword(this.command);

                System.out.println(this.message);
                if (Message.ENTER_NEW_PASSWORD_AGAIN.equals(message)) {
                    while (true) {
                        this.command = this.scanner.nextLine();
                        this.message = this.loginMenuController.getNewPasswordAgain(this.command);
                        System.out.println(this.message);
                        if (Message.CHANGE_PASSWORD_SUCCESSFUL.equals(this.message) || Message.CANCEL.equals(message))
                            break firstLoop;
                    }
                } else if (Message.CANCEL.equals(message))
                    break;
            }
        }
    }
}
