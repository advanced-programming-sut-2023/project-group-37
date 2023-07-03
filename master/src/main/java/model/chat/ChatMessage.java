package model.chat;

import model.user.User;

public class ChatMessage {
    private final String senderUsername;
    private String message;
    private MessageState state;

    public ChatMessage(User sender, String message) {
        this.senderUsername = sender.getUsername();
        this.message = message;
        this.state = MessageState.SENDING;
    }

    public String getSenderUsername() {
        return this.senderUsername;
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
}
