package controller;

import model.game.Game;
import model.game.Government;
import model.game.Item;
import model.game.TradeRequest;
import view.enums.Message;

import java.util.regex.Matcher;

public class TradeMenuController {
    private static Government government;
    private static Game game;

    public static void setGovernment(Government government) {
        TradeMenuController.government = government;
    }

    public static void setGame(Game game) {
        TradeMenuController.game = game;
    }

    public String requestTrade(Matcher matcher) {
        Government receiver = game.getGovernmentByUsername(matcher.group("receiverUsername"));
        Item item = Item.getItemByName(matcher.group("resourceType"));
        int amount = Integer.parseInt(matcher.group("resourceAmount"));
        int price = Integer.parseInt(matcher.group("price"));
        String message = matcher.group("message");

        if (receiver == null)
            return Message.USERNAME_NOT_FOUND.toString();

        if (item == null)
            return Message.INVALID_ITEM_NAME.toString();

        if (amount < 1)
            return Message.INVALID_AMOUNT.toString();

        if (price < 0)
            return Message.INVALID_PRICE.toString();

        new TradeRequest(item, amount, government, receiver);

        //todo
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
