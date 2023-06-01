package view.menus;

import controller.AppController;
import controller.MultiMenuFunctions;
import controller.RegisterMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.user.RecoveryQuestion;
import view.enums.Error;
import view.enums.Message;
import view.enums.Result;

import java.net.URL;
import java.util.Objects;

public class RegisterMenu extends Application {
    private final AppController appController;
    private final RegisterMenuController registerMenuController;

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
    private PasswordField passwordField;
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

    public RegisterMenu() {
        this.appController = AppController.getInstance();
        this.registerMenuController = RegisterMenuController.getInstance();
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/registerMenu.fxml");
        BorderPane borderPane = FXMLLoader.load(Objects.requireNonNull(url));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        initializeFields();
        initializeErrors();
        initializeSlogan();

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

        this.sloganChoiceBox.setOnAction(actionEvent -> {
            if (this.sloganChoiceBox.getValue().equals("Type a slogan")) {
                this.sloganField.setDisable(false);
                this.sloganError.setText(Error.NECESSARY_FIELD.toString());
            }

            else if (this.sloganChoiceBox.getValue().equals("Random slogan"))
                sloganField.setText(registerMenuController.generateRandomSlogan());
        });
    }

    private void initializeFields() {
        this.usernameField.textProperty().addListener((observable, oldText, newText) -> {
            if (MultiMenuFunctions.checkUsernameNotOK(newText))
                this.usernameError.setText(Error.INCORRECT_USERNAME_FORM.toString());

            else if (newText.isEmpty())
                this.usernameError.setText(Error.NECESSARY_FIELD.toString());

            else this.usernameError.setText("");
        });

        this.passwordField.textProperty().addListener((observable, oldText, newText) -> {
            if (MultiMenuFunctions.checkPasswordNotOK(newText))
                this.passwordError.setText(Error.WEAK_PASSWORD.toString());

            else if (newText.isEmpty())
                this.passwordError.setText(Error.NECESSARY_FIELD.toString());

            else this.passwordError.setText("");
        });

        this.passwordConfirmField.textProperty().addListener((observable, oldText, newText) -> {
            if (!passwordField.getText().equals(newText))
                this.passwordConfirmError.setText(Error.INCOMPATIBLE_PASSWORDS.toString());

            else if (newText.isEmpty())
                this.passwordConfirmError.setText(Error.NECESSARY_FIELD.toString());

            else this.passwordConfirmError.setText("");
        });

        this.nicknameField.textProperty().addListener((observable, oldText, newText) -> {
            if (newText.isEmpty())
                nicknameError.setText(Error.NECESSARY_FIELD.toString());
            else nicknameError.setText("");
        });

        this.emailField.textProperty().addListener((observable, oldText, newText) -> {
            if (MultiMenuFunctions.checkEmailNotOK(newText))
                this.emailError.setText(Error.INCORRECT_EMAIL_FORM.toString());

            else if (newText.isEmpty())
                emailError.setText(Error.NECESSARY_FIELD.toString());

            else emailError.setText("");
        });

        this.recoveryQuestions.setOnAction(actionEvent -> {
            if (!this.recoveryQuestions.getValue().isEmpty())
                this.recoveryQuestionError.setText("");
        });

        this.recoveryAnswerField.textProperty().addListener((observable, oldText, newText) -> {
            if (newText.isEmpty())
                recoveryAnswerError.setText(Error.NECESSARY_FIELD.toString());
            else recoveryAnswerError.setText("");
        });
    }

    private void initializeErrors() { // TODO : is this function necessary ?
        usernameError.setText(Error.NECESSARY_FIELD.toString());
        passwordError.setText(Error.NECESSARY_FIELD.toString());
        passwordConfirmError.setText(Error.NECESSARY_FIELD.toString());
        nicknameError.setText(Error.NECESSARY_FIELD.toString());
        emailError.setText(Error.NECESSARY_FIELD.toString());
        recoveryQuestionError.setText(Error.NECESSARY_FIELD.toString());
        recoveryAnswerError.setText(Error.NECESSARY_FIELD.toString());
    }

    private boolean checkForErrors() {
        return usernameError.getText().isEmpty() && passwordError.getText().isEmpty() &&
                passwordConfirmError.getText().isEmpty() && nicknameError.getText().isEmpty() &&
                emailError.getText().isEmpty() && recoveryQuestionError.getText().isEmpty() &&
                recoveryAnswerError.getText().isEmpty() && sloganError.getText().isEmpty();
    }

    public void register() throws Exception {
        if (!this.checkForErrors())
            return;

        Message message = this.registerMenuController.register(usernameField.getText(), passwordField.getText(),
                nicknameField.getText(), emailField.getText(), recoveryQuestions.getValue(),
                recoveryAnswerField.getText(), sloganField.getText());

        if (message == Message.USERNAME_ALREADY_EXISTS) {
            // TODO : alert !
        }
        else appController.runMenu(Result.GO_FOR_CAPTCHA);

    }

    public void generateRandomPassword() {
        this.passwordField.setText(registerMenuController.generateRandomPassword());
        this.showPassword.setSelected(true);
        showOrHide();
    }

    public void showOrHide() {
        this.passwordField.setVisible(this.showPassword.isSelected());
        this.passwordConfirmField.setVisible(this.showPassword.isSelected());
    }
}