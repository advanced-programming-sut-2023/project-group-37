package controller;

import model.User;
import view.enums.messages.TradeMenuMessages;

import java.util.regex.Matcher;

public class TradeMenuController {
    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public TradeMenuMessages requestTrade(Matcher matcher) {
        return null;
    }

    public TradeMenuMessages showTradeList() {
        return null;
    }

    public TradeMenuMessages acceptTrade(Matcher matcher) {
        return null;
    }

    public TradeMenuMessages showTradeHistory() {
        return null;
    }
}
