package view.menus;

import connection.RelationHandler;
import controller.AppController;
import controller.MultiMenuFunctions;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.enums.Result;

import java.net.URL;
import java.util.Objects;

public class ScoreboardMenu extends Application {
    private final AppController appController;
    private final RelationHandler relationHandler;
    public VBox lastSeen;
    @FXML
    private VBox status;
    @FXML
    private VBox avatars;
    @FXML
    private VBox ranks;
    @FXML
    private VBox usernames;
    @FXML
    private VBox highscores;

    public ScoreboardMenu() {
        this.appController = AppController.getInstance();
        this.relationHandler = RelationHandler.getInstance();
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/scoreboardMenu.fxml");
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(url));
        anchorPane.setPrefHeight(anchorPane.getPrefHeight() + 30);
        MultiMenuFunctions.setBackground(anchorPane, "registration-bg.jpg");

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        relationHandler.setScoreboardRanks(ranks);
        relationHandler.setScoreboardUsers(usernames);
        relationHandler.setScoreboardHighscores(highscores);
        relationHandler.setScoreboardAvatars(avatars);
        relationHandler.setLastSeen(this.lastSeen);
        relationHandler.getUsersFromServer();
        relationHandler.setScoreboardStatus(status);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        relationHandler.showScoreboard();
    }

    public void enterMainMenu(MouseEvent mouseEvent) throws Exception {
        appController.runMenu(Result.ENTER_MAIN_MENU);
    }
}
