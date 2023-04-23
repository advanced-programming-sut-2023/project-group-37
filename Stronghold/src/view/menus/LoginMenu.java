package view.menus;

import controller.LoginMenuController;
import view.enums.Results;
import view.enums.commands.LoginMenuCommands;
import view.enums.messages.LoginMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {
    private final LoginMenuController controller = LoginMenuController.getInstance();
    private LoginMenuMessages message;
    private final Scanner scanner;
    private String command;

    public LoginMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public Results run() {
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.LOGIN)) != null) {
                if(login(matcher))
                    return Results.LOGGED_IN;
            }

            else if ((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.FORGOT_PASSWORD)) != null)
                forgotPassword(matcher);

            else if (LoginMenuCommands.getMatcher(command, LoginMenuCommands.ENTER_REGISTER_MENU) != null)
                return Results.ENTER_REGISTER_MENU;

            else if (LoginMenuCommands.getMatcher(command, LoginMenuCommands.EXIT) != null)
                return Results.EXIT;

            else System.out.println("Invalid command!");
        }
    }

    private boolean login(Matcher matcher) {
        message = controller.login(matcher);
        System.out.println(message);

        return message == LoginMenuMessages.LOGIN_SUCCESSFUL;
    }

    private void forgotPassword(Matcher matcher) {
        message = controller.forgot(matcher);
        System.out.println(message);

        if (message == LoginMenuMessages.ASK_QUESTION) {
            System.out.println(controller.getUser().getPasswordRecoveryQuestion());
            do {
                command = scanner.nextLine();
                message = controller.answerRecoveryQuestion(command);

                System.out.println(message);

            } while (message != LoginMenuMessages.ENTER_NEW_PASSWORD);

            firstLoop:
            while (true) {
                command = scanner.nextLine();
                message = controller.enterNewPassword(command);

                System.out.println(message);

                if (message == LoginMenuMessages.ENTER_NEW_PASSWORD_AGAIN) {

                    while (true) {
                        command = scanner.nextLine();
                        message = controller.enterNewPasswordAgain(command);

                        System.out.println(message);

                        if (message == LoginMenuMessages.CHANGE_PASSWORD_SUCCESSFUL ||
                                message == LoginMenuMessages.CANCEL)
                            break firstLoop;
                    }
                }
                else if (message == LoginMenuMessages.CANCEL)
                    break;
            }
        }
    }

}
