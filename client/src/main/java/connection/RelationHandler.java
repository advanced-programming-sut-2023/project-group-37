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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    private final ArrayList<Chat> privateChats;
    private final ArrayList<Chat> rooms;

    private RelationHandler() {
        this.privateChats = new ArrayList<>();
        this.rooms = new ArrayList<>();
    }

    public static RelationHandler getInstance() {
        return instance;
    }

    private Pane createMessagePane(ChatMessage chatMessage) {
        Pane messagePane = new Pane();
        messagePane.setPrefHeight(30);
        messagePane.setPrefWidth(200);
        Label contentLabel = new Label(chatMessage.getMessage());
        Label timeLabel = new Label(chatMessage.getFormattedTimeSent());
        timeLabel.setLayoutX(100);
        timeLabel.setLayoutY(25);
        contentLabel.setLayoutY(5);
        if (User.getCurrentUser().getUsername().equals(chatMessage.getSenderUsername())) {
            contentLabel.setBackground(Background.fill(Color.BLUE));
            contentLabel.setLayoutX(70);
        }
        else {
            contentLabel.setBackground(Background.fill(Color.GREEN));
            contentLabel.setLayoutX(5);
        }
        messagePane.getChildren().add(contentLabel);
        messagePane.getChildren().add(timeLabel);

        return messagePane;
    }

    private void setPublicChat(Chat publicChat) {
        this.publicChat = publicChat;
        if (this.publicChatVBox.getChildren().size() > 0)
            this.publicChatVBox.getChildren().subList(0, this.publicChatVBox.getChildren().size()).clear();

        for (ChatMessage chatMessage : this.publicChat.getMessages())
            this.publicChatVBox.getChildren().add(this.createMessagePane(chatMessage));
    }

    private void setCurrentRoom(Chat currentRoom) {
        this.currentRoom = currentRoom;
        if (this.roomVBox.getChildren().size() > 0)
            this.roomVBox.getChildren().subList(0, this.roomVBox.getChildren().size()).clear();

        for (ChatMessage chatMessage : this.currentRoom.getMessages())
            this.roomVBox.getChildren().add(this.createMessagePane(chatMessage));
    }

    private void setCurrentPrivateChat(Chat privateChat) {
        this.currentPrivateChat = privateChat;
        if (this.privateChatVBox.getChildren().size() > 0)
            this.privateChatVBox.getChildren().subList(0, this.privateChatVBox.getChildren().size()).clear();

        for (ChatMessage chatMessage : this.currentPrivateChat.getMessages())
            this.privateChatVBox.getChildren().add(this.createMessagePane(chatMessage));
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

    public Chat getChatById(int id) {
        for (Chat chat : this.privateChats) {
            if (chat.getId() == id)
                return chat;
        }
        return null;
    }

    public void removeChatById(int id) {
        this.privateChats.removeIf(privateChat -> privateChat.getId() == id);
        this.rooms.removeIf(room -> room.getId() == id);
    }

    public void handlePrivateChat(Chat privateChat) {
        this.removeChatById(privateChat.getId());
        this.privateChats.add(privateChat);
        User.getCurrentUser().joinChat(privateChat);

        if (this.currentPrivateChat.getId() == privateChat.getId())
            this.setCurrentPrivateChat(privateChat);
    }

    public void handleRoom(Chat room) {
        this.removeChatById(room.getId());
        this.rooms.add(room);
        User.getCurrentUser().joinChat(room);

        if (currentRoom.getId() == room.getId())
            this.setCurrentRoom(room);
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
        Pane messagePane = new Pane();
        messagePane.setPrefWidth(200);
        messagePane.setPrefHeight(30);
        Label contentLabel = new Label(content);
        contentLabel.setBackground(Background.fill(Color.BLUE));
        contentLabel.setLayoutX(70);
        contentLabel.setLayoutY(5);

        Label timeLabel = new Label(chatMessage.getFormattedTimeSent());
        timeLabel.setLayoutX(100);
        timeLabel.setLayoutY(25);

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

    public void setPublicChatVBox(VBox publicChatVBox) {
        this.publicChatVBox = publicChatVBox;
    }

    public void setPrivateChatVBox(VBox privateChatVBox) {
        this.privateChatVBox = privateChatVBox;
    }
}