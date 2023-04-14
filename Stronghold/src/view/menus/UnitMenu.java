package view.menus;

import controller.UnitMenuController;
import view.enums.Results;
import java.util.Scanner;
import java.util.regex.Matcher;

public class UnitMenu {
    private final UnitMenuController controller = new UnitMenuController();
    private final Scanner scanner;

    public UnitMenu(Scanner scanner){
        this.scanner = scanner;
    }

    public Results run(Scanner scanner){
        //TODO: fill
        String command;
        Matcher matcher;
        return null;
    }
}
