package view.menus;

import controller.BuildingMenuController;
import controller.ProfileMenuController;
import view.enums.Results;

import java.util.Scanner;
import java.util.regex.Matcher;

public class BuildingMenu {
    private final BuildingMenuController controller = new BuildingMenuController();
    private final Scanner scanner;

    public BuildingMenu(Scanner scanner){
        this.scanner = scanner;
    }

    public Results run(){
        //TODO: fill
        String command;
        Matcher matcher;
        return null;
    }
}
