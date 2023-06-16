package view.menus;

import controller.MultiMenuFunctions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class StartGameMenu extends Application {
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
}
