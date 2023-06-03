package view.menus;

import controller.AppController;
import controller.CaptchaController;
import controller.MultiMenuFunctions;
import controller.viewControllers.RegisterMenuController;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.user.RecoveryQuestion;
import model.user.User;
import view.enums.Error;
import view.enums.Result;

import java.net.URL;
import java.util.Objects;

public class RegisterMenu extends Application {
    private final AppController appController;
    private final RegisterMenuController registerMenuController;
    private final CaptchaController captchaController;

    // choiceBoxes :
    @FXML
    private ChoiceBox<String> recoveryQuestions;
    @FXML
    private ChoiceBox<String> sloganChoiceBox;
    @FXML
    private CheckBox showPassword;

    // fields :
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordShow;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField passwordConfirmShow;
    @FXML
    private PasswordField passwordConfirmField;
    @FXML
    private TextField nicknameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField recoveryAnswerField;
    @FXML
    private TextField sloganField;
    @FXML
    private TextField captchaField;

    // Error labels :
    @FXML
    private Label usernameError;
    @FXML
    private Label passwordError;
    @FXML
    private Label passwordConfirmError;
    @FXML
    private Label nicknameError;
    @FXML
    private Label emailError;
    @FXML
    private Label recoveryQuestionError;
    @FXML
    private Label recoveryAnswerError;
    @FXML
    private Label sloganError;
    @FXML
    private Label captchaError;

    // Images :
    @FXML
    private ImageView captchaImage;

    public RegisterMenu() {
        this.appController = AppController.getInstance();
        this.registerMenuController = RegisterMenuController.getInstance();
        this.captchaController = new CaptchaController();
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/registerMenu.fxml");
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(url));
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        this.initializeFields();
        this.initializeErrors();
        this.initializeSlogan();
        this.initializeCaptcha();

