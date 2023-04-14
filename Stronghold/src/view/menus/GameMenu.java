package view.menus;

import controller.GameMenuController;
import view.enums.Results;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    private final GameMenuController controller = new GameMenuController();
    private final Scanner scanner;

    public GameMenu(Scanner scanner){
        this.scanner = scanner;
    }

    public Results run(Scanner scanner){
        //TODO: fill
        String command;
        Matcher matcher;
        return null;
    }
}
