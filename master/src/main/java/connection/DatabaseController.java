package connection;

import com.google.gson.Gson;
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
        CLIENTS_CONTROLLER = new DatabaseController();
    }

    private DatabaseController() {
        this.connectedUsers = new ArrayList<>();
        this.dataInputStreams = new HashMap<>();
        this.dataOutputStreams = new HashMap<>();
        this.gson = new Gson();
    }

    public static DatabaseController getInstance() {
        return DatabaseController.CLIENTS_CONTROLLER;
    }

    public void addConnectedUser(User user, Socket socket) throws IOException {
        this.connectedUsers.add(user);
        this.dataOutputStreams.put(user, new DataOutputStream(socket.getOutputStream()));
        this.dataInputStreams.put(user, new DataInputStream(socket.getInputStream()));
        new DataOutputStream(socket.getOutputStream()).writeUTF(gson.toJson(User.getUsers()));
    }

    public void updateEveryOneTilesExcept(ArrayList<Tile> modifiedTiles, User user) throws IOException {
        for (User connectedUser : this.connectedUsers) {
            if (connectedUser != user)
                this.dataOutputStreams.get(user).writeUTF(this.gson.toJson(new TilesPacket(modifiedTiles)));
        }
    }
}
