package view.menus;

import controller.MultiMenuFunctions;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.user.User;
import view.enums.Message;

import java.net.URL;
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


    }
}
