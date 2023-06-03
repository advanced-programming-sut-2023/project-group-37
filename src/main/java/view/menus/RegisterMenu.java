package view.menus;

import controller.AppController;
import controller.MultiMenuFunctions;
import controller.RegisterMenuController;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.user.RecoveryQuestion;
import view.enums.Error;
import view.enums.Message;
import view.enums.Result;

import java.net.URL;
import java.util.Objects;

public class RegisterMenu extends Application {
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
        this.registerMenuController = RegisterMenuController.getInstance();
    }

    public static void main(String[] args) {
        launch(args);
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

    private void initializeFields() {
        this.usernameField.textProperty().addListener((observable, oldText, newText) -> {
            if (MultiMenuFunctions.checkUsernameNotOK(newText))
                this.usernameError.setText(Error.INCORRECT_USERNAME_FORM.toString());

            else if (newText.isEmpty())
                this.usernameError.setText(Error.NECESSARY_FIELD.toString());

            else this.usernameError.setText("");
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
        else AppController.runMenu(Result.GO_FOR_CAPTCHA);

    }

    public void generateRandomPassword() {
        this.passwordField.setText(registerMenuController.generateRandomPassword());
        this.showPassword.setSelected(true);
    }

}