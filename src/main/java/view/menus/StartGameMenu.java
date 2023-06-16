package view.menus;

import controller.MultiMenuFunctions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class StartGameMenu extends Application {
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
}
