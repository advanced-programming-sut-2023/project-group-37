package model.game;

import java.util.ArrayList;

public class TradeRequest {

    private final static ArrayList<TradeRequest> requests;
    private final Item item;
    private final int itemAmount;
    private final int price;
    private boolean isDone;
    private final String senderMessage;
    private String receiverMessage;
    private final Government sender;
    private final Government receiver;
    private final int time;//It's the number of turn in which the trade is sent!

    static {
        requests = new ArrayList<>();
    }

    public TradeRequest(Item item, int itemAmount, int price, String senderMessage, Government sender,
                        Government receiver, int time) {
        this.item = item;
        this.itemAmount = itemAmount;
        this.price = price;
        this.senderMessage = senderMessage;
        this.sender = sender;
        this.receiver = receiver;
        this.isDone = false;
        this.time = time;
        requests.add(this);
    }

    public int getTime() {
        return time;
    }

    public Item getItem() {
        return this.item;
    }

    public int getItemAmount() {
        return this.itemAmount;
    }

    public Government getSender() {
        return sender;
    }

    public Government getReceiver() {
        return receiver;
    }

    public boolean isDone() {
        return isDone;
    }

    public String getReceiverMessage() {
        return receiverMessage;
    }

    public static ArrayList<TradeRequest> getRequestsByReceiver(Government receiver) {
        ArrayList<TradeRequest> requests = new ArrayList<>();

        for (TradeRequest request : TradeRequest.requests) {
            if (request.receiver == receiver)
                requests.add(request);
        }

        return requests;
    }

    public static ArrayList<TradeRequest> getRequestsBySender(Government sender) {
        ArrayList<TradeRequest> requests = new ArrayList<>();

        for (TradeRequest request : TradeRequest.requests) {
            if (request.sender == sender)
                requests.add(request);
        }
        return requests;
    }


    public boolean doTrade(String receiverMessage) {
        this.receiverMessage = receiverMessage;

        if (price == 0) {
            if (sender.getItemAmount(item) < itemAmount)
                return false;

            sender.removeItem(item, itemAmount);
            receiver.addItem(item, itemAmount);
        }
        else {
            if (receiver.getItemAmount(item) < itemAmount)
                return false;

            if (sender.getGold() < price)
                return false;

            sender.addItem(item, itemAmount);
            receiver.removeItem(item, itemAmount);
            sender.setGold(sender.getGold() - price);
            receiver.setGold(receiver.getGold() + price);
        }

        isDone = true;
        return true;
    }

    public int getPrice() {
        return price;
    }

    public String getSenderMessage() {
        return senderMessage;
    }
}