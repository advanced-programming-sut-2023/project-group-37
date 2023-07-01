package view.menus;

import connection.Connection;
import controller.AppController;
import controller.CaptchaController;
import controller.MultiMenuFunctions;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.user.RecoveryQuestion;
import model.user.Slogan;
import model.user.User;
import view.enums.Error;
import view.enums.Message;
import view.enums.Result;

import java.net.URL;
import java.security.SecureRandom;
import java.util.Objects;

public class RegisterMenu extends Application {
    private final AppController appController;
    private final CaptchaController captchaController;

    // choiceBoxes && Buttons :
    @FXML
    private ChoiceBox<String> recoveryQuestions;
    @FXML
    private ChoiceBox<String> sloganChoiceBox;
    @FXML
    private CheckBox showPassword;
    @FXML
    private Label refreshLabel;

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
        this.captchaController = new CaptchaController();
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/registerMenu.fxml");
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(url));
        anchorPane.setPrefHeight(anchorPane.getPrefHeight() + 30);
        MultiMenuFunctions.setBackground(anchorPane, "registration-bg.jpg");

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

    public String randomPassword() {
        SecureRandom random = new SecureRandom();

        String LOWER_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
        String UPPER_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String NUMBERS = "0123456789";
        String OTHER_CHARACTERS = "?!@#$%^&*_+=-/:;.><";

        StringBuilder password = new StringBuilder();
        int randomIndex;
        char randomChar;

        for (int i = 0; i < 3; i++) {
            randomIndex = random.nextInt(UPPER_CHARACTERS.length());
            randomChar = UPPER_CHARACTERS.charAt(randomIndex);
            password.append(randomChar);
        }

        randomIndex = random.nextInt(OTHER_CHARACTERS.length());
        randomChar = OTHER_CHARACTERS.charAt(randomIndex);
        password.append(randomChar);

        for (int i = 0; i < 3; i++) {
            randomIndex = random.nextInt(LOWER_CHARACTERS.length());
            randomChar = LOWER_CHARACTERS.charAt(randomIndex);
            password.append(randomChar);
        }

        for (int i = 0; i < 2; i++) {
            randomIndex = random.nextInt(NUMBERS.length());
            randomChar = NUMBERS.charAt(randomIndex);
            password.append(randomChar);
        }
        return password.toString();
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
                        this.sloganField.setText(Slogan.getRandomSlogan().toString());
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

        this.captchaError.setText(Error.INCORRECT_CAPTCHA.toString());

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

        MultiMenuFunctions.initializePasswordFieldsWithConfirm(this.passwordShow, this.passwordConfirmShow,
                this.passwordField, this.passwordConfirmField, this.passwordError, this.passwordConfirmError,
                this.showPassword);

        this.nicknameField.textProperty().addListener((observable, oldText, newText) -> {
            if (newText.isEmpty())
                this.nicknameError.setText(Error.NECESSARY_FIELD.toString());
            else this.nicknameError.setText("");
        });

        this.emailField.textProperty().addListener((observable, oldText, newText) -> {
            if (MultiMenuFunctions.checkEmailNotOK(newText))
                this.emailError.setText(Error.INCORRECT_EMAIL_FORM.toString());

            else if (User.getUserByEmail(newText) != null)
                this.emailError.setText(Error.EMAIL_ALREADY_EXISTS.toString());

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

        this.refreshLabel.setGraphic(new ImageView(new Image(Objects.requireNonNull(RegisterMenu.class.getResource(
                "/Image/Graphic/refresh.png")).toExternalForm(), this.refreshLabel.getPrefHeight() + 20,
                this.refreshLabel.getPrefHeight(), false, false)));
    }

    private void initializeErrors() {
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
        if (!this.checkForErrors()) {
            new Alert(Alert.AlertType.ERROR, Message.EMPTY_FIELD.toString()).show();
            return;
        }

        Connection.getInstance().register(this.usernameField.getText(), this.passwordField.getText(),
                this.nicknameField.getText(), this.emailField.getText(), this.recoveryQuestions.getValue(),
                this.recoveryAnswerField.getText(), this.sloganField.getText());
        this.appController.runMenu(Result.ENTER_LOGIN_MENU);
    }

    @FXML
    private void generateRandomPassword() {
        this.passwordField.setText(this.randomPassword());
        this.showPassword.setSelected(true);
    }

    @FXML
    private void refreshCaptcha() {
        this.generateCaptcha();

        if (this.captchaController.isCaptchaInCorrect(this.captchaField.getText()))
            this.captchaError.setText(Error.INCORRECT_CAPTCHA.toString());

        else this.captchaError.setText("");
    }

    @FXML
    private void enterLoginMenu() throws Exception {
        this.appController.runMenu(Result.ENTER_LOGIN_MENU);
    }
}