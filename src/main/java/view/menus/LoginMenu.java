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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.user.User;
import view.enums.Error;
import view.enums.Result;
import view.enums.Message;

import java.net.URL;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu extends Application {
    private final AppController appController;
    private final LoginMenuController loginMenuController;
    private final CaptchaController captchaController;
    private final Scanner scanner;
    private String command;
    private String message;

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

    // Errors :
    @FXML
    private Label usernameError;
    @FXML
    private Label passwordError;
    @FXML
    private Label captchaError;

    // Images :
    @FXML
    private ImageView captchaImage;

    public LoginMenu() {
        this.appController = AppController.getInstance();
        this.loginMenuController = LoginMenuController.getInstance();
        this.captchaController = new CaptchaController();
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/loginMenu.fxml");
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(url));
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

        this.passwordField.textProperty().addListener((observable, oldText, newText) ->
                this.passwordShow.setText(newText));

        this.passwordShow.textProperty().addListener((observable, oldText, newText) ->
                this.passwordField.setText(newText));

        this.passwordShow.managedProperty().bind(this.showPassword.selectedProperty());
        this.passwordShow.visibleProperty().bind(this.showPassword.selectedProperty());

        this.passwordField.managedProperty().bind(this.showPassword.selectedProperty().not());
        this.passwordField.visibleProperty().bind(this.showPassword.selectedProperty().not());
    }

    private void initializeErrors() {
        this.usernameError.setText(Error.NECESSARY_FIELD.toString());
        this.passwordError.setText(Error.NECESSARY_FIELD.toString());
    }

    private void initializeCaptcha() {
        this.generateCaptcha();

        this.captchaError.setText(Error.NECESSARY_FIELD.toString());

        this.captchaField.textProperty().addListener((
                ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            if (this.captchaController.isCaptchaInCorrect(new_val))
                this.captchaError.setText(Error.INCORRECT_CAPTCHA.toString());

            else if (new_val.isEmpty()) this.captchaError.setText(Error.NECESSARY_FIELD.toString());

            else this.captchaError.setText("");
        });
    }

    private boolean login(Matcher matcher) {
        this.message = this.loginMenuController.login(matcher);
        System.out.println(this.message);

        if (Message.INCORRECT_PASSWORD.equals(message)) {
            System.out.println("You can try again in " + loginMenuController.getDelayTime() + " seconds!");
            MultiMenuFunctions.wait(loginMenuController.getDelayTime() * 1000);
        }

        return Message.LOGIN_SUCCESSFUL.equals(message);
    }

    private void forgotPassword(Matcher matcher) {
        this.message = this.loginMenuController.forgotPassword(matcher);
        System.out.println(this.message);

        if (Message.ASK_QUESTION.equals(message)) {
            System.out.println(this.loginMenuController.getUser().getRecoveryQuestion());
            do {
                this.command = this.scanner.nextLine();
                this.message = this.loginMenuController.answerSecurityQuestion(this.command);

                System.out.println(this.message);

            } while (!Message.ENTER_NEW_PASSWORD.equals(message));

            firstLoop:
            while (true) {
                this.command = this.scanner.nextLine();
                this.message = this.loginMenuController.getNewPassword(this.command);

                System.out.println(this.message);
                if (Message.ENTER_NEW_PASSWORD_AGAIN.equals(message)) {
                    while (true) {
                        this.command = this.scanner.nextLine();
                        this.message = this.loginMenuController.getNewPasswordAgain(this.command);
                        System.out.println(this.message);
                        if (Message.CHANGE_PASSWORD_SUCCESSFUL.equals(this.message) || Message.CANCEL.equals(message))
                            break firstLoop;
                    }
                } else if (Message.CANCEL.equals(message))
                    break;
            }
        }
    }

    private void generateCaptcha() {
        this.captchaController.generateCaptcha();
        this.captchaImage.setImage(this.captchaController.getCaptchaImage());
    }

    @FXML
    private void forgotPassword() {
        Stage forgotPasswordStage = new Stage();
        forgotPasswordStage.initModality(Modality.APPLICATION_MODAL);
        forgotPasswordStage.initOwner(this.appController.getStage());

//        try {
//            new ForgotMenu().start(forgotPasswordStage);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

    @FXML
    private void login() {

    }

    @FXML
    private void backRegisterMenu() throws Exception {
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
