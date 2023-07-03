package model.chat;

import model.user.User;

import java.util.ArrayList;
import java.util.Arrays;

public class Chat {
    private static int idCounter;
    private final int id;
    private final User owner;
    private final ChatType type;
    private final ArrayList<User> subscribers;
    private final ArrayList<ChatMessage> messages;

    static {
        idCounter = 0;
    }

    public enum ChatType {
        PRIVATE, ROOM, PUBLIC
    }

    public Chat(User owner, ChatType type, User... subscribers) {
        this.id = idCounter++;
        this.owner = owner;
        this.type = type;
        this.subscribers = new ArrayList<>();
        this.subscribers.addAll(Arrays.asList(subscribers));
        this.messages = new ArrayList<>();
    }

    public Chat(User owner, ChatType type) {
        this.id = idCounter++;
        this.owner = owner;
        this.type = type;
        this.subscribers = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public int getId() {
        return this.id;
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

    public void addSubscriber(User user) {
        this.subscribers.add(user);
    }

    public void removeSubscriber(User user) {
        this.subscribers.remove(user);
    }
}