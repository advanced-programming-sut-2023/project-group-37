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
        return this.senderUsername;
    }

    public Image getSenderAvatar() {
        return this.senderAvatar;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public ArrayList<Image> getReactions() {
        return this.reactions;
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
