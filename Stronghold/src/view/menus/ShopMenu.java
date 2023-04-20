package view.menus;

import controller.ShopMenuController;

import java.util.Scanner;

public class ShopMenu {
    private ShopMenuController shopMenuController = new ShopMenuController();

    private String command;
    private final Scanner scanner;

    public ShopMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {

    }
}