package model.game;

import java.util.ArrayList;

public class TradeRequest {

    private final static ArrayList<TradeRequest> requests = new ArrayList<>();
    private final Item item;
    private final int itemAmount;
    private final int price;
    private boolean isDone = false;
    private final String senderMessage;
    private String receiverMessage;
    private final Government sender;
    private final Government receiver;

    public TradeRequest(Item item, int itemAmount, int price, String senderMessage, Government sender, Government receiver) {
        this.item = item;
        this.itemAmount = itemAmount;
        this.price = price;
        this.senderMessage = senderMessage;
        this.sender = sender;
        this.receiver = receiver;
        requests.add(this);
    }

    public Item getItem(){
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

    public static ArrayList<TradeRequest> getRequestsByReceiver(Government receiver) {
        ArrayList<TradeRequest> requests = new ArrayList<>();

        for (TradeRequest request : TradeRequest.requests) {
            if (request.receiver == receiver && !request.isDone)
                requests.add(request);
        }

        return requests;
    }

    public boolean doTrade(String receiverMessage) {
        this.receiverMessage = receiverMessage;
        //todo : handle
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