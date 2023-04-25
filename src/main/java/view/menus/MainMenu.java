package view.menus;

import controller.MainMenuController;
import view.enums.Result;
import view.enums.Command;
import view.enums.Message;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu {
    private final MainMenuController controller;

    {
        this.controller = new MainMenuController();
    }

    public Result run(Scanner scanner) {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if (Command.ENTER_PROFILE_MENU.getMatcher(command) != null) {
                System.out.println(this.controller.enterProfileMenu());
                return Result.ENTER_PROFILE_MENU;
            }
            else if ((matcher = Command.START_GAME.getMatcher(command)) != null) {
                if (startGame(matcher))
                    return Result.ENTER_GAME_MENU;
            }
            else if (Command.LOGOUT.getMatcher(command) != null) {
                System.out.println(this.controller.logout());
                return Result.ENTER_LOGIN_MENU;
            }
            else
                System.out.println(Message.INVALID_COMMAND);
        }
    }

    private boolean startGame(Matcher matcher) {
        Message message = this.controller.startGame(matcher);
        System.out.println(message);

        return message == Message.GAME_STARTED;
    }
}