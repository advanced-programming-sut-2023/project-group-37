package connection;

import com.google.gson.Gson;
import connection.packet.*;
import connection.packet.game.*;
import connection.packet.registration.LoginPacket;
import connection.packet.registration.RegisterPacket;
import connection.packet.registration.UserPacket;
import connection.packet.relation.*;
import controller.GameController;
import model.chat.Chat;
import model.chat.Lobby;
import model.game.Game;
import model.game.GameColor;
import model.game.Government;
import model.game.Map;
import model.user.User;
import view.enums.Message;
import view.enums.Result;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

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
                    friendRequestPacket.getReceiver().getUsername()).writeUTF(friendRequestPacket.toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void handleChatPacket(ChatPacket chatPacket) {
        if (chatPacket.getChat().getType() == Chat.ChatType.PUBLIC) {
            databaseController.updatePublicChat(chatPacket.getChat());
            return;
        }

        for (User subscriber : chatPacket.getChat().getSubscribers()) {
            if (!subscriber.getUsername().equals(this.user.getUsername())) {
                try {
                    DatabaseController.getInstance().getUserDataOutputStream(subscriber.getUsername()).writeUTF(chatPacket.toJson());
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
            PacketType type = packet.getType();

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
                case USER_PACKET -> this.updateTheUser(gson.fromJson(data, UserPacket.class));
                case LOBBIES_PACKET -> this.sendLobbies();
                case JOIN_REQUEST_PACKET -> this.handleJoining(gson.fromJson(data, JoinRequestPacket.class));
                case LEAVE_REQUEST_PACKET -> this.handleLeaveLobby(gson.fromJson(data, LeaveRequestPacket.class));
                case START_REQUEST_PACKET -> this.handleStartingGame(gson.fromJson(data, StartRequestPacket.class));
            }
        }
    }

    private synchronized void handleStartingGame(StartRequestPacket startRequestPacket){
        Lobby lobby = this.databaseController.getLobbyById(startRequestPacket.getLobbyId());
        if (lobby == null)
            return;

        if (lobby.getUsers().size() == 1) {
            try {
                this.dataOutputStream.writeUTF(new PopUpPacket(Message.CANT_START, true).toJson());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        for (User player : lobby.getUsers()) {
            try {
                this.databaseController.getUserDataOutputStream(player.getUsername())
                        .writeUTF(new StartGamePacket().toJson());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private synchronized void handleLeaveLobby(LeaveRequestPacket leaveRequestPacket) {
        Lobby lobby = this.databaseController.getLobbyById(leaveRequestPacket.getLobbyId());
        if (lobby == null)
            return;

        lobby.removeMember(this.user);
        if (lobby.getAdmin().getUsername().equals(this.user.getUsername())) {
            if (lobby.getUsers().size() > 0)
                lobby.setAdmin(lobby.getUsers().get(0));
            else this.databaseController.removeLobby(lobby);
        }

        for (User subscriber : lobby.getUsers()) {
            try {
                this.databaseController.getUserDataOutputStream(subscriber.getUsername()).writeUTF(new RefreshLobbyPacket(lobby).toJson());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private synchronized void handleJoining(JoinRequestPacket joinRequestPacket) {
        Lobby lobby = this.databaseController.getLobbyById(joinRequestPacket.getLobbyId());
        if (lobby == null)
            return;

        User sender = this.databaseController.getConnectedUser(joinRequestPacket.getSenderUsername());
        if (sender == null)
            return;

        if (lobby.getUsers().size() >= lobby.getCapacity()) {
            try {
                this.dataOutputStream.writeUTF(new PopUpPacket(Message.LOBBY_IS_FULL, true).toJson());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        lobby.addUser(sender);
        for (User player : lobby.getUsers()) {
            if (player != sender) {
                try {
                    this.databaseController.getUserDataOutputStream(player.getUsername()).writeUTF(new RefreshLobbyPacket(lobby).toJson());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        try {
            this.dataOutputStream.writeUTF(new JoinedLobbyPacket(lobby).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (lobby.getUsers().size() == lobby.getCapacity()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (User player : lobby.getUsers()) {
                try {
                    this.databaseController.getUserDataOutputStream(player.getUsername()).writeUTF(new StartGamePacket().toJson());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private synchronized void sendLobbies() {
        try {
            this.dataOutputStream.writeUTF(new LobbiesPacket(databaseController.getLobbies()).toJson());
        } catch (IOException e) {
            System.out.println("Couldn't send lobbies to client");
        }
    }

    private synchronized void updateTheUser(UserPacket userPacket) {
        for (Session currentSession : databaseController.getCurrentSessions()) {
            if (currentSession.getUser().getUsername().equals(userPacket.getUser().getUsername())) {
                currentSession.setUser(userPacket.getUser());
            }
        }
        User user = User.getUserByUsername(userPacket.getUser().getUsername());
        User.getUsers().remove(user);
        User.getUsers().add(userPacket.getUser());
        User.updateDatabase();
    }

    private synchronized void acceptReq(AcceptRequest acceptRequest) {
        System.out.println("ACCEPT REQ");
        Chat privateChat = new Chat(acceptRequest.getSender(), Chat.ChatType.PRIVATE, acceptRequest.getReceiver());
        try {
            this.dataOutputStream.writeUTF(new ChatPacket(privateChat).toJson());
            System.out.println(acceptRequest.getReceiver().getUsername());
            this.databaseController.getUserDataOutputStream(acceptRequest.getReceiver().getUsername()).writeUTF
                    (new ChatPacket(privateChat).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void updateChat(RequestChatPacket requestChatPacket) {
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
            }
            else {
                if (this.databaseController.getConnectedUser(loginPacket.getUsername()) == null)
                    this.dataOutputStream.writeUTF(new PopUpPacket(Message.CANT_LOGIN, true).toJson());
                else this.dataOutputStream.writeUTF(new PopUpPacket(Message.USER_IS_LOGIN, true).toJson());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void createLobby(RequestLobbyPacket requestLobbyPacket) {
        try {
            Lobby lobby = new Lobby(
                    this.user, requestLobbyPacket.getCapacity(), requestLobbyPacket.isPublic());

            this.databaseController.addLobby(lobby);
            this.dataOutputStream.writeUTF(new LobbyPacket(lobby).toJson());
            System.out.println(new LobbyPacket(new Lobby(
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
