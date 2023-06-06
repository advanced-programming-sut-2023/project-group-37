package view.menus;

import controller.AppController;
import controller.MultiMenuFunctions;
import controller.viewControllers.ChangeMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.enums.Message;

import java.net.URL;
import java.util.Objects;

public class ChangeMenu extends Application {
    private final AppController appController;
    private final ChangeMenuController changeMenuController;

    public ChangeMenu() {
        this.appController = AppController.getInstance();
        this.changeMenuController = ChangeMenuController.getInstance();
    }

    @FXML
    private static TextField textField;


    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/ChangeMenu.fxml");
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(url));
        anchorPane.setPrefHeight(anchorPane.getPrefHeight() + 30);
        MultiMenuFunctions.setBackground(anchorPane, "registration-bg.jpg");

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        textField.setPromptText(changeMenuController.getPromptText());

    }

    private boolean hasError() {
        return false;
    }


    public void change(MouseEvent mouseEvent) {
        if (hasError()) {
            new Alert(Alert.AlertType.ERROR, Message.EMPTY_FIELD.toString()).show();
            return;
        }
        switch (textField.getPromptText()) {
            case "new username" -> changeMenuController.changeUsername(textField.getText());
            case "new password" -> changeMenuController.changePassword(textField.getText());
            case "new slogan" -> changeMenuController.changeSlogan(textField.getText());
            case "new email" -> changeMenuController.changeEmial(textField.getText());
            case "new nickname" -> changeMenuController.changeNickname(textField.getText());
        }
    }
}
