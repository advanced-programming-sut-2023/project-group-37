package controller;

import model.game.Game;
import model.game.Government;
import model.game.Item;
import model.game.TradeRequest;
import view.enums.Message;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class TradeMenuController {
    private Government government;
    private static Game game;

    public void setGovernment(Government government) {
        this.government = government;
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

        new TradeRequest(item, amount, price, message, government, receiver);

        return Message.REQUEST_SENT.toString();
    }

    public String showTradeList() {
        StringBuilder message = new StringBuilder();
        int id = 1;
        for (TradeRequest request : TradeRequest.getRequestsByReceiver(government)) {
            message.append("Id: ").append(id).append(", Sender: ").append(request.getSender().getUsername())
                    .append(", Type: ").append(request.getItem().getName()).append(", Amount: ").append(request.getItemAmount())
                    .append(", Price: ").append(request.getPrice()).append("\n").append("Message: ")
                    .append(request.getSenderMessage()).append("\n");
        }

        return message.toString().trim();
    }

    public String acceptTrade(Matcher matcher) {
        int id = Integer.parseInt(matcher.group("id"));

        ArrayList<TradeRequest> requests = TradeRequest.getRequestsByReceiver(government);
        if (requests.size() < id || id < 1)
            return Message.INVALID_ID.toString();

        TradeRequest request = requests.get(id-1);
        if (!request.doTrade(matcher.group("message")))
            return Message.TRADE_FAILED.toString();

        return Message.TRADE_SUCCESS.toString();
    }

    public Message showTradeHistory() {
        return null;
    }
}
