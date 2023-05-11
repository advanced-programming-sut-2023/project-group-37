package view.menus;

import controller.MapMenuController;
import view.enums.Command;
import view.enums.Message;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MapMenu {

    private final MapMenuController controller;
    private final Scanner scanner;

    public MapMenu(Scanner scanner, MapMenuController mapMenuController) {
        this.scanner = scanner;
        this.controller = mapMenuController;
    }

    public void run() {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = Command.MOVE_THE_MAP.getMatcher(command)) != null)
                System.out.println(this.controller.moveMap(matcher));
            else if ((matcher = Command.SHOW_DETAILS.getMatcher(command)) != null)
                System.out.println(this.controller.showDetails(matcher));
            else if (Command.BACK_GAME_MENU.getMatcher(command) != null) {
                System.out.println(Message.BACK_GAME_MENU);
                return;
            } else System.out.println(Message.INVALID_COMMAND);
        }
    }
}