package model.chat;

import model.user.User;

import java.util.HashMap;

public class Lobby {
    private static int idCounter;
    private final int id;
    private final User admin;
    private final int capacity;
    private final HashMap<User, Integer> users;
    private final Chat room;
    private final boolean isPublic;
    private int territoryCount;

    static {
        idCounter = 0;
    }

    public Lobby(User admin, int capacity, boolean isPublic) {
        this.id = idCounter++;
        this.admin = admin;
        this.capacity = capacity;
        this.users = new HashMap<>();
        this.territoryCount = 0;
        this.users.put(admin, ++territoryCount);
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

    public HashMap<User, Integer> getUsers() {
        return this.users;
    }

    public Chat getRoom() {
        return room;
    }

    public void addUser(User user) {
        this.users.put(user, ++this.territoryCount);
        this.room.addSubscriber(user);
    }

    public void removeMember (User user) {
        this.users.remove(user);
        this.room.removeSubscriber(user);
    }

    public boolean isPublic() {
        return isPublic;
    }
}