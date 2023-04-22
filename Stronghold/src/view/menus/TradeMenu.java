package view.menus;

import controller.TradeMenuController;
import view.enums.Results;
import view.enums.commands.TradeMenuCommands;
import view.enums.messages.TradeMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu {
    private final Scanner scanner;
    private final TradeMenuController controller = new TradeMenuController();
    public TradeMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.REQUEST_TRADE)) != null)
                System.out.println(controller.requestTrade(matcher));

            else if (TradeMenuCommands.getMatcher(command, TradeMenuCommands.SHOW_TRADE_LIST) != null)
                System.out.println(controller.showTradeList());

            else if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.ACCEPT_TRADE)) != null)
                System.out.println(controller.acceptTrade(matcher));

            else if (TradeMenuCommands.getMatcher(command, TradeMenuCommands.SHOW_TRADE_HISTORY) != null)
                System.out.println(controller.showTradeHistory());

            else if (TradeMenuCommands.getMatcher(command, TradeMenuCommands.BACK_GAME_MENU) != null) {
                System.out.println(TradeMenuMessages.BACK_GAME_MENU);
                return;
            }
        }
    }
}