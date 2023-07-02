package connection;

import com.google.gson.Gson;
import connection.packet.*;
import model.user.User;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class QueryReceiver extends Thread {
    private final AuthorizationController authorizationController;
    private GamingController gamingController;
    private User user;
    private final Socket socket;
    private final DatabaseController databaseController;

    public QueryReceiver(Socket socket) throws IOException {
        this.authorizationController = new AuthorizationController(socket);
        this.socket = socket;
        this.databaseController = DatabaseController.getInstance();
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

            switch (type) {
                case LOGIN_PACKET -> this.handleLogin((LoginPacket) packet);
                case REGISTER_PACKET -> this.handleRegister((RegisterPacket) packet);
                case TILES_PACKET -> this.handleTiles((TilesPacket) packet);
            }
        }
    }

    private void handleTiles(TilesPacket tilesPacket) {
        this.gamingController.handleTiles(tilesPacket);
    }

    private void handleRegister(RegisterPacket registerPacket) {
        try {
            this.authorizationController.handleRegister(registerPacket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleLogin(LoginPacket loginPacket) {
        User user;
        try {
            if ((user = this.authorizationController.handleLogin(loginPacket)) != null) {
                this.user = user;
                try {
                    this.databaseController.addConnectedUser(user, this.socket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                this.gamingController = new GamingController(user);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
