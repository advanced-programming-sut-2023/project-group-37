package view.menus;

import controller.MainMenuController;
import view.enums.Results;
import view.enums.commands.MainMenuCommands;
import view.enums.messages.MainMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu {
    private final MainMenuController controller = new MainMenuController();
    private final Scanner scanner;

    public MainMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public Results run() {
        controller.setCurrentUser();

        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if (MainMenuCommands.getMatcher(command, MainMenuCommands.ENTER_PROFILE_MENU) != null) {
                System.out.println(controller.enterProfileMenu());
                return Results.ENTER_PROFILE_MENU;
            }

            else if ((matcher = MainMenuCommands.getMatcher(command, MainMenuCommands.START_GAME)) != null) {
                startGame(matcher);
                return Results.ENTER_GAME_MENU;
            }

            else if (MainMenuCommands.getMatcher(command, MainMenuCommands.LOGOUT) != null) {
                System.out.println(controller.logout());
                return Results.ENTER_LOGIN_MENU;
            }

            else System.out.println("Invalid command!");
        }
    }

    private void startGame(Matcher matcher) {
        MainMenuMessages message = controller.startGame(matcher);
    }
}
