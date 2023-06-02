package view.menus;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.enums.Message;
import view.enums.Result;

import java.net.URL;
import java.util.Objects;

public class CaptchaMenu extends Application {
    @FXML
    private Label label;
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
}
