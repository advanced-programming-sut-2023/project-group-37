package connection;

import connection.packet.game.LobbiesPacket;
import connection.packet.relation.AcceptRequest;
import connection.packet.relation.ChatPacket;
import connection.packet.relation.FriendRequestPacket;
import connection.packet.relation.SearchPacket;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.chat.Chat;
import model.chat.ChatMessage;
import model.chat.Lobby;
import model.user.User;

import java.io.IOException;
import java.util.ArrayList;

public class RelationHandler {
    private final static RelationHandler instance = new RelationHandler();
    private Lobby currentLobby;
    private Chat currentRoom;
    private VBox roomVBox;
    private Chat currentPrivateChat;
    private VBox privateChatVBox;
    private Chat publicChat;
    private VBox publicChatVBox;
    private Circle avatar;
    private User foundFriend;
    private VBox friendsVBox;
    private VBox lobbyNames;
    private VBox lobbyCapacities;
    private VBox lobbyAdmin;
    private VBox lobbyOthers;

    public void setLobbyNames(VBox lobbyNames) {
        this.lobbyNames = lobbyNames;
    }

    public void setLobbyCapacities(VBox lobbyCapacities) {
        this.lobbyCapacities = lobbyCapacities;
    }

    public void setLobbyAdmin(VBox lobbyAdmin) {
        this.lobbyAdmin = lobbyAdmin;
    }

    public void setLobbyOthers(VBox lobbyOthers) {
        this.lobbyOthers = lobbyOthers;
    }

    private final ArrayList<Chat> privateChats;
    private final ArrayList<Chat> rooms;

    public ArrayList<Lobby> getLobbies() {
        return lobbies;
    }

    public void setLobbies(ArrayList<Lobby> lobbies) {
        this.lobbies = lobbies;
    }

    private ArrayList<Lobby> lobbies;

    private RelationHandler() {
        this.privateChats = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.publicChat = new Chat(null, Chat.ChatType.PUBLIC);
    }

    public static RelationHandler getInstance() {
        return instance;
    }

    private Pane createMessagePane(ChatMessage chatMessage) {
        Pane messagePane = new Pane();
        messagePane.setPrefHeight(30);
        messagePane.setPrefWidth(400);
        Label contentLabel = new Label("   " + chatMessage.getMessage());
        Label timeLabel = new Label(chatMessage.getFormattedTimeSent());
        timeLabel.setLayoutY(15);
        contentLabel.setLayoutY(10);
        contentLabel.setPrefWidth(300);
        if (User.getCurrentUser().getUsername().equals(chatMessage.getSenderUsername())) {
            contentLabel.setBackground(Background.fill(Color.LIGHTPINK));
            contentLabel.setLayoutX(90);
            timeLabel.setLayoutX(60);
        } else {
            contentLabel.setBackground(Background.fill(Color.GREEN));
            contentLabel.setLayoutX(10);
            timeLabel.setLayoutX(320);
        }
        contentLabel.setStyle("-fx-font-size: 15");
        timeLabel.setStyle("-fx-font-size: 10");
        messagePane.getChildren().add(contentLabel);
        messagePane.getChildren().add(timeLabel);

        return messagePane;
    }

    private Pane createFriendViewPane(Chat privateChat) {
        User friend = privateChat.getSubscribers().get(0);
        if (friend.getUsername().equals(User.getCurrentUser().getUsername()))
            friend = privateChat.getSubscribers().get(1);

        Circle circle = new Circle(30);
        circle.setFill(new ImagePattern(friend.getAvatar()));
        circle.setLayoutY(40);
        circle.setLayoutX(40);

        Label nickName = new Label(friend.getNickName());
        nickName.setLayoutY(10);
        nickName.setLayoutX(50);
        nickName.setFont(new Font(20));

        Pane friendPane = new Pane(circle, nickName);
        friendPane.setOnMouseClicked((MouseEvent mouseEvent) -> this.setCurrentPrivateChat(privateChat));
        return friendPane;
    }

