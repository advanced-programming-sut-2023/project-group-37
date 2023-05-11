package view.menus;

import controller.BuildingMenuController;
import view.enums.Command;
import view.enums.Message;

import java.util.Scanner;
import java.util.regex.Matcher;

public class BuildingMenu {
    private final BuildingMenuController controller;

    public BuildingMenu(BuildingMenuController buildingMenuController) {
        this.controller = buildingMenuController;
    }

    public void run(Scanner scanner) {

        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = Command.DROP_BUILDING.getMatcher(command)) != null)
                System.out.println(this.controller.selectBuilding(matcher));
            else if (command.matches(Command.CANCEL.toString())) {
                Message result = this.controller.deselectBuilding();
                if (result != null)
                    System.out.println(result);
            } else if (Command.REPAIR.getMatcher(command) != null)
                this.controller.repair();
            else if (Command.BACK_GAME_MENU.getMatcher(command) != null) {
                System.out.println(Message.BACK_GAME_MENU);
                return;
            } else System.out.println(Message.INVALID_COMMAND);
        }
    }
}
