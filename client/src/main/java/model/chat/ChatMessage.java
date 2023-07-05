package model.chat;

import model.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatMessage {
    private static int idCounter;
    private static final DateTimeFormatter formatter;
    private final int id;
    private transient final User sender;
    private final String timeSent;
    private String message;
    private MessageState state;

    static {
        idCounter = 0;
        formatter = DateTimeFormatter.ofPattern("HH:mm");
    }

    public ChatMessage(User sender, String message) {
        this.id = idCounter++;
        this.sender = sender;
        this.timeSent = LocalDateTime.now().toString();
        this.message = message;
        this.state = MessageState.SENDING;
    }

    public int getId() {
        return this.id;
    }

    public User getSender() {
        return this.sender;
    }

    public String getSenderUsername() {
        return this.sender.getUsername();
    }

    public String getTimeSent() {
        return this.timeSent;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageState getState() {
        return this.state;
    }

    public void setState(MessageState state) {
        this.state = state;
    }

    public String getFormattedTimeSent() {
        return formatter.format(LocalDateTime.parse(this.timeSent));
    }
}