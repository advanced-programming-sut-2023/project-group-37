package view.menus;

import connection.Connection;
import connection.packet.relation.SearchPacket;
import connection.RelationHandler;
import controller.AppController;
import controller.MultiMenuFunctions;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
        this.relationHandler.setPublicChatVBox(publicChatVBox);
        this.relationHandler.setPrivateChatVBox(privateChatVBox);
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
    }

    public void addFriend() {
    }

    public void search() {
        User foundUser = null;
        setAvatar(foundUser);
    }
    public void search(MouseEvent mouseEvent) throws IOException {
        Connection.getInstance().getDataOutputStream().writeUTF(new SearchPacket(friendName.getText()).toJson());

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
