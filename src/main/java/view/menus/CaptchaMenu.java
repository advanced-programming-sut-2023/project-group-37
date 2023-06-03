package view.menus;

import controller.AppController;
import controller.RegisterMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.enums.Message;

import java.net.URL;
import java.util.Objects;

public class CaptchaMenu extends Application {
    private final AppController appController;
    private final RegisterMenuController registerMenuController;
    @FXML
    private Label label;
    @FXML
    private ImageView captchaImage;
    @FXML
    private TextField captchaField;

    public CaptchaMenu() {
        this.appController = AppController.getInstance();
        this.registerMenuController = RegisterMenuController.getInstance();
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/captchaMenu.fxml");
        Pane pane = FXMLLoader.load(Objects.requireNonNull(url));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        this.label.setText(Message.GO_FOR_CAPTCHA.toString());
    }

    @FXML
    private void refresh() {

    }

    @FXML
    private void confirm() {

    }
}
