package view.menus;

import controller.TradeMenuController;
import view.enums.Results;

import java.util.Scanner;

public class TradeMenu {
    private final Scanner scanner;
    private String command;
    private final TradeMenuController controller = new TradeMenuController();
    public TradeMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public Results run() {
        return null;
    }
}