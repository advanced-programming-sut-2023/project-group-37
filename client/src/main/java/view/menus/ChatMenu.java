package view.menus;

import connection.Connection;
import connection.RelationHandler;
import connection.packet.relation.RequestChatPacket;
import controller.AppController;
import controller.MultiMenuFunctions;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.chat.Chat;
import model.user.User;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ChatMenu extends Application {
    private final AppController appController;
    private final RelationHandler relationHandler;
    @FXML
    private VBox privateChatVBox;
    @FXML
    private VBox friendsVBox;
    @FXML
    private VBox publicChatVBox;
    @FXML
    private TextField friendName;
    @FXML
    private Rectangle searchButton;
    @FXML
    private Circle avatar;
    @FXML
    private TextField privateChatBox;
    @FXML
    private Rectangle sendButtonPrivate;
    @FXML
    private TextField publicChatBox;
    @FXML
    private Rectangle sendButtonPublic;

    public ChatMenu() {
        this.appController = AppController.getInstance();
        this.relationHandler = RelationHandler.getInstance();

        try {
            Connection.getInstance().getDataOutputStream().writeUTF(new RequestChatPacket(relationHandler.getPublicChat()).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/chatMenu.fxml");
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(url));

        MultiMenuFunctions.setBackground(anchorPane, "main-menu-background.jpg");

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        searchButton.setFill(new ImagePattern(MultiMenuFunctions.getImageView("/Image/Button/search.jpg", 30).getImage()));
        sendButtonPrivate.setFill(new ImagePattern(MultiMenuFunctions.getImageView("/Image/Button/send.png", 30).getImage()));
        sendButtonPublic.setFill(new ImagePattern(MultiMenuFunctions.getImageView("/Image/Button/send.png", 30).getImage()));
        friendsVBox.setBackground(Background.fill(Color.WHITE));
        publicChatVBox.setBackground(Background.fill(Color.WHITE));
        privateChatVBox.setBackground(Background.fill(Color.WHITE));

        this.relationHandler.setPublicChatVBox(publicChatVBox);
        this.relationHandler.setPrivateChatVBox(privateChatVBox);
        this.relationHandler.setAvatar(avatar);
    }

    public void addFriend() { // TODO send reqChatPacket
        this.relationHandler.sendFriendReq();
    }

    public void search() {
        this.relationHandler.handleSearch(friendName.getText());
    }

    public void sendPrivate() {
        this.relationHandler.sendMessage(this.privateChatBox.getText(), Chat.ChatType.PRIVATE);
    }

    public void sendPublic() {
        this.relationHandler.sendMessage(this.publicChatBox.getText(), Chat.ChatType.PUBLIC);
    }

    public void setAvatar(User user) {
        avatar.setFill(new ImagePattern(user.getAvatar()));
    }
}
