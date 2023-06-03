package view.menus;

import controller.AppController;
import controller.BuildingMenuController;
import view.enums.Command;
import view.enums.Message;

import java.util.Scanner;
import java.util.regex.Matcher;

public class BuildingMenu {
    private final BuildingMenuController buildingMenuController;
    private final Scanner scanner;

    public BuildingMenu() {
        this.scanner = new Scanner(System.in);
        this.buildingMenuController = BuildingMenuController.getInstance();
    }

    public void run() {

        String command;
        Matcher matcher;

        if (this.buildingMenuController.getCurrentBuilding().getType().isRepairable())
            System.out.println("Hitpoints: " + this.buildingMenuController.getCurrentBuilding().getHitpoints());

        while (true) {
            command = scanner.nextLine();
            if (command.matches(Command.REPAIR.toString()) && this.buildingMenuController.getCurrentBuilding() != null)
                System.out.println(this.buildingMenuController.repair());

            else if ((matcher = Command.CREATE_UNIT.getMatcher(command)) != null)
                System.out.println(buildingMenuController.createUnit(matcher));

            else if (command.matches(Command.BACK_GAME_MENU.toString())) {
                System.out.println(Message.BACK_GAME_MENU);
                return;
            } else System.out.println(Message.INVALID_COMMAND);
        }
    }

}
