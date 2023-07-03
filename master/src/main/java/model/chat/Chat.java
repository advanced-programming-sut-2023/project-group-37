package model.chat;

import model.user.User;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Chat {
    private final User owner;
    private final ChatType type;
    private final ArrayList<User> subscribers;
    private final ArrayList<ChatMessage> messages;

    public enum ChatType {
        PRIVATE, ROOM, PUBLIC
    }

    public Chat(User owner, ChatType type, User... subscribers) {
        this.owner = owner;
        this.type = type;
        this.subscribers = new ArrayList<>();
        this.subscribers.addAll(Arrays.asList(subscribers));
        this.messages = new ArrayList<>();
    }

    public User getOwner() {
        return this.owner;
    }

    public ChatType getType() {
        return this.type;
    }

    public ArrayList<User> getSubscribers() {
        return this.subscribers;
    }

    public ArrayList<ChatMessage> getMessages() {
        return this.messages;
    }
}