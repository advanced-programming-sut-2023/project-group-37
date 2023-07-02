package model;

import model.user.User;

import java.util.ArrayList;

public abstract class Chat {
    private final ArrayList<User> subscribers;
    private final ArrayList<ChatMessage> messages;
    private final User owner;

    public Chat(ArrayList<User> subscribers, ArrayList<ChatMessage> messages, User owner) {
        this.subscribers = subscribers;
        this.messages = messages;
        this.owner = owner;
    }

    public ArrayList<User> getSubscribers() {
        return this.subscribers;
    }

    public ArrayList<ChatMessage> getMessages() {
        return this.messages;
    }

    public User getOwner() {
        return this.owner;
    }
}
