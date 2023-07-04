package view.menus;

import connection.Connection;
import connection.RelationHandler;
import connection.packet.game.LobbiesPacket;
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
import model.chat.Lobby;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class JoinMenu extends Application {
    private final AppController appController;
    private final RelationHandler relationHandler;
    private ArrayList<Lobby> lobbies;
    @FXML
    private TextField searchBox;
    @FXML
    private Rectangle searchButton;
    @FXML
    private Label searchedLobby;
    @FXML
    private AnchorPane showLobbyPane;
    @FXML
    private VBox names;
    @FXML
    private VBox capacities;
    @FXML
    private VBox admins;
    @FXML
    private VBox others;

    public JoinMenu() {
        this.appController = AppController.getInstance();
        lobbies = new ArrayList<>();
        relationHandler = RelationHandler.getInstance();
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/joinMenu.fxml");
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(url));

        MultiMenuFunctions.setBackground(anchorPane, "registration-bg.jpg");

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        relationHandler.setLobbyNames(names);
        relationHandler.setLobbyCapacities(capacities);
        relationHandler.setLobbyAdmin(admins);
        relationHandler.setLobbyOthers(others);
        searchButton.setFill(new ImagePattern(MultiMenuFunctions.getImageView("/Image/Button/search.jpg", 30)
                .getImage()));
        relationHandler.getLobbiesFromMaster();
        relationHandler.showLobbies();
    }

    public void search(MouseEvent mouseEvent) {

    }

    public void join(MouseEvent mouseEvent) {

    }

    public void startGame(MouseEvent mouseEvent) {
        //TODO : start online game
    }
}
