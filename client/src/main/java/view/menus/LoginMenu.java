package view.menus;

import controller.AppController;
import controller.CaptchaController;
import controller.viewControllers.LoginMenuController;
import controller.MultiMenuFunctions;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.user.User;
import view.enums.Error;
import view.enums.Result;
import view.enums.Message;

import java.net.URL;
import java.util.Objects;

public class LoginMenu extends Application {
    private final AppController appController;
    private final LoginMenuController loginMenuController;
    private final CaptchaController captchaController;

    // Fields :
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordShow;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField captchaField;
    @FXML
    private CheckBox showPassword;
    @FXML
    private CheckBox stayLoggedIn;

    // Errors :
    @FXML
    private Label usernameError;
    @FXML
    private Label passwordError;
    @FXML
    private Label captchaError;
    @FXML
    private Label refreshLabel;

    // Images :
    @FXML
    private ImageView captchaImage;

    public LoginMenu() {
        this.appController = AppController.getInstance();
        this.loginMenuController = LoginMenuController.getInstance();
        this.captchaController = new CaptchaController();
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/loginMenu.fxml");
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(url));

        MultiMenuFunctions.setBackground(anchorPane, "registration-bg.jpg");

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        initializeFields();
        this.initializeErrors();
        this.initializeCaptcha();
    }

    private void initializeFields() {
        this.usernameField.textProperty().addListener((observable, oldText, newText) -> {
            if (MultiMenuFunctions.checkUsernameNotOK(newText))
                this.usernameError.setText(Error.INCORRECT_USERNAME_FORM.toString());

            else if (newText.isEmpty()) this.usernameError.setText(Error.NECESSARY_FIELD.toString());

            else this.usernameError.setText("");
        });

        MultiMenuFunctions.initializePasswordFields(this.passwordShow, this.passwordField, this.passwordError, this.showPassword);

        this.refreshLabel.setGraphic(new ImageView(new Image(Objects.requireNonNull(RegisterMenu.class.getResource(
                "/Image/Graphic/refresh.png")).toExternalForm(), this.refreshLabel.getPrefHeight() + 20,
                this.refreshLabel.getPrefHeight(), false, false)));
    }

    private void initializeErrors() {
        this.usernameError.setText(Error.NECESSARY_FIELD.toString());
        this.passwordError.setText(Error.NECESSARY_FIELD.toString());
    }

    private void initializeCaptcha() {
        this.generateCaptcha();

        this.captchaError.setText(Error.INCORRECT_CAPTCHA.toString());

        this.captchaField.textProperty().addListener((
                ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            if (this.captchaController.isCaptchaInCorrect(new_val))
                this.captchaError.setText(Error.INCORRECT_CAPTCHA.toString());

            else if (new_val.isEmpty()) this.captchaError.setText(Error.NECESSARY_FIELD.toString());

            else this.captchaError.setText("");
        });
    }

    private void generateCaptcha() {
        this.captchaController.generateCaptcha();
        this.captchaImage.setImage(this.captchaController.getCaptchaImage());
    }

    private boolean checkForErrors() {
        return this.usernameError.getText().isEmpty() && this.passwordError.getText().isEmpty() &&
                this.captchaError.getText().isEmpty();
    }

    @FXML
    private void forgotPassword() throws Exception {
        this.appController.runMenu(Result.ENTER_FORGOT_MENU);
    }

    @FXML
    private void login() throws Exception {
        if (!this.checkForErrors()) {
            new Alert(Alert.AlertType.ERROR, Message.EMPTY_FIELD.toString()).show();
            return;
        }

        Message message = this.loginMenuController.login(this.usernameField.getText(), this.passwordField.getText(),
                this.stayLoggedIn.isSelected());
        if (message == Message.LOGIN_SUCCESSFUL) {
            MultiMenuFunctions.setAllCurrentUsers(User.getUserByUsername(this.usernameField.getText()));
            this.appController.runMenu(Result.ENTER_MAIN_MENU);
        } else new Alert(Alert.AlertType.ERROR, Message.CANT_LOGIN.toString()).show();
    }

    @FXML
    private void enterRegisterMenu() throws Exception {
        this.appController.runMenu(Result.ENTER_REGISTER_MENU);
    }

    @FXML
    private void refreshCaptcha() {
        this.generateCaptcha();

        if (this.captchaController.isCaptchaInCorrect(this.captchaField.getText()))
            this.captchaError.setText(Error.INCORRECT_CAPTCHA.toString());

        else this.captchaError.setText("");
    }
}
