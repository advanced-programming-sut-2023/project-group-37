package view.menus;

import controller.BuildingMenuController;
import view.enums.Command;
import view.enums.Message;

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
        Matcher matcher;

        if (this.controller.getCurrentBuilding().getType().isRepairable())
            System.out.println("Hitpoints: " + this.controller.getCurrentBuilding().getHitpoints());

        while (true) {
            command = scanner.nextLine();
            if (command.matches(Command.REPAIR.toString()) && this.controller.getCurrentBuilding() != null)
                System.out.println(this.controller.repair());

            else if ((matcher = Command.CREATE_UNIT.getMatcher(command)) != null)
                System.out.println(controller.createUnit(matcher));

            else if (command.matches(Command.BACK_GAME_MENU.toString())) {
                System.out.println(Message.BACK_GAME_MENU);
                return;
            } else System.out.println(Message.INVALID_COMMAND);
        }
    }

}
