package view.menus;

import controller.BuildingMenuController;
import view.enums.Results;
import view.enums.commands.BuildingMenuCommands;
import view.enums.messages.BuildingMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class BuildingMenu {
    private final BuildingMenuController controller = new BuildingMenuController();
    private final Scanner scanner;

    public BuildingMenu(Scanner scanner){
        this.scanner = scanner;
    }

    public void run(){
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if (BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.REPAIR) != null) {
                controller.repair();
            }

            else if (BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.BACK_GAME_MENU) != null) {
                System.out.println(BuildingMenuMessages.BACK_GAME_MENU);
                return;
            }
        }
    }
}
