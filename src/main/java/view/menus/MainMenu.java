package view.menus;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class MainMenu extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        URL url = MainMenu.class.getResource("main-menu.fxml");
        assert url != null;
        BorderPane borderPane = FXMLLoader.load(url);
        borderPane.setBackground(new Background(new BackgroundImage(new Image(Objects.requireNonNull(
                MainMenu.class.getResource("images/background/main-menu-background.jpg")).toExternalForm(),
                borderPane.getPrefWidth(), borderPane.getPrefHeight(), false, false),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));
        borderPane.getStylesheets().add((Objects.requireNonNull(this.getClass().getResource("css/main-menu.css")).toExternalForm()));
        Scene scene = new Scene(borderPane);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void profileMenu(MouseEvent mouseEvent) {
    }

    public void resumeGame(MouseEvent mouseEvent) {
    }

    public void startGame(MouseEvent mouseEvent) {
    }

    public void exit() {
        Platform.exit();
    }
}