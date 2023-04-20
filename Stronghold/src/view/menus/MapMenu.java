package view.menus;

import controller.MapMenuController;
import view.enums.Results;
import view.enums.commands.MapMenuCommands;

import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MapMenu {

    private final MapMenuController controller = new MapMenuController();
    private final Scanner scanner;

    public MapMenu(Scanner scanner){
        this.scanner = scanner;
    }

    public Results run(Scanner scanner){
        //TODO: fill
        String command;
        Matcher matcher;
        return null;
    }
}