package view.menus;

import controller.GameMenuController;
import model.Game;
import view.enums.Results;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    private final GameMenuController controller;
    private final Scanner scanner;

    public GameMenu(Scanner scanner,Game game){
        this.scanner = scanner;
        controller = new GameMenuController(game);
    }

    public Results run(Scanner scanner){
        //TODO: fill
        String command;
        Matcher matcher;
        return null;
    }
}
