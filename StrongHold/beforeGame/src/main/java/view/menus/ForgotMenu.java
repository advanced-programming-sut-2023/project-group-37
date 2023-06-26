package view.menus;

import controller.AppController;
import controller.MultiMenuFunctions;
import controller.viewControllers.LoginMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.user.User;
import view.enums.Error;
import view.enums.Message;
import view.enums.Result;

import java.net.URL;
import java.util.Objects;

public class ForgotMenu extends Application {
    private final AppController appController;
    private final LoginMenuController loginMenuController;

    // Fields :
    @FXML
    private TextField usernameField;
    @FXML
    private TextField answerField;
    @FXML
    private TextField passwordShow;
    @FXML
    private TextField passwordConfirmShow;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordConfirmField;
    @FXML
    private CheckBox showPassword;

    // Labels :
    @FXML
    private Label usernameError;
    @FXML
    private Label questionLabel;
    @FXML
    private Label answerError;
    @FXML
    private Label passwordError;
    @FXML
    private Label passwordConfirmError;

    @FXML
    private Button changePassword;

    {
        this.appController = AppController.getInstance();
        this.loginMenuController = LoginMenuController.getInstance();
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/forgotMenu.fxml");
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(url));
        MultiMenuFunctions.setBackground(anchorPane, "registration-bg.jpg");

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        this.usernameError.setText(Error.NECESSARY_FIELD.toString());
        initializeFields();
        setVisibilities(false);
    }

    private void initializeFields() {
        this.usernameField.textProperty().addListener((observable, oldText, newText) -> {
            if (MultiMenuFunctions.checkUsernameNotOK(newText))
                this.usernameError.setText(Error.INCORRECT_USERNAME_FORM.toString());

            else if (newText.isEmpty())
                this.usernameError.setText(Error.NECESSARY_FIELD.toString());

            else this.usernameError.setText("");
        });

        this.answerField.textProperty().addListener((observable, oldText, newText) -> {
            if (newText.isEmpty())
                this.answerError.setText(Error.NECESSARY_FIELD.toString());

            else this.answerError.setText("");
        });

        MultiMenuFunctions.initializePasswordFieldsWithConfirm(this.passwordShow, this.passwordConfirmShow,
                this.passwordField, this.passwordConfirmField, this.passwordError, this.passwordConfirmError,
                this.showPassword);
    }

    private void initializeErrors() {
        this.passwordError.setText(Error.NECESSARY_FIELD.toString());
        this.passwordConfirmError.setText(Error.NECESSARY_FIELD.toString());
        this.answerError.setText(Error.NECESSARY_FIELD.toString());
    }

    private void setVisibilities(boolean visibility) {
        this.questionLabel.setVisible(visibility);
        this.answerField.setVisible(visibility);
        this.showPassword.setVisible(visibility);
        this.changePassword.setVisible(visibility);

        if (visibility) {
            this.passwordField.managedProperty().bind(showPassword.selectedProperty().not());
            this.passwordField.visibleProperty().bind(showPassword.selectedProperty().not());

            this.passwordConfirmField.managedProperty().bind(showPassword.selectedProperty().not());
            this.passwordConfirmField.visibleProperty().bind(showPassword.selectedProperty().not());
        }
        else {
            this.passwordField.managedProperty().bind(showPassword.selectedProperty());
            this.passwordField.visibleProperty().bind(showPassword.selectedProperty());

            this.passwordConfirmField.managedProperty().bind(showPassword.selectedProperty());
            this.passwordConfirmField.visibleProperty().bind(showPassword.selectedProperty());
        }
    }

    private boolean checkForErrors() {
        if (!this.answerField.isVisible())
            return false;

        return this.passwordError.getText().isEmpty() && this.passwordConfirmError.getText().isEmpty() &&
                this.answerError.getText().isEmpty();
    }

    @FXML
    private void findUser() {
        if (!this.usernameError.getText().isEmpty())
            return;

        Message message = this.loginMenuController.findUser(usernameField.getText(), questionLabel);

        if (message == null) {
            this.setVisibilities(true);
            this.initializeErrors();
        }

        else new Alert(Alert.AlertType.ERROR, message.toString());
    }

    @FXML
    private void changePassword() {
        if (!this.checkForErrors()) {
            new Alert(Alert.AlertType.ERROR, Message.EMPTY_FIELD.toString()).show();
            return;
        }

        Message message = this.loginMenuController.changePassword(this.answerField, this.passwordField);

        if (message == Message.CHANGE_PASSWORD) {
            try {
                this.appController.runMenu(Result.ENTER_LOGIN_MENU);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            new Alert(Alert.AlertType.INFORMATION, message.toString()).show();
        }

        else new Alert(Alert.AlertType.ERROR, message.toString()).show();
    }

    @FXML
    private void enterLoginMenu() throws Exception {
        this.appController.runMenu(Result.ENTER_LOGIN_MENU);
    }
}
