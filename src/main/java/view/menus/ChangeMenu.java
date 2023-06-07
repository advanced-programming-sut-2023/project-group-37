package view.menus;

import controller.AppController;
import controller.MultiMenuFunctions;
import controller.viewControllers.ChangeMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.user.User;
import view.enums.Error;
import view.enums.Message;
import view.enums.Result;

import java.net.URL;
import java.util.Objects;

public class ChangeMenu extends Application {
    private final AppController appController;
    private final ChangeMenuController changeMenuController;
    @FXML
    private Label errorLabel;
    @FXML
    private Label label;
    @FXML
    private TextField textField;

    public ChangeMenu() {
        this.appController = AppController.getInstance();
        this.changeMenuController = ChangeMenuController.getInstance();
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

    @FXML
    private void initialize() {
        textField.setPromptText(changeMenuController.getPromptText());
        label.setText(changeMenuController.getPromptText());
        errorLabel.setText(Error.NECESSARY_FIELD.toString());
        this.updateErrorLabel();
    }


    private void updateErrorLabel() {
        String promptText = changeMenuController.getPromptText();
        switch (promptText) {
            case "new username" -> updateUsernameError();
            case "new email" -> updateEmailError();
        }
    }

    private void updateUsernameError() {
        this.textField.textProperty().addListener((observable, oldText, newText) -> {
            if (MultiMenuFunctions.checkUsernameNotOK(newText))
                this.errorLabel.setText(Error.INCORRECT_USERNAME_FORM.toString());

            else if (User.getUserByUsername(textField.getText()) != null)
                this.errorLabel.setText(Error.USERNAME_ALREADY_EXISTS.toString());

            else if (newText.isEmpty())
                this.textField.setText(Error.NECESSARY_FIELD.toString());

            else this.errorLabel.setText("");

        });
    }

    private void updateEmailError() {
        this.textField.textProperty().addListener((observable, oldText, newText) -> {
            if (MultiMenuFunctions.checkEmailNotOK(newText))
                this.errorLabel.setText(Error.INCORRECT_EMAIL_FORM.toString());

            else if (User.getUserByEmail(newText) != null)
                this.errorLabel.setText(Error.EMAIL_ALREADY_EXISTS.toString());
            else if (newText.isEmpty())
                this.textField.setText(Error.NECESSARY_FIELD.toString());

            else this.errorLabel.setText("");
        });
    }

    private boolean hasError() {
        return !errorLabel.getText().isEmpty();
    }


    public void change(MouseEvent mouseEvent) throws Exception {
        if (hasError()) {
            new Alert(Alert.AlertType.ERROR, Message.EMPTY_FIELD.toString()).show();
            return;
        }
        Message message = Message.valueOf("");
        switch (textField.getPromptText()) {
            case "new username" -> message = changeMenuController.changeUsername(textField.getText());
            case "new slogan" -> message = changeMenuController.changeSlogan(textField.getText());
            case "new email" -> message = changeMenuController.changeEmail(textField.getText());
            case "new nickname" -> message = changeMenuController.changeNickname(textField.getText());
        }
        new Alert(Alert.AlertType.INFORMATION, message.toString()).show();
        appController.runMenu(Result.ENTER_PROFILE_MENU);
    }

    public void enterProfileMenu(MouseEvent mouseEvent) throws Exception {
        appController.runMenu(Result.ENTER_PROFILE_MENU);
    }
}
