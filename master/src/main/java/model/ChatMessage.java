package model;

import javafx.scene.image.Image;
import model.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChatMessage {
    private final String senderUsername;
    private final Image senderAvatar;
    private final LocalDateTime time;
    private final ArrayList<Image> reactions;
    private String message;
    private MessageState state;

    public ChatMessage(User sender, String message) {
        this.senderUsername = sender.getUsername();
        this.senderAvatar = sender.getAvatar();
        this.time = LocalDateTime.now();
        this.reactions = new ArrayList<>();
        this.message = message;
        this.state = MessageState.SENDING;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public Image getSenderAvatar() {
        return senderAvatar;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public ArrayList<Image> getReactions() {
        return reactions;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageState getState() {
        return state;
    }

    public void setState(MessageState state) {
        this.state = state;
    }
}
