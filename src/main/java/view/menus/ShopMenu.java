package view.menus;

import controller.AppController;
import controller.viewControllers.ShopMenuController;
import view.enums.Command;
import view.enums.Message;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu {
    private final AppController appController;
    private final ShopMenuController shopMenuController;
    private final Scanner scanner;

    public ShopMenu() {
        this.appController = AppController.getInstance();
        this.scanner = new Scanner(System.in);
        this.shopMenuController = ShopMenuController.getInstance();
    }

    public void run() {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = Command.BUY_ITEM.getMatcher(command)) != null)
                System.out.println(this.shopMenuController.buy(matcher));

            else if ((matcher = Command.SELL_ITEM.getMatcher(command)) != null)
                System.out.println(this.shopMenuController.sell(matcher));

            else if (command.matches(Command.SHOW_ALL_ITEMS.toString()))
                System.out.println(shopMenuController.showAllItems());

            else if (command.matches(Command.BACK_GAME_MENU.toString())) {
                System.out.println(Message.BACK_GAME_MENU);
                return;
            } else System.out.println(Message.INVALID_COMMAND);
        }
    }
}