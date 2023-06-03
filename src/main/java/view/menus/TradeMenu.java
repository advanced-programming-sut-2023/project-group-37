package view.menus;

import controller.AppController;
import controller.TradeMenuController;
import view.enums.Command;
import view.enums.Message;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu {
    private final AppController appController;
    private final TradeMenuController tradeMenuController;
    private final Scanner scanner;

    {
        this.appController = AppController.getInstance();
        this.scanner = new Scanner(System.in);
        this.tradeMenuController = TradeMenuController.getInstance();
    }

    public void run() {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = Command.REQUEST_TRADE.getMatcher(command)) != null)
                System.out.println(tradeMenuController.requestTrade(matcher));

            else if (command.matches(Command.SHOW_TRADE_LIST.toString()))
                System.out.println(tradeMenuController.showTradeList());

            else if ((matcher = Command.ACCEPT_TRADE.getMatcher(command)) != null)
                System.out.println(tradeMenuController.acceptTrade(matcher));

            else if (command.matches(Command.SHOW_TRADE_HISTORY.toString()))
                System.out.println(tradeMenuController.showTradeHistory());

            else if (command.matches(Command.SHOW_NEW_TRADES.toString()))
                System.out.println(tradeMenuController.showNewTrades());

            else if (command.matches(Command.BACK_GAME_MENU.toString())) {
                System.out.println(Message.BACK_GAME_MENU);
                return;
            } else System.out.println(Message.INVALID_COMMAND);
        }
    }
}