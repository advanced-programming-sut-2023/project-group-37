package view.menus;

import controller.AppController;
import controller.MultiMenuFunctions;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class LobbyMenu extends Application {
    private final AppController appController;
    @FXML
    private Rectangle sendButton;
    @FXML
    private TextField chatBox;
    @FXML
    private VBox yourChat;
    @FXML
    private VBox contactChat;
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

    public void leaveLobby(MouseEvent mouseEvent) {
    }

    public void send(MouseEvent mouseEvent) {
        System.out.println(chatBox.getText());
        chatBox.setText("");
    }
}
