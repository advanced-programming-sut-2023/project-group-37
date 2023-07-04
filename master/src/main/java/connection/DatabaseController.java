package connection;

import com.google.gson.Gson;
import connection.packet.game.TilesPacket;
import connection.packet.registration.UserPacket;
import connection.packet.relation.ChatPacket;
import model.chat.Chat;
import model.chat.Lobby;
import model.game.Tile;
import model.user.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseController {
    private static final DatabaseController DATABASE_CONTROLLER;
    private final ArrayList<Session> currentSessions;
    private final HashMap<User, DataInputStream> dataInputStreams;
    private final HashMap<User, DataOutputStream> dataOutputStreams;
    private final ArrayList<Chat> rooms;
    private final ArrayList<Lobby> lobbies;
    private Chat publicChat;
    private final Gson gson;

    static {
        DATABASE_CONTROLLER = new DatabaseController();
    }

    private DatabaseController() {
        this.currentSessions = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.lobbies = new ArrayList<>();
        this.publicChat = new Chat(null, Chat.ChatType.PUBLIC);
        this.dataInputStreams = new HashMap<>();
        this.dataOutputStreams = new HashMap<>();
        this.gson = new Gson();
    }

    public static DatabaseController getInstance() {
        return DatabaseController.DATABASE_CONTROLLER;
    }

    public ArrayList<Session> getCurrentSessions() {
        return this.currentSessions;
    }

    public Session getSessionByUser(User user) {
        for (Session session : this.currentSessions)
            if (session.getUser() == user)
                return session;
        return null;
    }

    public synchronized void addSession(User user, Socket socket) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.publicChat.addSubscriber(user);
        for (Session session : this.currentSessions) {
            this.dataOutputStreams.get(session.getUser()).writeUTF(new ChatPacket(publicChat).toJson());
        }

        this.currentSessions.add(new Session(user));
        this.dataOutputStreams.put(user, dataOutputStream);
        this.dataInputStreams.put(user, new DataInputStream(socket.getInputStream()));
        dataOutputStream.writeUTF(new UserPacket(user).toJson());
    }

    public void addRoom(Chat room) {
        this.rooms.add(room);
    }

    public void addLobby(Lobby lobby) {
        this.lobbies.add(lobby);
    }

    public User getConnectedUser(String username) {
        for (Session session : this.currentSessions) {
            if (session.getUser().getUsername().equals(username))
                return session.getUser();
        }
        return null;
    }

    public DataOutputStream getUserDataOutputStream(String username) {
        return this.dataOutputStreams.get(this.getConnectedUser(username));
    }

    public void updateEveryOneTilesExcept(ArrayList<Tile> modifiedTiles, User user) throws IOException {
        for (Session session : this.currentSessions)
            if (session.getUser() != user)
                this.dataOutputStreams.get(user).writeUTF(this.gson.toJson(new TilesPacket(modifiedTiles)));
    }

    public void endSession(User user) {
        for (Session session : currentSessions)
            if (session.getUser().equals(user)) {
                currentSessions.remove(session);
                dataInputStreams.remove(user);
                dataOutputStreams.remove(user);
            }
    }

    public Chat getPublicChat() {
        return publicChat;
    }

    public Chat getRoomById(int id) {
        for (Chat room : this.rooms) {
            if (room.getId() == id)
                return room;
        }
        return null;
    }

    public ArrayList<Lobby> getLobbies() {
        return lobbies;
    }

    public Lobby getLobbyById(int id) {
        for (Lobby lobby : this.lobbies) {
            if (lobby.getId() == id)
                return lobby;
        }
        return null;
    }

    public void joinAtRoom(User user, int id) {
        Chat room;
        if ((room = this.getRoomById(id)) != null)
            room.addSubscriber(user);
    }

    public void joinAtLobby(User user, int id) {
        Lobby lobby;
        if ((lobby = this.lobbies.get(id)) != null)
            lobby.addMember(user);
    }

    public void updatePublicChat(Chat publicChat) {
        this.publicChat = publicChat;
        for (Session session : this.currentSessions) {
            try {
                this.dataOutputStreams.get(session.getUser()).writeUTF(new ChatPacket(publicChat).toJson());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
