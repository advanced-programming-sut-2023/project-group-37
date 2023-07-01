package connection;

import com.google.gson.Gson;
import model.game.Tile;
import model.user.User;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Database {
    private static final Database DATABASE;
    private final ArrayList<User> connectedUsers;
    private final ArrayList<Socket> sockets;
    private final Gson gson;
    static {
        DATABASE = new Database();
    }

    private Database() {
        this.connectedUsers = new ArrayList<>();
        this.sockets = new ArrayList<>();
        this.gson = new Gson();
    }

    public static Database getInstance() {
        return Database.DATABASE;
    }

    public void addConnectedUser(User user, Socket socket) {
        this.connectedUsers.add(user);
        this.sockets.add(socket);
    }

    public void updateEveryOneTiles(ArrayList<Tile> modifiedTiles) throws IOException {
        for (Socket socket : this.sockets)
            new DataOutputStream(socket.getOutputStream()).writeUTF(gson.toJson(modifiedTiles));
    }
}
