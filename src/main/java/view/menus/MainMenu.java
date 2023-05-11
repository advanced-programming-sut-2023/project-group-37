package view.menus;

import controller.MainMenuController;
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

            if (Command.ENTER_PROFILE_MENU.getMatcher(command) != null) {
                System.out.println(this.controller.enterProfileMenu());
                return Result.ENTER_PROFILE_MENU;
            } else if ((matcher = Command.START_GAME.getMatcher(command)) != null) {
                if (startGame(matcher))
                    return Result.ENTER_GAME_MENU;
                System.out.println(Message.TERRITORY_NOT_ASSIGNED);
            } else if (Command.LOGOUT.getMatcher(command) != null) {
                System.out.println(this.controller.logout());
                return Result.ENTER_LOGIN_MENU;
            } else if (Command.EXIT.getMatcher(command) != null) {
                return Result.EXIT;
            } else
                System.out.println(Message.INVALID_COMMAND);
        }
    }

    private boolean startGame(Matcher matcher) {
        String[] parts = matcher.group("users").trim().split("\\s*&\\s*");
        String[] usernames = new String[parts.length];
        String[] numbers = new String[parts.length];
        String[] split;

        for (int index = 0; index < parts.length; index++) {
            try {
                split = parts[index].split("\\s+");
                usernames[index] = split[0];
                numbers[index] = split[1];
            } catch (Exception ex) {
                return false;
            }
        }

        String message = this.controller.startGame(usernames, numbers, matcher.group("turns"), matcher.group("mapName"));
        System.out.println(message);

        return message.equals(Message.GAME_STARTED.toString());
    }
}