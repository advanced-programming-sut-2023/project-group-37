package view.menus;

import connection.Connection;
import connection.RelationHandler;
import connection.packet.game.LeaveRequestPacket;
import controller.AppController;
import controller.MultiMenuFunctions;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.chat.Chat;
import model.game.Map;
import model.user.User;
import view.enums.Result;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class LobbyMenu extends Application {
    private final AppController appController;
    private final RelationHandler relationHandler;
    @FXML
    private ChoiceBox mapChoiceBox;
    @FXML
    private Button leaveButton;
    @FXML
    private Button startButton;
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
        this.relationHandler.setRoomVBox(this.chatVBox);
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
        for (Map map : Map.getMaps())
            mapChoiceBox.getItems().add(map.getName());

        Border border = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

        territories.setBorder(border);
        usernames.setBorder(border);
        chatVBox.setBorder(border);

        this.startButton.setBackground(Background.fill(Color.GREEN));
        this.leaveButton.setBackground(Background.fill(Color.RED));

        this.relationHandler.setStartButton(this.startButton);

        this.relationHandler.setUsernames(this.usernames);
        this.relationHandler.setTerritories(this.territories);
        this.relationHandler.setRoomVBox(this.chatVBox);
        this.sendButton.setFill(new ImagePattern(MultiMenuFunctions.getImageView("/Image/Button/send.png",
                30).getImage()));
        this.chatVBox.setBackground(Background.fill(Color.WHITE));
        territories.setBackground(Background.fill(Color.LIGHTYELLOW));
        usernames.setBackground(Background.fill(Color.LIGHTYELLOW));
        this.lobbyName.setText(String.valueOf(this.relationHandler.getCurrentLobby().getId()));
        this.lobbyCapacity.setText(String.valueOf(this.relationHandler.getCurrentLobby().getCapacity()));
    }

    public void leaveLobby() {
        try {
            Connection.getInstance().getDataOutputStream().writeUTF(
                    new LeaveRequestPacket(relationHandler.getCurrentLobby()).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            this.appController.runMenu(Result.ENTER_MAIN_MENU);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void send() {
        this.relationHandler.sendMessage(chatBox.getText(), Chat.ChatType.ROOM);
        this.chatBox.setText("");
    }

    public void startTheGame() {
        this.relationHandler.startGame();
    }
}
