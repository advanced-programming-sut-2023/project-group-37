package model.chat;

import model.user.User;

import java.util.ArrayList;

public class Lobby {
    private final String id;
    private final User admin;
    private final int capacity;
    private final ArrayList<User> members;
    private final Chat room;
    private final boolean isPublic;

    public Lobby(String id, User admin, int capacity, ArrayList<User> members, Chat room, boolean isPublic) {
        this.id = id;
        this.admin = admin;
        this.capacity = capacity;
        this.members = members;
        this.room = room;
        this.isPublic = isPublic;
    }

    public User getAdmin() {
        return this.admin;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public ArrayList<User> getMembers() {
        return this.members;
    }

    public Chat getRoom() {
        return room;
    }

    public void addMember(User user) {
        this.members.add(user);
        this.room.addSubscriber(user);
    }

    public void removeMember (User user) {
        this.members.remove(user);
        this.room.removeSubscriber(user);
    }
}