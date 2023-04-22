package view.menus;

import controller.MapMenuController;
import view.enums.Results;
import view.enums.commands.MapMenuCommands;
import view.enums.messages.MapMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MapMenu {

    private final MapMenuController controller = new MapMenuController();
    private final Scanner scanner;

    public MapMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.MOVE_THE_MAP)) != null)
                System.out.println(controller.moveMap(matcher));

            else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.SHOW_DETAILS)) != null)
                System.out.println(controller.showDetails(matcher));

            else if (MapMenuCommands.getMatcher(command, MapMenuCommands.BACK_GAME_MENU) != null) {
                System.out.println(MapMenuMessages.BACK_GAME_MENU);
                return;
            }
        }
    }
}