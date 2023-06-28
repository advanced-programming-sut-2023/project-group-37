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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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
        BorderPane borderPane = FXMLLoader.load(Objects.requireNonNull(url));
        borderPane.setPrefHeight(borderPane.getPrefHeight() + 30);
        MultiMenuFunctions.setBackground(borderPane, "main-menu-background.jpg");

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
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

        userTerritory.put(Integer.parseInt(territory), username);
        usernames.getChildren().add(new Label(username));
        territories.getChildren().add(new Label(territory));

    }
}
