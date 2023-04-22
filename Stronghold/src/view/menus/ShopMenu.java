package view.menus;

import controller.ShopMenuController;
import view.enums.Results;

import java.util.Scanner;

public class ShopMenu {
    private final ShopMenuController controller = new ShopMenuController();

    private String command;
    private final Scanner scanner;

    public ShopMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public Results run() {
        return null;
    }
}