    private void setPublicChat(Chat publicChat) {
        Platform.runLater(() -> {
            try {
                this.publicChat = publicChat;
                if (this.publicChatVBox.getChildren().size() > 0)
                    this.publicChatVBox.getChildren().subList(0, this.publicChatVBox.getChildren().size()).clear();

                for (ChatMessage chatMessage : this.publicChat.getMessages())
                    this.publicChatVBox.getChildren().add(this.createMessagePane(chatMessage));
            } catch (Exception ignored) {
            }
        });
    }

    private void setCurrentRoom(Chat currentRoom) {
        Platform.runLater(() -> {
            try {
                this.currentRoom = currentRoom;
                if (this.roomVBox.getChildren().size() > 0)
                    this.roomVBox.getChildren().subList(0, this.roomVBox.getChildren().size()).clear();

                for (ChatMessage chatMessage : this.currentRoom.getMessages())
                    this.roomVBox.getChildren().add(this.createMessagePane(chatMessage));
            } catch (Exception ignored) {
            }
        });
    }

    private void addPrivateChat(Chat privateChat) {
        System.out.println("ADD PRIVATE CHAT");
        this.privateChats.add(privateChat);
        Platform.runLater(() -> {
            try {
                if (this.friendsVBox.getChildren().size() > 0)
                    this.friendsVBox.getChildren().subList(0, this.friendsVBox.getChildren().size()).clear();

                for (Chat chat : this.privateChats)
                    this.friendsVBox.getChildren().add(this.createFriendViewPane(chat));

            } catch (Exception ignored) {
            }
        });
    }

    private void setCurrentPrivateChat(Chat privateChat) {
        Platform.runLater(() -> {
            try {
                this.currentPrivateChat = privateChat;
                if (this.privateChatVBox.getChildren().size() > 0)
                    this.privateChatVBox.getChildren().subList(0, this.privateChatVBox.getChildren().size()).clear();

                for (ChatMessage chatMessage : this.currentPrivateChat.getMessages())
                    this.privateChatVBox.getChildren().add(this.createMessagePane(chatMessage));
            } catch (Exception ignored) {
            }
        });
    }

    public Lobby getCurrentLobby() {
        return currentLobby;
    }

    public void setCurrentLobby(Lobby currentLobby) {
        this.currentLobby = currentLobby;
        this.currentRoom = currentLobby.getRoom();
    }

    public void handlePublicChat(Chat chat) {
        this.publicChat = chat;
        User.getCurrentUser().joinChat(chat);
        this.setPublicChat(chat);
    }

    public Chat getPublicChat() {
        return publicChat;
    }

    public void removeChatById(int id) {
        this.privateChats.removeIf(privateChat -> privateChat.getId() == id);
        this.rooms.removeIf(room -> room.getId() == id);
    }

    public void handlePrivateChat(Chat privateChat) {
        this.removeChatById(privateChat.getId());
        this.addPrivateChat(privateChat);
        User.getCurrentUser().joinChat(privateChat);

        if (this.currentPrivateChat != null) {
            if (this.currentPrivateChat.getId() == privateChat.getId())
                this.setCurrentPrivateChat(privateChat);
        }
    }

    public void handleRoom(Chat room) {
        this.removeChatById(room.getId());
        this.rooms.add(room);
        User.getCurrentUser().joinChat(room);

        if (currentRoom.getId() == room.getId())
            this.setCurrentRoom(room);
    }

