package model.chat;

import model.game.Territory;
import model.user.User;

import java.util.HashMap;

public class Lobby {
    private static int idCounter;
    private final int id;
    private final User admin;
    private final int capacity;
    private final HashMap<User, Territory> governments;
    private final Chat room;
    private final boolean isPublic;

    static {
        idCounter = 0;
    }

    public Lobby(User admin, int capacity, boolean isPublic) {
        this.id = idCounter++;
        this.admin = admin;
        this.capacity = capacity;
        this.governments = new HashMap<>();
        this.room = new Chat(admin, Chat.ChatType.ROOM);
        this.isPublic = isPublic;
    }

    public int getId() {
        return this.id;
    }

    public User getAdmin() {
        return this.admin;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public HashMap<User, Territory> getGovernments() {
        return this.governments;
    }

    public Chat getRoom() {
        return room;
    }

    public void addGovernment(User user, Territory territory) {
        this.governments.put(user, territory);
        this.room.addSubscriber(user);
    }

    public void removeMember (User user) {
        this.governments.remove(user);
        this.room.removeSubscriber(user);
    }
}