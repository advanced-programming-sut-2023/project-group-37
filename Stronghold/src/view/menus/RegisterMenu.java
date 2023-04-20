package view.menus;

import controller.RegisterMenuController;
import view.enums.Results;
import view.enums.commands.RegisterMenuCommands;
import view.enums.messages.RegisterMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class RegisterMenu {
    private final RegisterMenuController controller = RegisterMenuController.getInstance();
    private RegisterMenuMessages message;
    private final Scanner scanner;
    private String command;

    public RegisterMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public Results run() {

        while (true) {
            command = scanner.nextLine();

            Matcher matcher;

            if ((matcher = RegisterMenuCommands.getMatcher(command, RegisterMenuCommands.REGISTER)) != null) {
                if (register(matcher))
                    return Results.USER_CREATED;
            }

            else if (RegisterMenuCommands.getMatcher(command, RegisterMenuCommands.ENTER_LOGIN_MENU) != null)
                return Results.ENTER_LOGIN_MENU;

            else if (RegisterMenuCommands.getMatcher(command, RegisterMenuCommands.EXIT) != null)
                return Results.EXIT;

            else System.out.println("Invalid command!");
        }
    }

    private boolean pickQuestion() {
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = RegisterMenuCommands.getMatcher(command, RegisterMenuCommands.PICK_QUESTION)) != null) {
                message = controller.pickQuestion(matcher);
                System.out.println(message);

                if (message == RegisterMenuMessages.REGISTER_SUCCESSFUL)
                    return true;
            }

            else if (RegisterMenuCommands.getMatcher(command, RegisterMenuCommands.CANCEL) != null) {
                System.out.println(RegisterMenuMessages.CANCEL);
                return false;
            }

            else System.out.println("Invalid command!");
        }
    }

    private boolean register(Matcher matcher) {
        message = controller.register(matcher);

        System.out.println(message);

        switch (message) {

            case ASK_FOR_SECURITY_QUESTION -> { return pickQuestion(); }

            case RANDOM_PASSWORD -> {
                String randomPassword = controller.getRandomPassword();
                System.out.println(RegisterMenuMessages.RANDOM_PASSWORD.continueOutput(randomPassword));

                do {
                    command = scanner.nextLine();

                    message = controller.checkPasswordConfirm(command);
                    System.out.println(message);

                } while (message != RegisterMenuMessages.ASK_FOR_SECURITY_QUESTION &&
                        message != RegisterMenuMessages.CANCEL);

                return pickQuestion();
            }
        }

        return false;
    }

}