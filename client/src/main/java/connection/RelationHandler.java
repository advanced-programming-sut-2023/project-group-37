package connection;

import connection.packet.relation.ChatPacket;
import connection.packet.relation.FriendRequestPacket;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    private VBox currentRoomVBox;
    private Chat currentPrivateChat;
    private VBox currentPrivateChatVBox;
    private Chat publicChat;
    private VBox publicChatVBox;
    private final ArrayList<Chat> privateChats;
    private final ArrayList<Chat> rooms;

    private RelationHandler() {
        this.privateChats = new ArrayList<>();
        this.rooms = new ArrayList<>();
    }

    public static RelationHandler getInstance() {
        return instance;
    }

    public Lobby getCurrentLobby() {
        return currentLobby;
    }

    public void setCurrentLobby(Lobby currentLobby) {
        this.currentLobby = currentLobby;
    }

    public void setPublicChat(Chat chat) {
        this.publicChat = chat;
        User.getCurrentUser().joinChat(chat);
    }

    public Chat getPublicChat() {
        return publicChat;
    }

    public Chat getChatById(int id) {
        for (Chat chat : this.privateChats) {
            if (chat.getId() == id)
                return chat;
        }
        return null;
    }

    public void sendMessage() {

    }

    public void removeChatById(int id) {
        this.privateChats.removeIf(privateChat -> privateChat.getId() == id);
        this.rooms.removeIf(room -> room.getId() == id);
    }

    public void handlePrivateChat(Chat privateChat) {
        this.removeChatById(privateChat.getId());
        this.privateChats.add(privateChat);
        User.getCurrentUser().joinChat(privateChat);
    }

    public void handleRoom(Chat room) {
        this.removeChatById(room.getId());
        this.rooms.add(room);
        User.getCurrentUser().joinChat(room);
    }

    public void handleRequest(FriendRequestPacket friendRequestPacket) {
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
                User.getCurrentUser().addFriend(friendRequestPacket.getSender());
                stage.close();
            });

            Button reject = new Button("Accept");
            reject.setBackground(Background.fill(Color.GREEN));
            reject.setLayoutX(40);
            reject.setLayoutY(100);

            reject.setOnMouseClicked((MouseEvent mouseEvent) -> stage.close());

            AnchorPane anchorPane = new AnchorPane(label, accept, reject);
            stage.setScene(new Scene(anchorPane));
            stage.show();
        });
    }

    public void sendMessage(String content, Chat.ChatType chatType) {
        ChatMessage chatMessage = new ChatMessage(User.getCurrentUser(), content);
        Rectangle rectangle = new Rectangle(200, 30);
        Label contentLabel = new Label(content);
        contentLabel.setBackground(Background.fill(Color.LIGHTBLUE));
        contentLabel.setLayoutX(70);
        contentLabel.setLayoutY(5);
        switch (chatType) {
            case ROOM -> {
                this.currentRoomVBox.getChildren().add(rectangle);
                this.currentRoom.addMessage(chatMessage);
                this.sendChatPacket(new ChatPacket(this.currentRoom));
            }

            case PRIVATE -> {
                this.currentPrivateChatVBox.getChildren().add(rectangle);
                this.currentPrivateChat.addMessage(chatMessage);
                this.sendChatPacket(new ChatPacket(this.currentPrivateChat));
            }

            case PUBLIC -> {
                this.publicChatVBox.getChildren().add(rectangle);
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

    public void setCurrentRoomVBox(VBox roomVBox) {
        this.currentRoomVBox = roomVBox;
    }
}