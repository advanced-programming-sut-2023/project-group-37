package connection;

import com.google.gson.Gson;
import connection.packet.game.TilesPacket;
import connection.packet.registration.UserPacket;
import connection.packet.TilesPacket;
import connection.packet.UserPacket;
import model.chat.Chat;
import model.game.Tile;
import model.user.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseController {

    private static final DatabaseController CLIENTS_CONTROLLER;
    private final ArrayList<User> connectedUsers;
    private final HashMap<User, DataInputStream> dataInputStreams;
    private final HashMap<User, DataOutputStream> dataOutputStreams;
    private final Gson gson;

    static {
        DATABASE_CONTROLLER = new DatabaseController();
    }

    private DatabaseController() {
        this.connectedUsers = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.dataInputStreams = new HashMap<>();
        this.dataOutputStreams = new HashMap<>();
        this.gson = new Gson();
    }

    public static DatabaseController getInstance() {
        return DatabaseController.DATABASE_CONTROLLER;
    }

    public void addConnectedUser(User user, Socket socket) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.connectedUsers.add(user);
        this.dataOutputStreams.put(user, dataOutputStream);
        this.dataInputStreams.put(user, new DataInputStream(socket.getInputStream()));
        dataOutputStream.writeUTF(new UserPacket(user).toJson());
    }

    public void addRoom(Chat room) {
        this.rooms.add(room);
    }

    public DataOutputStream getUserDataOutputStream(User user) {
        if (!this.dataOutputStreams.containsKey(user)) {
            // TODO: should change later...
            System.out.println("error");
            return null;
        }
        return this.dataOutputStreams.get(user);
    }

    public void updateEveryOneTilesExcept(ArrayList<Tile> modifiedTiles, User user) throws IOException {
        for (User connectedUser : this.connectedUsers) {
            if (connectedUser != user)
                this.dataOutputStreams.get(user).writeUTF(this.gson.toJson(new TilesPacket(modifiedTiles)));
        }
    }

    public void disconnectUser(User user) {
        if (connectedUsers.contains(user)) {
            connectedUsers.remove(user);
            dataInputStreams.remove(user);
            dataOutputStreams.remove(user);
        }
    }
}
