package view.menus;

import controller.BuildingMenuController;
import view.enums.Command;
import view.enums.Message;
import view.enums.Result;

import java.util.Scanner;
import java.util.regex.Matcher;

public class BuildingMenu {
    private final BuildingMenuController controller;
    private final Scanner scanner;

    public BuildingMenu(Scanner scanner, BuildingMenuController buildingMenuController) {
        this.scanner = scanner;
        this.controller = buildingMenuController;
    }

    public void run() {

        String command;
//        Matcher matcher;

        if (this.controller.getCurrentBuilding().getType().isRepairable())
            System.out.println("Hitpoints: " + this.controller.getCurrentBuilding().getHitpoints());

        while (true) {
            command = scanner.nextLine();

            if (command.matches(Command.CANCEL.toString())) {
                Message result = this.controller.deselectBuilding();
                if (result != null)
                    System.out.println(result);
                return;
            } else if (command.matches(Command.REPAIR.toString()) && this.controller.getCurrentBuilding() != null)
                System.out.println(this.controller.repair());
            else if (Command.BACK_GAME_MENU.getMatcher(command) != null) {
                System.out.println(Message.BACK_GAME_MENU);
                return;
            } else System.out.println(Message.INVALID_COMMAND);

            // TODO: createUnit!
        }
    }
}
