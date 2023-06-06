package view.menus;

import controller.AppController;
import controller.MultiMenuFunctions;
import controller.viewControllers.ChangeMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    private TextField textField;

    public void setPromptText(String text){
        textField.setPromptText(text);
    }


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


    public void change(MouseEvent mouseEvent) {

    }
}