        for (RecoveryQuestion recoveryQuestion : RecoveryQuestion.values()) {
            this.recoveryQuestions.getItems().add(recoveryQuestion.getQuestion());
        }
    }

    private void initializeSlogan() {
        this.sloganChoiceBox.getItems().add("No slogan");
        this.sloganChoiceBox.getItems().add("Type a slogan");
        this.sloganChoiceBox.getItems().add("Random slogan");
        this.sloganChoiceBox.setValue("No slogan");

        this.sloganField.setDisable(true);

        this.sloganChoiceBox.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    if (new_val.intValue() == 1) {
                        this.sloganField.setDisable(false);
                        this.sloganError.setText(Error.NECESSARY_FIELD.toString());
                        return;
                    }

                    if (new_val.intValue() == 2)
                        this.sloganField.setText(this.registerMenuController.generateRandomSlogan());
                    else this.sloganField.setText("");

                    this.sloganField.setDisable(true);
                    this.sloganError.setText("");
                });

        this.sloganField.textProperty().addListener((
            ObservableValue<? extends String> ov, String old_val, String new_val) -> {
                if (this.sloganChoiceBox.getValue().equals("Type a slogan")) {
                    if (new_val.equals(""))
                        this.sloganError.setText(Error.NECESSARY_FIELD.toString());
                    else this.sloganError.setText("");
                }
        });
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

    private void initializeFields() {
        this.usernameField.textProperty().addListener((observable, oldText, newText) -> {
            if (MultiMenuFunctions.checkUsernameNotOK(newText))
                this.usernameError.setText(Error.INCORRECT_USERNAME_FORM.toString());

            else if (newText.isEmpty())
                this.usernameError.setText(Error.NECESSARY_FIELD.toString());

            else this.usernameError.setText("");

            if (User.getUserByUsername(usernameField.getText()) != null) {
                this.usernameError.setText(Error.USERNAME_ALREADY_EXISTS.toString());
            }
        });

        this.passwordShow.textProperty().addListener((observable, oldText, newText) ->
                this.passwordField.setText(newText));

        this.passwordField.textProperty().addListener((observable, oldText, newText) -> {
            this.passwordShow.setText(newText);

            if (MultiMenuFunctions.checkPasswordNotOK(newText))
                this.passwordError.setText(Error.WEAK_PASSWORD.toString());

            else if (newText.isEmpty())
                this.passwordError.setText(Error.NECESSARY_FIELD.toString());

            else this.passwordError.setText("");
        });

        this.passwordConfirmShow.textProperty().addListener((observable, oldText, newText) ->
                this.passwordConfirmField.setText(newText));

        this.passwordConfirmField.textProperty().addListener((observable, oldText, newText) -> {
            passwordConfirmShow.setText(newText);

            if (!passwordField.getText().equals(newText))
                this.passwordConfirmError.setText(Error.INCOMPATIBLE_PASSWORDS.toString());

            else if (newText.isEmpty())
                this.passwordConfirmError.setText(Error.NECESSARY_FIELD.toString());

            else this.passwordConfirmError.setText("");
        });

        this.passwordShow.managedProperty().bind(this.showPassword.selectedProperty());
        this.passwordShow.visibleProperty().bind(this.showPassword.selectedProperty());

        this.passwordField.managedProperty().bind(this.showPassword.selectedProperty().not());
        this.passwordField.visibleProperty().bind(this.showPassword.selectedProperty().not());

        this.passwordConfirmShow.managedProperty().bind(this.showPassword.selectedProperty());
        this.passwordConfirmShow.visibleProperty().bind(this.showPassword.selectedProperty());

        this.passwordConfirmField.managedProperty().bind(this.showPassword.selectedProperty().not());
        this.passwordConfirmField.visibleProperty().bind(this.showPassword.selectedProperty().not());


        this.nicknameField.textProperty().addListener((observable, oldText, newText) -> {
            if (newText.isEmpty())
                this.nicknameError.setText(Error.NECESSARY_FIELD.toString());
            else this.nicknameError.setText("");
        });

        this.emailField.textProperty().addListener((observable, oldText, newText) -> {
            if (MultiMenuFunctions.checkEmailNotOK(newText))
                this.emailError.setText(Error.INCORRECT_EMAIL_FORM.toString());

            else if (newText.isEmpty())
                this.emailError.setText(Error.NECESSARY_FIELD.toString());

            else this.emailError.setText("");
        });

        this.recoveryQuestions.setOnAction(actionEvent -> {
            if (!this.recoveryQuestions.getValue().isEmpty())
                this.recoveryQuestionError.setText("");
        });

        this.recoveryAnswerField.textProperty().addListener((observable, oldText, newText) -> {
            if (newText.isEmpty())
                this.recoveryAnswerError.setText(Error.NECESSARY_FIELD.toString());
            else this.recoveryAnswerError.setText("");
        });
    }

    private void initializeErrors() { // TODO : is this function necessary ?
        this.usernameError.setText(Error.NECESSARY_FIELD.toString());
        this.passwordError.setText(Error.NECESSARY_FIELD.toString());
        this.passwordConfirmError.setText(Error.NECESSARY_FIELD.toString());
        this.nicknameError.setText(Error.NECESSARY_FIELD.toString());
        this.emailError.setText(Error.NECESSARY_FIELD.toString());
        this.recoveryQuestionError.setText(Error.NECESSARY_FIELD.toString());
        this.recoveryAnswerError.setText(Error.NECESSARY_FIELD.toString());
    }

    private boolean checkForErrors() {
        return this.usernameError.getText().isEmpty() && this.passwordError.getText().isEmpty() &&
                this.passwordConfirmError.getText().isEmpty() && this.nicknameError.getText().isEmpty() &&
                this.emailError.getText().isEmpty() && this.recoveryQuestionError.getText().isEmpty() &&
                this.recoveryAnswerError.getText().isEmpty() && this.sloganError.getText().isEmpty() &&
                this.captchaError.getText().isEmpty();
    }

    private void generateCaptcha() {
        this.captchaController.generateCaptcha();
        this.captchaImage.setImage(captchaController.getCaptchaImage());
    }

    @FXML
    private void register() throws Exception {
        if (!this.checkForErrors())
            return;

        this.registerMenuController.register(this.usernameField.getText(), this.passwordField.getText(),
                this.nicknameField.getText(), this.emailField.getText(), this.recoveryQuestions.getValue(),
                this.recoveryAnswerField.getText(), this.sloganField.getText());

        this.appController.runMenu(Result.GO_FOR_CAPTCHA);
    }

    @FXML
    private void generateRandomPassword() {
        this.passwordField.setText(registerMenuController.generateRandomPassword());
        this.showPassword.setSelected(true);
    }

    @FXML
    private void refreshCaptcha() {
        this.generateCaptcha();

        if (this.captchaController.isCaptchaInCorrect(this.captchaField.getText()))
            this.captchaError.setText(Error.INCORRECT_CAPTCHA.toString());

        else this.captchaError.setText("");
    }
}