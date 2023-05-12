package view.menus;

import controller.TradeMenuController;
import view.enums.Command;
import view.enums.Message;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu {
    private final TradeMenuController controller;
    private final Scanner scanner;

    public TradeMenu(Scanner scanner, TradeMenuController tradeMenuController) {
        this.scanner = scanner;
        this.controller = tradeMenuController;
    }

    public void run() {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = Command.REQUEST_TRADE.getMatcher(command)) != null)
                System.out.println(controller.requestTrade(matcher));

            else if (command.matches(Command.SHOW_TRADE_LIST.toString()))
                System.out.println(controller.showTradeList());

            else if ((matcher = Command.ACCEPT_TRADE.getMatcher(command)) != null)
                System.out.println(controller.acceptTrade(matcher));

            else if (command.matches(Command.SHOW_TRADE_HISTORY.toString()))
                System.out.println(controller.showTradeHistory());

            else if (command.matches(Command.SHOW_NEW_TRADES.toString()))
                System.out.println(controller.showNewTrades());

            else if (command.matches(Command.BACK_GAME_MENU.toString())) {
                System.out.println(Message.BACK_GAME_MENU);
                return;
            } else System.out.println(Message.INVALID_COMMAND);
        }
    }
}