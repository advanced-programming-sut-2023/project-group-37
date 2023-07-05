package view.menus;

import connection.Connection;
import connection.RelationHandler;
import connection.packet.game.JoinRequestPacket;
import controller.AppController;
import controller.MultiMenuFunctions;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.chat.Lobby;
import model.user.User;
import view.enums.PopUp;
import view.enums.Result;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class JoinMenu extends Application {
    private final AppController appController;
    private final RelationHandler relationHandler;
    @FXML
    private TextField searchBox;
    @FXML
    private Rectangle searchButton;
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
        Border border = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

        names.setBackground(Background.fill(Color.LIGHTSKYBLUE));
        capacities.setBackground(Background.fill(Color.WHITE));
        admins.setBackground(Background.fill(Color.LIGHTSKYBLUE));
        others.setBackground(Background.fill(Color.WHITE));

        names.setBorder(border);
        capacities.setBorder(border);
        admins.setBorder(border);
        others.setBorder(border);

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
    }

    public void join() {
        TextInputDialog idInput = new TextInputDialog("Enter Id to join:");
        idInput.showAndWait();

        Lobby searchedLobby = relationHandler.searchLobby(idInput.getEditor().getText());
        if (searchedLobby == null) {
            PopUp.LOBBY_NOT_FOUND.show();
            return;
        }

        try {
            Connection.getInstance().getDataOutputStream().writeUTF(new JoinRequestPacket(
                    User.getCurrentUser(), Integer.parseInt(idInput.getEditor().getText())).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void refresh() {
        relationHandler.clearVBoxes();
        relationHandler.getLobbiesFromMaster();
        relationHandler.showLobbies();
    }

    @FXML
    private void backMainMenu() {
        try {
            this.appController.runMenu(Result.ENTER_MAIN_MENU);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
