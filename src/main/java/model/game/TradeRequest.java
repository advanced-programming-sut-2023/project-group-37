package model.game;

import controller.TradeMenuController;

import java.util.ArrayList;

public class TradeRequest {

    private final static ArrayList<TradeRequest> requests = new ArrayList<>();
    private final Item item;
    private final int itemAmount;
    private final int price;
    private final String message;
    private final Government sender;
    private final Government receiver;

    public TradeRequest(Item item, int itemAmount, int price, String message, Government sender, Government receiver) {
        this.item = item;
        this.itemAmount = itemAmount;
        this.price = price;
        this.message = message;
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
            if (request.receiver == receiver)
                requests.add(request);
        }

        return requests;
    }

    public void doTrade() {
        //todo : handle
        requests.remove(this);
    }

    public int getPrice() {
        return price;
    }

    public String getMessage() {
        return message;
    }
}