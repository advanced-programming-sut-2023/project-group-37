package connection;

import com.google.gson.Gson;
import connection.packet.*;
import connection.packet.game.TilesPacket;
import connection.packet.registration.LoginPacket;
import connection.packet.registration.RegisterPacket;
import connection.packet.relation.*;
import model.chat.Chat;
import model.chat.Lobby;
import model.chat.PublicChat;
import model.user.User;
import view.enums.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

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

    private synchronized void handleFriendRequestPacket(FriendRequestPacket friendRequestPacket) {
        try {
            DatabaseController.getInstance().getUserDataOutputStream(
                    friendRequestPacket.getReceiver()).writeUTF(friendRequestPacket.toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void handleChatPacket(ChatPacket chatPacket) {
        if (chatPacket.getChat().getType() == Chat.ChatType.PUBLIC) {
            databaseController.updatePublicChat((PublicChat) chatPacket.getChat());
            return;
        }

        for (User subscriber : chatPacket.getChat().getSubscribers()) {
            if (!subscriber.getUsername().equals(this.user.getUsername())) {
                try {
                    DatabaseController.getInstance().getUserDataOutputStream(subscriber).writeUTF(chatPacket.toJson());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
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
                case FRIEND_REQUEST_PACKET -> this.handleFriendRequestPacket(gson.fromJson(data,
                        FriendRequestPacket.class));
                case CHAT_PACKET -> this.handleChatPacket(gson.fromJson(data, ChatPacket.class));
                case ALIVE_PACKET -> this.setQueryAlive(true);
                case LOGOUT_PACKET -> this.databaseController.endSession(this.user);
                case REQUEST_LOBBY_PACKET -> this.createLobby(gson.fromJson(data, RequestLobbyPacket.class));
                case SEARCH_PACKET -> this.searchFriend(gson.fromJson(data, SearchPacket.class));
                case REQ_UPDATE_CHAT -> this.updateChat(gson.fromJson(data, RequestChatPacket.class));
                case ACCEPT_PACKET -> this.acceptReq(gson.fromJson(data, AcceptRequest.class));
            }
        }
    }

    private void acceptReq(AcceptRequest acceptRequest) {
        Chat privateChat = new Chat(acceptRequest.getSender(), Chat.ChatType.PRIVATE, acceptRequest.getReceiver());
        try {
            this.dataOutputStream.writeUTF(new ChatPacket(privateChat).toJson());
            this.databaseController.getUserDataOutputStream(acceptRequest.getReceiver()).writeUTF
                    (new ChatPacket(privateChat).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private synchronized void updateChat(RequestChatPacket requestChatPacket) {
        System.out.println("REQ");
        switch (requestChatPacket.getChat().getType()) {
            case PUBLIC -> sendPublic();
            case PRIVATE -> sendPrivate();
            case ROOM -> sendRoom(requestChatPacket);
        }
    }

    private synchronized void sendRoom(RequestChatPacket requestChatPacket) {
        Chat wantedRoom = databaseController.getRoomById(requestChatPacket.getChat().getId());
        try {
            this.dataOutputStream.writeUTF(new ChatPacket(wantedRoom).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void sendPrivate() {

    }

    private synchronized void sendPublic() {
        try {
            this.dataOutputStream.writeUTF(new ChatPacket(databaseController.getPublicChat()).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private synchronized void searchFriend(SearchPacket searchPacket) {
        User friend;
        if ((friend = User.getUserByUsername(searchPacket.getUsername())) == null) {
            try {
                this.dataOutputStream.writeUTF(new PopUpPacket(Message.USER_NOT_EXISTS, true).toJson());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                this.dataOutputStream.writeUTF(new FoundUserPacket(friend).toJson());
            } catch (IOException e) {
                throw new RuntimeException(e);
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

    private synchronized void handleLogin(LoginPacket loginPacket) {
        System.out.println("Q : login handle");
        User user;
        try {
            if ((user = this.registrationController.handleLogin(loginPacket)) != null) {
                this.user = user;
                try {
                    this.databaseController.addSession(user, this.socket);
                    this.dataOutputStream.writeUTF(new PopUpPacket(Message.LOGIN_SUCCESSFUL, false).toJson());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                this.gamingController = new GamingController(user);
            } else this.dataOutputStream.writeUTF(new PopUpPacket(Message.CANT_LOGIN, true).toJson());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void createLobby(RequestLobbyPacket requestLobbyPacket) {
        try {
            this.dataOutputStream.writeUTF(new LobbyPacket(new Lobby(
                    this.user, requestLobbyPacket.getCapacity(), requestLobbyPacket.isPublic())).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    public User getUser() {
        return this.user;
    }
}
