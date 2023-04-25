package view.menus;

import controller.BuildingMenuController;
import view.enums.Command;
import view.enums.Message;

import java.util.Scanner;

public class BuildingMenu {
    private final BuildingMenuController controller;

    {
        this.controller = new BuildingMenuController();
    }

    public void run(Scanner scanner) {

        String command;

        while (true) {
            command = scanner.nextLine();

            if (Command.REPAIR.getMatcher(command) != null)
                this.controller.repair();
            else if (Command.BACK_GAME_MENU.getMatcher(command) != null) {
                System.out.println(Message.BACK_GAME_MENU);
                return;
            }

            else System.out.println(Message.INVALID_COMMAND);
        }
    }
}
