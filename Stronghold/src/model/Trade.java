package model;

import model.enums.Resources;

public class Trade {
    private final int resourceAmount;
    private final Resources resource;
    private final Government sender;
    private final Government receiver;

    public Trade(int resourceAmount, Resources resource, Government sender, Government receiver) {
        this.resourceAmount = resourceAmount;
        this.resource = resource;
        this.sender = sender;
        this.receiver = receiver;
    }
    public int getResourceAmount() {
        return resourceAmount;
    }

    public Resources getResource() {
        return resource;
    }

    public Government getSender() {
        return sender;
    }

    public Government getReceiver() {
        return receiver;
    }
}
