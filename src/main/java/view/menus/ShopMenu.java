package view.menus;

import controller.ShopMenuController;
import view.enums.Command;
import view.enums.Message;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu {
    private final ShopMenuController controller;
    private final Scanner scanner;

    public ShopMenu(Scanner scanner, ShopMenuController shopMenuController) {
        this.scanner = scanner;
        this.controller = shopMenuController;
    }

    public void run(Scanner scanner) {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if(Command.SHOW_PRICE_LIST.getMatcher(command) != null)
                System.out.println(this.controller.showPriceList());

            else if ((matcher = Command.BUY_ITEM.getMatcher(command)) != null)
                System.out.println(this.controller.buy(matcher));

            else if ((matcher = Command.SELL_ITEM.getMatcher(command)) != null)
                System.out.println(this.controller.sell(matcher));

            else if (Command.BACK_GAME_MENU.getMatcher(command) != null) {
                System.out.println(Message.BACK_GAME_MENU);
                return;
            }

            else System.out.println(Message.INVALID_COMMAND);
        }
    }
}