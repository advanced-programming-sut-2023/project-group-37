package view.menus;

import controller.TradeMenuController;
import view.enums.Command;
import view.enums.Message;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu {
    private final TradeMenuController controller;

    {
        this.controller = new TradeMenuController();
    }

    public void run(Scanner scanner) {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = Command.REQUEST_TRADE.getMatcher(command)) != null)
                System.out.println(controller.requestTrade(matcher));
            else if (Command.SHOW_TRADE_LIST.getMatcher(command) != null)
                System.out.println(controller.showTradeList());
            else if ((matcher = Command.ACCEPT_TRADE.getMatcher(command)) != null)
                System.out.println(controller.acceptTrade(matcher));
            else if (Command.SHOW_TRADE_HISTORY.getMatcher(command) != null)
                System.out.println(controller.showTradeHistory());
            else if (Command.BACK_GAME_MENU.getMatcher(command) != null) {
                System.out.println(Message.BACK_GAME_MENU);
                return;
            }

            else System.out.println(Message.INVALID_COMMAND);
        }
    }
}