package view.menus;

import controller.AppController;
import controller.MultiMenuFunctions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class LobbyMenu extends Application {
    private final AppController appController;
    public Rectangle sendButton;
    public TextField chatBox;
    public VBox yourChat;
    public VBox contactChat;
    public AnchorPane chatPane;
    public Label lobbyCapacity;
    public Label lobbyName;
    public VBox territories;
    public VBox usernames;

    public LobbyMenu() {
        this.appController = AppController.getInstance();
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

    public void leaveLobby(MouseEvent mouseEvent) {
    }

    public void send(MouseEvent mouseEvent) {

    }
}
