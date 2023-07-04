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
import model.chat.Lobby;
import model.user.User;
import view.enums.PopUp;

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
    private Label wantedLobby;
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
    private boolean hasSearched;
    private Lobby searchedLobby;

    public JoinMenu() {
        this.appController = AppController.getInstance();
        lobbies = new ArrayList<>();
        relationHandler = RelationHandler.getInstance();
        hasSearched = false;
        searchedLobby = null;
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

    public void search() {
        if (searchBox.getText().isEmpty()) {
            PopUp.EMPTY_FIELD.show();
            return;
        }
        Lobby searchedLobby = relationHandler.searchLobby(searchBox.getText());
        if (searchedLobby == null) {
            PopUp.LOBBY_NOT_FOUND.show();
            return;
        }
        relationHandler.clearVBoxes();
        relationHandler.addLobbyToPane(searchedLobby);
        hasSearched = true;
    }

    public void join() {
        if (!hasSearched || searchedLobby == null) {
            PopUp.SEARCH_LOBBY.show();
            return;
        }
        searchedLobby.addUser(User.getCurrentUser());
        //nothing to do after joining a lobby ??
    }

    public void refresh() {
        relationHandler.showLobbies();
        hasSearched = false;
        searchedLobby = null;
    }

}