    public void handleFriendRequest(FriendRequestPacket friendRequestPacket) {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            Label label = new Label("A friend request from: " + friendRequestPacket.getSender().getUsername());
            label.setLayoutX(20);
            label.setLayoutY(20);

            Button accept = new Button("Accept");
            accept.setBackground(Background.fill(Color.GREEN));
            accept.setLayoutX(40);
            accept.setLayoutY(100);

            accept.setOnMouseClicked((MouseEvent mouseEvent) -> {
                try {
                    Connection.getInstance().getDataOutputStream().writeUTF(new AcceptRequest(User.getCurrentUser(),
                            friendRequestPacket.getSender()).toJson());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.close();
            });

            Button reject = new Button("Reject");
            reject.setBackground(Background.fill(Color.RED));
            reject.setLayoutX(100);
            reject.setLayoutY(100);

            reject.setOnMouseClicked((MouseEvent mouseEvent) -> stage.close());

            AnchorPane anchorPane = new AnchorPane(label, accept, reject);
            anchorPane.setPrefHeight(140);
            stage.setScene(new Scene(anchorPane));
            stage.show();
        });
    }

    public void sendMessage(String content, Chat.ChatType chatType) {
        ChatMessage chatMessage = new ChatMessage(User.getCurrentUser(), content);
        Pane messagePane = new Pane();
        messagePane.setPrefWidth(400);
        messagePane.setPrefHeight(30);
        Label contentLabel = new Label("   " + content);
        contentLabel.setBackground(Background.fill(Color.LIGHTPINK));
        contentLabel.setStyle("-fx-font-size: 15");
        contentLabel.setLayoutX(90);
        contentLabel.setLayoutY(10);
        contentLabel.setPrefWidth(300);

        Label timeLabel = new Label(chatMessage.getFormattedTimeSent());
        timeLabel.setLayoutX(60);
        timeLabel.setLayoutY(15);
        timeLabel.setStyle("-fx-font-size: 10");

        messagePane.getChildren().add(contentLabel);
        messagePane.getChildren().add(timeLabel);

        switch (chatType) {
            case ROOM -> {
                this.roomVBox.getChildren().add(messagePane);
                this.currentRoom.addMessage(chatMessage);
                this.sendChatPacket(new ChatPacket(this.currentRoom));
            }

            case PRIVATE -> {
                this.privateChatVBox.getChildren().add(messagePane);
                this.currentPrivateChat.addMessage(chatMessage);
                this.sendChatPacket(new ChatPacket(this.currentPrivateChat));
            }

            case PUBLIC -> {
                this.publicChatVBox.getChildren().add(messagePane);
                this.publicChat.addMessage(chatMessage);
                this.sendChatPacket(new ChatPacket(this.publicChat));
            }
        }
    }

    public void sendChatPacket(ChatPacket chatPacket) {
        try {
            Connection.getInstance().getDataOutputStream().writeUTF(chatPacket.toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setRoomVBox(VBox roomVBox) {
        this.roomVBox = roomVBox;
    }

    public void setAvatar(Circle avatar) {
        this.avatar = avatar;
    }

    public void setPublicChatVBox(VBox publicChatVBox) {
        this.publicChatVBox = publicChatVBox;
    }

    public void setPrivateChatVBox(VBox privateChatVBox) {
        this.privateChatVBox = privateChatVBox;
    }

    public void setFriendsVBox(VBox friendsVBox) {
        this.friendsVBox = friendsVBox;
    }

    public void handleSearch(String friendName) {
        try {
            Connection.getInstance().getDataOutputStream().writeUTF(new SearchPacket(friendName).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeAvatar(User friend) {
        this.avatar.setFill(new ImagePattern(friend.getAvatar()));
        this.foundFriend = friend;
    }

    public void sendFriendReq() {
        try {
            Connection.getInstance().getDataOutputStream().writeUTF(new FriendRequestPacket(User.getCurrentUser(), foundFriend).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendReqToGetLobbies() {
        try {
            Connection.getInstance().getDataOutputStream().writeUTF(new LobbiesPacket(null).toJson()); // null -> Its just a request
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showLobbies() {
        for (Lobby lobby : lobbies) {
            addLobbyToPain(lobby);
        }
    }

    private void addLobbyToPain(Lobby lobby) {
        Label name = new Label("" + lobby.getId());
        name.setStyle("-fx-font-size: 10");
        lobbyNames.getChildren().add(name);

        Label capacity = new Label("" + lobby.getCapacity());
        capacity.setStyle("-fx-font-size: 10");
        lobbyCapacities.getChildren().add(capacity);

        Label admin = new Label(lobby.getAdmin().getNickName());
        admin.setStyle("-fx-font-size: 10");
        lobbyCapacities.getChildren().add(admin);

        Label others = new Label(setOtherUsers());
        others.setStyle("-fx-font-size: 10");
        lobbyCapacities.getChildren().add(others);


    }

    private String setOtherUsers() {
        return null;
    }
}