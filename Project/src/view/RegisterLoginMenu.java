package view;

import controller.RegisterLoginMenuController;

import java.util.Scanner;
import java.util.regex.Matcher;

public class RegisterLoginMenu {
    private final RegisterLoginMenuController registerLoginMenuController = new RegisterLoginMenuController();
    private final Scanner scanner;

    public RegisterLoginMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = RegisterLoginMenuCommands.getMatcher(command, RegisterLoginMenuCommands.REGISTER)) != null)
                register();

            else if ((matcher = RegisterLoginMenuCommands.getMatcher(command, RegisterLoginMenuCommands.LOGIN)) != null)
                login();

            else if (RegisterLoginMenuCommands.getMatcher(command, RegisterLoginMenuCommands.FORGOT_PASSWORD) != null)
                forgot();

            else if ()
        }
    }
}
