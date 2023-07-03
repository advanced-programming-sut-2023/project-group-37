package model.chat;

import model.chat.ChatMessage;
import model.user.User;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Chat {
    private final User owner;
    private final ArrayList<User> subscribers;
    private final ArrayList<ChatMessage> messages;

    public Chat(User owner, User... subscribers) {
        this.owner = owner;
        this.subscribers = new ArrayList<>();
        this.subscribers.addAll(Arrays.asList(subscribers));
        this.messages = new ArrayList<>();
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