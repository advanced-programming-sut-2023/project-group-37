package connection;

import com.google.gson.Gson;
import connection.packet.*;
import connection.packet.game.TilesPacket;
import connection.packet.registration.LoginPacket;
import connection.packet.registration.RegisterPacket;
import connection.packet.relation.ChatPacket;
import connection.packet.relation.FriendRequestPacket;
import connection.packet.relation.LobbyPacket;
import connection.packet.relation.RequestLobbyPacket;
import model.chat.Lobby;
import model.chat.PublicChat;
import model.user.User;
import view.enums.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class QueryReceiver extends Thread {
    private final RegistrationController registrationController;
    private GamingController gamingController;
    private User user;
    private boolean isQueryAlive;
    private boolean isDead;
    private final Socket socket;
    private final DataOutputStream dataOutputStream;
    private final DatabaseController databaseController;

    public QueryReceiver(Socket socket) throws IOException {
        this.registrationController = new RegistrationController(socket);
        this.socket = socket;
        this.databaseController = DatabaseController.getInstance();
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.isQueryAlive = true;
        this.isDead = false;
    }

    private void handleFriendRequestPacket(FriendRequestPacket packet) {
        try {
            DatabaseController.getInstance().getUserDataOutputStream(packet.getSender()).writeUTF(packet.toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleChatPacket(ChatPacket packet) {
        // TODO: do we need to exclude the sender himself?
        // TODO: add try catch or anything to avoid NullPointerException...
        for (User subscriber : packet.getChat().getSubscribers()) {
            try {
                DatabaseController.getInstance().getUserDataOutputStream(subscriber).writeUTF(packet.toJson());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public synchronized void run() {
        try {
            this.dataOutputStream.writeUTF(new ChatPacket(PublicChat.getInstance()).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DataInputStream dataInputStream;
        try {
            dataInputStream = new DataInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();
        String data;

        while (!this.isDead) {
            try {
                data = dataInputStream.readUTF();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Packet packet = gson.fromJson(data, Packet.class);
            System.out.println(packet instanceof LoginPacket);
            PacketType type = packet.getType();
            System.out.println("Q : received");

            switch (type) {
                case LOGIN_PACKET -> this.handleLogin(gson.fromJson(data, LoginPacket.class));
                case REGISTER_PACKET -> this.handleRegister(gson.fromJson(data, RegisterPacket.class));
                case TILES_PACKET -> this.handleTiles(gson.fromJson(data, TilesPacket.class));
                case FRIEND_REQUEST_PACKET -> this.handleFriendRequestPacket(gson.fromJson(data, FriendRequestPacket.class));
                case CHAT_PACKET -> this.handleChatPacket(gson.fromJson(data, ChatPacket.class));
                case ALIVE_PACKET -> this.setQueryAlive(true);
                case LOGOUT_PACKET -> this.databaseController.disconnectUser(this.user);
                case REQUEST_LOBBY_PACKET -> this.createLobby(gson.fromJson(data, RequestLobbyPacket.class));
            }
        }
    }

    void setQueryAlive(boolean isQueryAlive) {
        this.isQueryAlive = true;
    }

    public boolean isQueryAlive() {
        return this.isQueryAlive;
    }

    private void handleTiles(TilesPacket tilesPacket) {
        this.gamingController.handleTiles(tilesPacket);
    }

    private void handleRegister(RegisterPacket registerPacket) {
        try {
            this.registrationController.handleRegister(registerPacket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleLogin(LoginPacket loginPacket) {
        System.out.println("Q : login handle");
        User user;
        try {
            if ((user = this.registrationController.handleLogin(loginPacket)) != null) {
                this.user = user;
                try {
                    this.databaseController.addConnectedUser(user, this.socket);
                    this.dataOutputStream.writeUTF(new PopUpPacket(Message.LOGIN_SUCCESSFUL, false).toJson());
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
                this.gamingController = new GamingController(user);
            }
            else this.dataOutputStream.writeUTF(new PopUpPacket(Message.CANT_LOGIN, true).toJson());

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createLobby(RequestLobbyPacket requestLobbyPacket) {
        try {
            this.dataOutputStream.writeUTF(new LobbyPacket(new Lobby(
                    this.user, requestLobbyPacket.getCapacity(), requestLobbyPacket.isPublic())).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    public User getUser() {
        return this.user;
    }
}
