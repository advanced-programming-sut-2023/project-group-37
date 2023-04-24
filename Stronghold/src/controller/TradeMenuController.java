package controller;

import model.user.User;
import view.enums.Message;

import java.util.regex.Matcher;

public class TradeMenuController {
    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
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
