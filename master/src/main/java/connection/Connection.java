package connection;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import connection.packet.Packet;
import connection.packet.PacketType;
import controller.GameController;
import controller.viewControllers.MainMenuController;
import model.game.Map;
import model.game.Tile;
import model.user.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Connection extends Thread {
    private User user;
    private final Socket socket;
    private final DatabaseController databaseController;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.databaseController = DatabaseController.getInstance();
        new DataOutputStream(socket.getOutputStream()).writeUTF(new Gson().toJson(User.getUsers()));
    }

    @Override
    public synchronized void run() {
        DataInputStream dataInputStream;
        try {
            dataInputStream = new DataInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();
        String data;

        while (true) {
            try {
                data = dataInputStream.readUTF();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Packet packet = gson.fromJson(data, Packet.class);
            PacketType type = packet.getType();
        }
    }
}
