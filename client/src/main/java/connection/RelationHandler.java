package connection;

import model.chat.Chat;
import model.chat.Lobby;

import java.util.ArrayList;

public class RelationHandler {
    private final static RelationHandler instance = new RelationHandler();
    private Lobby currentLobby;
    private Chat publicChat;
    private final ArrayList<Chat> privateChats;
    private final ArrayList<Chat> rooms;

    private RelationHandler() {
        this.privateChats = new ArrayList<>();
        this.rooms = new ArrayList<>();
    }

    public static RelationHandler getInstance() {
        return instance;
    }

    public Lobby getCurrentLobby() {
        return currentLobby;
    }

    public void setCurrentLobby(Lobby currentLobby) {
        this.currentLobby = currentLobby;
    }

    public void setPublicChat(Chat chat) {
        this.publicChat = chat;
    }

    public Chat getPublicChat() {
        return publicChat;
    }

    public Chat getChatById(int id) {
        for (Chat chat : this.privateChats) {
            if (chat.getId() == id)
                return chat;
        }
        return null;
    }

    public void removeChatById(int id) {
        this.privateChats.removeIf(privateChat -> privateChat.getId() == id);
        this.rooms.removeIf(room -> room.getId() == id);
    }

    public void handlePrivateChat(Chat privateChat) {
        this.removeChatById(privateChat.getId());
        this.privateChats.add(privateChat);
    }

    public void handleRoom(Chat room) {
        this.removeChatById(room.getId());
        this.rooms.add(room);
    }
}