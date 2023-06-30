package view.menus;

import controller.AppController;
import controller.MultiMenuFunctions;
import controller.GameController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.game.GameColor;
import model.game.Game;
import model.game.Government;
import model.game.Map;
import model.user.User;
import view.enums.Message;
import view.enums.Result;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class StartGameMenu extends Application {
    private final AppController appController = AppController.getInstance();
    @FXML
    private ImageView back;
    @FXML
    private TextField mapName;
    @FXML
    private VBox usernames;
    @FXML
    private VBox territories;
    @FXML
    private TextField territory;
    @FXML
    private TextField username;

    private final HashMap<Integer, String> userTerritory = new HashMap<>();

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
        back.setImage(new Image(Objects.requireNonNull(RegisterMenu.class.getResource("/Image/Button/back1.png")).toExternalForm()));
        createLabel(User.getCurrentUser().getUsername(), "1");
    }

    public void addPlayer() {
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
        l1.setFont(new Font("Arial Black", 14));
        l1.setTextFill(GameColor.BLUE.getColor());
        usernames.getChildren().add(l1);
        Label l2 = new Label(territory);
        l2.setFont(new Font("Arial Black", 14));
        l2.setTextFill(GameColor.BLUE.getColor());
        territories.getChildren().add(l2);
        userTerritory.put(Integer.parseInt(territory), username);
    }

    public void back() throws Exception {
        appController.runMenu(Result.ENTER_MAIN_MENU);
    }

    public void startGame() throws Exception {
        createGame(userTerritory);
    }

    private void createGame(HashMap<Integer, String> userTerritory) throws Exception {
        if (mapName.getText().isEmpty() || Map.getMapCopyByName(mapName.getText()) == null) {
            new Alert(Alert.AlertType.ERROR, Message.EMPTY_FIELD.toString()).show();
            return;
        }
        ArrayList<Government> governments = new ArrayList<>();
        Map map = Map.getMapCopyByName(mapName.getText());

        assert map != null;

        int index = 1;
        for (java.util.Map.Entry<Integer, String> integerStringEntry : userTerritory.entrySet()) {
            String username = integerStringEntry.getValue();
            Integer territory = integerStringEntry.getKey();
            governments.add(new Government(User.getUserByUsername(username), GameColor.values()[index], map, territory));
        }

        Game game = new Game(map, governments);
        GameController.getInstance().setCurrentGame(game);
        appController.runMenu(Result.ENTER_GAME_MENU);
    }
}
