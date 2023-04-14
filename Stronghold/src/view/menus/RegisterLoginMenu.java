package view.menus;

import controller.RegisterLoginMenuController;
import view.enums.commands.RegisterLoginMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class RegisterLoginMenu {
    private final RegisterLoginMenuController registerLoginMenuController = new RegisterLoginMenuController();
    private final Scanner scanner;
    private String command;

    public RegisterLoginMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {

        while (true) {
            command = scanner.nextLine();

            Matcher matcher;
            if ((matcher = RegisterLoginMenuCommands.getMatcher(command, RegisterLoginMenuCommands.REGISTER)) != null)
                register(matcher);

            else if ((matcher = RegisterLoginMenuCommands.getMatcher(command, RegisterLoginMenuCommands.LOGIN)) != null)
                login(matcher);

            else if ((matcher = RegisterLoginMenuCommands.getMatcher(command, RegisterLoginMenuCommands.FORGOT_PASSWORD)) != null)
                forgot(matcher);

            else if (RegisterLoginMenuCommands.getMatcher(command, RegisterLoginMenuCommands.LOGOUT) != null)
                System.out.println(registerLoginMenuController.logout());
        }
    }

    private void register(Matcher matcher) {
        String message = registerLoginMenuController.register(matcher);

        if (message == null) {
            System.out.println("Pick your security question: 1. What is my father’s name? 2. What\n" +
                    "was my first pet’s name? 3. What is my mother’s last name?");

            while (true) {
                command = scanner.nextLine();

                if ((matcher = RegisterLoginMenuCommands.getMatcher(command, RegisterLoginMenuCommands.PICK_QUESTION)) != null) {
                    message = registerLoginMenuController.pickQuestion(matcher);

                    if (message != null)
                        System.out.println(message);

                    else break;
                } else System.out.println("Invalid command!");
            }
        }
    }

    private void login(Matcher matcher) {
        String message = registerLoginMenuController.login(matcher);
    }

    private void forgot(Matcher matcher) {

    }

}
