package controller;

import model.game.Government;
import model.user.User;
import view.enums.Message;

import java.util.regex.Matcher;

public class TradeMenuController {
    private static Government government;

    public static void setGovernment(Government government) {
        TradeMenuController.government = government;
    }

    public Message requestTrade(Matcher matcher) {
        return null;
    }

    public Message showTradeList() {
        return null;
    }

    public Message acceptTrade(Matcher matcher) {
        return null;
    }

    public Message showTradeHistory() {
        return null;
    }
}
