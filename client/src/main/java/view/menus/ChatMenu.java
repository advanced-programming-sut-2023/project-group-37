package view.menus;

import controller.AppController;
import controller.MultiMenuFunctions;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.user.User;

import java.net.URL;
import java.util.Objects;

public class ChatMenu extends Application {
    private final AppController appController = AppController.getInstance();
    @FXML
    private VBox privateChatVBox;
    @FXML
    private VBox friendsVBox;
    @FXML
    private VBox publicChatVBox;
    @FXML
    private TextField friendName;
    @FXML
    private Rectangle searchButton;
    @FXML
    private Circle avatar;
    @FXML
    private TextField privateChatBox;
    @FXML
    private Rectangle sendButtonPrivate;
    @FXML
    private TextField publicChatBox;
    @FXML
    private Rectangle sendButtonPublic;


    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/chatMenu.fxml");
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(url));

        MultiMenuFunctions.setBackground(anchorPane, "main-menu-background.jpg");

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        searchButton.setFill(new ImagePattern(MultiMenuFunctions.getImageView("/Image/Button/search.jpg", 30).getImage()));
        sendButtonPrivate.setFill(new ImagePattern(MultiMenuFunctions.getImageView("/Image/Button/send.png", 30).getImage()));
        sendButtonPublic.setFill(new ImagePattern(MultiMenuFunctions.getImageView("/Image/Button/send.png", 30).getImage()));
    }

    public void addFriend(MouseEvent mouseEvent) {
    }

    public void search(MouseEvent mouseEvent) {
        User foundUser = null;

        setAvatar(foundUser);
    }

    public void sendPrivate(MouseEvent mouseEvent) {
    }

    public void sendPublic(MouseEvent mouseEvent) {
    }

    public void setAvatar(User user) {
        avatar.setFill(new ImagePattern(user.getAvatar()));
    }
}
