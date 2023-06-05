package view.menus;

import controller.AppController;
import controller.viewControllers.MapController;
import view.enums.Command;
import view.enums.Message;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MapMenu {
    private final AppController appController;
    private final MapController mapController;
    private final Scanner scanner;

    public MapMenu() {
        this.appController = AppController.getInstance();
        this.scanner = new Scanner(System.in);
        this.mapController = MapController.getInstance();
    }

    public void run() {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = Command.SHOW_DETAILS.getMatcher(command)) != null)
                System.out.println(this.mapController.showDetails(matcher));
            else if (command.matches(Command.BACK_GAME_MENU.toString())) {
                System.out.println(Message.BACK_GAME_MENU);
                return;
            } else System.out.println(Message.INVALID_COMMAND);
        }
    }

}