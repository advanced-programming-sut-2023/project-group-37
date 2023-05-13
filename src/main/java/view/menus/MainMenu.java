package view.menus;

import controller.MainMenuController;
import controller.MultiMenuFunctions;
import view.enums.Result;
import view.enums.Command;
import view.enums.Message;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu {
    private final MainMenuController controller;
    private final Scanner scanner;

    public MainMenu(Scanner scanner, MainMenuController mainMenuController) {
        this.scanner = scanner;
        this.controller = mainMenuController;
    }

    public Result run() {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if (command.matches(Command.ENTER_PROFILE_MENU.toString())) {
                System.out.println(this.controller.enterProfileMenu());
                return Result.ENTER_PROFILE_MENU;
            } else if ((matcher = Command.START_GAME.getMatcher(command)) != null) {
                if (startGame(matcher))
                    return Result.ENTER_GAME_MENU;
            } else if (command.matches(Command.LOGOUT.toString())) {
                System.out.println(this.controller.logout());
                return Result.ENTER_LOGIN_MENU;
            } else if (command.matches(Command.EXIT.toString())) {
                return Result.EXIT;
            } else
                System.out.println(Message.INVALID_COMMAND);
        }
    }

    private boolean startGame(Matcher matcher) {
        String[] parts = MultiMenuFunctions.deleteQuotations(matcher.group("users")).trim().split("\\s*&\\s*");
        String[] usernames = new String[parts.length];
        String[] numbers = new String[parts.length];
        String[] split;

        try {
            for (int index = 0; index < parts.length; index++) {
                split = parts[index].split("\\s+");
                usernames[index] = split[0];
                numbers[index] = split[1];
            }
        }
        catch (Exception ex) {
            System.out.println(Message.TERRITORY_NOT_ASSIGNED);
            return false;
        }

        String message = this.controller.startGame(usernames, numbers, matcher.group("turns"),
                MultiMenuFunctions.deleteQuotations(matcher.group("mapName")));

        System.out.println(message);
        return message.equals(Message.GAME_STARTED.toString());
    }
}