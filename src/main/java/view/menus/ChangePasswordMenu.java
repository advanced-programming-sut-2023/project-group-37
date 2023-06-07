package view.menus;

import controller.AppController;
import controller.MultiMenuFunctions;
import controller.viewControllers.ChangePasswordMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class ChangePasswordMenu extends Application {
    private final AppController appController;
    private final ChangePasswordMenuController changePasswordMenuController;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField oldPassword;
    @FXML
    private TextField newPassword;

    public ChangePasswordMenu() {
        this.appController = AppController.getInstance();
        this.changePasswordMenuController = ChangePasswordMenuController.getInstance();
    }


    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/ChangePasswordMenu.fxml");
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(url));

        MultiMenuFunctions.setBackground(anchorPane, "registration-bg.jpg");

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    public void changePassword(MouseEvent mouseEvent) {
    }
}
