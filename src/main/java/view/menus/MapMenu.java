package view.menus;

import controller.AppController;
import controller.viewControllers.MapMenuController;
import model.game.Tile;
import view.enums.Command;
import view.enums.Message;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MapMenu {
    private final AppController appController;
    private final MapMenuController mapMenuController;
    private final Scanner scanner;

    public MapMenu() {
        this.appController = AppController.getInstance();
        this.scanner = new Scanner(System.in);
        this.mapMenuController = MapMenuController.getInstance();
    }

    public void run() {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = Command.MOVE_THE_MAP.getMatcher(command)) != null) {
                if (!this.showMap(matcher))
                    System.out.println(Message.ADDRESS_OUT_OF_BOUNDS);
            }
            else if ((matcher = Command.SHOW_DETAILS.getMatcher(command)) != null)
                System.out.println(this.mapMenuController.showDetails(matcher));
            else if (command.matches(Command.BACK_GAME_MENU.toString())) {
                System.out.println(Message.BACK_GAME_MENU);
                return;
            } else System.out.println(Message.INVALID_COMMAND);
        }
    }
    private boolean showMap(Matcher matcher) {
        Tile[][] tiles = this.mapMenuController.moveMap(matcher);
        if (tiles == null)
            return false;

        return showMapWithTiles(tiles);
    }

    static boolean showMapWithTiles(Tile[][] tiles) {
        int columnLength = tiles[0].length;
        Tile tile;

        for (int j = 0; j < columnLength; j++) {
            for (Tile[] value : tiles) {
                tile = value[j];
                System.out.print(tile.getTexture().getColor().toString() + " " + tile.getState());
            }
            System.out.print(" ");
            System.out.println("\033[0m");
        }

        return true;
    }
}