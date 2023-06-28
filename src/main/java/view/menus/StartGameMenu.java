package view.menus;

import controller.MultiMenuFunctions;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.user.User;
import view.enums.Message;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class StartGameMenu extends Application {
    @FXML
    private VBox usernames;
    @FXML
    private VBox territories;
    @FXML
    private TextField territory;
    @FXML
    private TextField username;

    private HashMap<Integer, String> userTerritory = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/startGameMenu.fxml");
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(url));
        anchorPane.setPrefHeight(anchorPane.getPrefHeight() + 30);
        MultiMenuFunctions.setBackground(anchorPane, "main-menu-background.jpg");

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        createLabel(User.getCurrentUser().getUsername(), "1");
    }

    public void addPlayer(MouseEvent mouseEvent) {
        String username = this.username.getText();
        String territory = this.territory.getText();
        if (username.isEmpty() || territory.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, Message.EMPTY_FIELD.toString()).show();
            return;
        }

        if (User.getUserByUsername(username) == null) {
            new Alert(Alert.AlertType.ERROR, Message.USERNAME_NOT_FOUND.toString()).show();
            return;
        }

        if (!territory.matches("\\d+") || !(1 <= Integer.parseInt(territory) && Integer.parseInt(territory) <= 8)) {
            new Alert(Alert.AlertType.ERROR, Message.INVALID_TERRITORY_NUMBER.toString()).show();
            return;
        }

        if (userTerritory.containsKey(Integer.parseInt(territory))) {
            new Alert(Alert.AlertType.ERROR, Message.TERRITORY_EXISTS.toString()).show();
            return;
        }

        if (userTerritory.containsValue(username)) {
            new Alert(Alert.AlertType.ERROR, Message.USERNAME_ALREADY_EXISTS.toString()).show();
            return;
        }

        createLabel(username, territory);
    }

    private void createLabel(String username, String territory) {
        Label l1 = new Label(username);
        l1.setFont(new Font("Segoe Print", 14));
        usernames.getChildren().add(l1);
        Label l2 = new Label(territory);
        l2.setFont(new Font("Segoe Print", 14));
        territories.getChildren().add(l2);
        userTerritory.put(Integer.parseInt(territory), username);
    }
}
