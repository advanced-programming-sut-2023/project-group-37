package model.game;

public class TradeRequest {

    private final Item item;
    private final int itemCount;
    private final Government sender;
    private final Government receiver;

    public TradeRequest(Item item, int count, Government sender, Government receiver) {
        this.item = item;
        this.itemCount = count;
        this.sender = sender;
        this.receiver = receiver;
    }

    public Item getItem(){
        return this.item;
    }

    public int getItemCount() {
        return this.itemCount;
    }

    public Government getSender() {
        return sender;
    }

    public Government getReceiver() {
        return receiver;
    }
}