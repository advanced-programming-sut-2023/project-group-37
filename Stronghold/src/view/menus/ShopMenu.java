package view.menus;

import controller.ShopMenuController;
import view.enums.Results;
import view.enums.commands.ShopMenuCommands;
import view.enums.messages.ShopMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu {
    private final ShopMenuController controller = new ShopMenuController();
    private final Scanner scanner;

    public ShopMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if(ShopMenuCommands.getMatcher(command, ShopMenuCommands.SHOW_PRICE_LIST) != null)
                System.out.println(controller.showPriceList());

            else if ((matcher = ShopMenuCommands.getMatcher(command, ShopMenuCommands.BUY_ITEM)) != null)
                System.out.println(controller.buy(matcher));

            else if ((matcher = ShopMenuCommands.getMatcher(command, ShopMenuCommands.SELL_ITEM)) != null)
                System.out.println(controller.sell(matcher));

            else if (ShopMenuCommands.getMatcher(command, ShopMenuCommands.BACK_GAME_MENU) != null) {
                System.out.println(ShopMenuMessages.BACK_GAME_MENU);
                return;
            }
        }
    }
}