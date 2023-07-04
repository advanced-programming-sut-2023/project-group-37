package connection;

import com.google.gson.Gson;
import connection.packet.*;
import connection.packet.game.TilesPacket;
import connection.packet.registration.UserPacket;
import connection.packet.relation.ChatPacket;
import connection.packet.relation.FoundUserPacket;
import connection.packet.relation.FriendRequestPacket;
import connection.packet.relation.LobbyPacket;
import controller.AppController;
import controller.MultiMenuFunctions;
import model.chat.Chat;
import model.user.User;

import java.io.DataInputStream;
import java.io.IOException;

public class NotificationReceiver extends Thread {
    private final AppController appController;
    private final DataInputStream dataInputStream;
    private final RelationHandler relationHandler;
    private boolean stayLoggedIn;

    public NotificationReceiver(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
        this.stayLoggedIn = false;
        this.appController = AppController.getInstance();
        this.relationHandler = RelationHandler.getInstance();
    }

    @Override
    public void run() {
        Gson gson = new Gson();
        String data;

        while (true) {
            try {
                data = this.dataInputStream.readUTF();
                System.out.println("Response got");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Packet packet = gson.fromJson(data, Packet.class);
            PacketType type = packet.getType();
            switch (type) {
                case POPUP_PACKET -> this.appController.handleAlert(gson.fromJson(data, PopUpPacket.class));
                case USER_PACKET -> this.login(gson.fromJson(data, UserPacket.class));
                case FRIEND_REQUEST_PACKET ->
                        this.relationHandler.handleFriendRequest(gson.fromJson(data, FriendRequestPacket.class));
                case LOBBY_PACKET -> this.createLobby(gson.fromJson(data, LobbyPacket.class));
                case CHAT_PACKET -> this.handleChat(gson.fromJson(data, ChatPacket.class));
                case TILES_PACKET -> this.handleTiles(gson.fromJson(data, TilesPacket.class));
                case FOUND_USER_PACKET -> this.handleFoundFriend(gson.fromJson(data, FoundUserPacket.class));
            }
        }
    }

    private void handleFoundFriend(FoundUserPacket foundUserPacket) {
        User friend = foundUserPacket.getFoundUser();
        relationHandler.changeAvatar(friend);
    }

    private void handleChat(ChatPacket chatPacket) {
        Chat chat = chatPacket.getChat();
        switch (chat.getType()) {
            case PUBLIC -> this.relationHandler.handlePublicChat(chat);
            case PRIVATE -> this.relationHandler.handlePrivateChat(chat);
            case ROOM -> this.relationHandler.handleRoom(chat);
        }
    }

    private void login(UserPacket userPacket) {
        MultiMenuFunctions.setAllCurrentUsers(userPacket.getUser());
        if (this.stayLoggedIn)
            User.setStayLoggedIn(userPacket.getUser());
    }

    private void createLobby(LobbyPacket lobbyPacket) {
        this.relationHandler.setCurrentLobby(lobbyPacket.getLobby());
        this.appController.createLobby(lobbyPacket);
    }

    private void handleTiles(TilesPacket tilesPacket) {

    }

    public void setStayLoggedIn(boolean stayLoggedIn) {
        this.stayLoggedIn = stayLoggedIn;
    }
}
