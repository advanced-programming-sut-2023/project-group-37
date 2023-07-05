package connection;

import com.google.gson.Gson;
import connection.packet.*;
import connection.packet.game.*;
import connection.packet.registration.UserPacket;
import connection.packet.relation.ChatPacket;
import connection.packet.relation.FoundUserPacket;
import connection.packet.relation.FriendRequestPacket;
import connection.packet.relation.LobbyPacket;
import controller.AppController;
import controller.GameController;
import controller.MultiMenuFunctions;
import javafx.application.Platform;
import model.chat.Chat;
import model.game.Game;
import model.game.GameColor;
import model.game.Government;
import model.game.Map;
import model.user.User;
import view.enums.Result;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

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
                case REFRESH_LOBBY_PACKET -> this.handleRefreshLobby(gson.fromJson(data, RefreshLobbyPacket.class));
                case LOBBIES_PACKET -> this.receiveLobbies(gson.fromJson(data, LobbiesPacket.class));
                case JOINED_LOBBY_PACKET -> this.joinToLobby(gson.fromJson(data, JoinedLobbyPacket.class));
                case START_PACKET -> this.startGame();
            }
        }
    }

    private void startGame() {
        Platform.runLater(() -> {
            ArrayList<Government> governments = new ArrayList<>();
            Map map = Map.getMaps().get(0);

            int index = 1;
            for (User player : this.relationHandler.getCurrentLobby().getUsers()) {
                governments.add(new Government(player, GameColor.values()[index - 1], map, index));
                index++;
            }

            Game game = new Game(map, governments);
            GameController.getInstance().setCurrentGame(game);

            try {
                appController.runMenu(Result.ENTER_GAME_MENU);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void joinToLobby(JoinedLobbyPacket joinedLobbyPacket) {
        this.relationHandler.setCurrentLobby(joinedLobbyPacket.getLobby());
        this.appController.createLobby();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.relationHandler.refreshLobbyUsers();
    }

    private void receiveLobbies(LobbiesPacket lobbiesPacket) {
        relationHandler.setLobbies(lobbiesPacket.getLobbies());
    }

    private void handleRefreshLobby(RefreshLobbyPacket refreshLobbyPacket) {
        this.relationHandler.setCurrentLobby(refreshLobbyPacket.getLobby());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.relationHandler.refreshLobbyUsers();
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
        this.appController.createLobby();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.relationHandler.refreshLobbyUsers();
    }

    private void handleTiles(TilesPacket tilesPacket) {

    }

    public void setStayLoggedIn(boolean stayLoggedIn) {
        this.stayLoggedIn = stayLoggedIn;
    }
}
