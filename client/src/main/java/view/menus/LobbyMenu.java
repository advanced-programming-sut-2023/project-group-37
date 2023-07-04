package view.menus;

import connection.RelationHandler;
import controller.AppController;
import controller.MultiMenuFunctions;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.chat.Chat;

import java.net.URL;
import java.util.Objects;

public class LobbyMenu extends Application {
    private final AppController appController;
    private final RelationHandler relationHandler;
    @FXML
    private Label lobbyName1;
    @FXML
    private Rectangle sendButton;
    @FXML
    private TextField chatBox;
    @FXML
    private VBox chatVBox;
    @FXML
    private AnchorPane chatPane;
    @FXML
    private Label lobbyCapacity;
    @FXML
    private Label lobbyName;
    @FXML
    private VBox territories;
    @FXML
    private VBox usernames;

    public LobbyMenu() {
        this.appController = AppController.getInstance();
        this.relationHandler = RelationHandler.getInstance();
        this.relationHandler.setCurrentRoomVBox(this.chatVBox);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/lobbyMenu.fxml");
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(url));

        MultiMenuFunctions.setBackground(anchorPane, "registration-bg.jpg");

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        sendButton.setFill(new ImagePattern(MultiMenuFunctions.getImageView("/Image/Button/send.png", 30).getImage()));
    }

    public void leaveLobby() {
        System.out.println("leave");
    }

    public void send() {
        relationHandler.sendMessage(chatBox.getText(), Chat.ChatType.ROOM);
        chatBox.setText("");
    }
}
