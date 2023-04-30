package model.game;

import java.util.ArrayList;

public class TradeRequest {

    private final static ArrayList<TradeRequest> requests = new ArrayList<>();
    private final Item item;
    private final int itemAmount;
    private final Government sender;
    private final Government receiver;

    public TradeRequest(Item item, int itemAmount, Government sender, Government receiver) {
        this.item = item;
        this.itemAmount = itemAmount;
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

    public void doTrade() {
        //todo : handle
        requests.remove(this);
    }
}