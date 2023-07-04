package model.chat;

import model.user.User;

import java.util.ArrayList;

public class Lobby {
    private static int idCounter;
    private final int id;
    private final User admin;
    private final int capacity;
    private final ArrayList<User> members;
    private final Chat room;
    private final boolean isPublic;


    static {
        idCounter = 0;
    }

    public Lobby(User admin, int capacity, boolean isPublic) {
        this.id = idCounter++;
        this.admin = admin;
        this.capacity = capacity;
        this.members = new ArrayList<>();
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

    public void removeMember(User user) {
        this.members.remove(user);
        this.room.removeSubscriber(user);
    }
}