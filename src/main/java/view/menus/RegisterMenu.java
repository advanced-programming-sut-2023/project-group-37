package view.menus;

import controller.AppController;
import controller.RegisterMenuController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.user.RecoveryQuestion;
import model.utils.Captcha;
import view.enums.Message;
import view.enums.Result;
import view.enums.Command;

import java.net.URL;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

public class RegisterMenu extends Application {
    private final AppController appController;
    private final RegisterMenuController registerMenuController;
    private final Scanner scanner;
    private String command;
    private String message;

    @FXML
    private ChoiceBox<String> recoveryQuestions;
    @FXML
    private ChoiceBox<String> sloganChoiceBox;
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
    @FXML
    private Label usernameError;
    @FXML
    private Label passwordError;
    @FXML
    private Label PasswordConfirmError;
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
        this.scanner = new Scanner(System.in);
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
        for (RecoveryQuestion recoveryQuestion : RecoveryQuestion.values()) {
            recoveryQuestions.getItems().add(recoveryQuestion.getQuestion());
        }

        sloganChoiceBox.getItems().add("No slogan");
        sloganChoiceBox.getItems().add("Type a slogan");
        sloganChoiceBox.getItems().add("Random slogan");

        sloganChoiceBox.setValue("No slogan");
    }

    public Result run() {
        Matcher matcher;

        while (true) {
            this.command = scanner.nextLine();

            if ((matcher = Command.REGISTER.getMatcher(this.command)) != null
                    || (matcher = Command.REGISTER_RANDOM_PASSWORD.getMatcher(this.command)) != null) {
                if (register(matcher))
                    return Result.ENTER_LOGIN_MENU;
            }

            else if (command.matches(Command.ENTER_LOGIN_MENU.toString())) {
                System.out.println(Message.ENTERED_LOGIN_MENU);
                return Result.ENTER_LOGIN_MENU;
            }

            else if (command.matches(Command.EXIT.toString()))
                return Result.EXIT;

            else
                System.out.println(Message.INVALID_COMMAND);
        }
    }

    private boolean pickQuestion() {
        Matcher matcher;

        while (true) {
            this.command = this.scanner.nextLine();

            if ((matcher = Command.PICK_QUESTION.getMatcher(this.command)) != null) {
                this.message = this.registerMenuController.pickQuestion(matcher);
                System.out.println(this.message);

                if (Message.DO_CAPTCHA.equals(this.message)) {
                    Captcha captcha = new Captcha();
                    captcha.createCaptcha();
                    while (!command.equals("cancel")) {
                        this.command = scanner.nextLine();
                        if (command.equals(captcha.getCaptchaNumber())) {
                            System.out.println(registerMenuController.captcha());
                            return true;
                        } else if (!command.equals("cancel")) {
                            System.out.println(Message.WRONG_CAPTCHA);
                        }
                    }
                    System.out.println(Message.CANCEL);
                    return false;
                }
            } else if (Command.CANCEL.getMatcher(this.command) != null) {
                System.out.println(Message.CANCEL);
                return false;
            } else
                System.out.println(Message.INVALID_COMMAND);
        }
    }

    private boolean register(Matcher matcher) {
        this.message = this.registerMenuController.register(matcher);
        System.out.println(message);

        if (this.message.contains("re-enter")) {
            do {
                this.command = this.scanner.nextLine();

                this.message = this.registerMenuController.checkPasswordConfirm(this.command);
                System.out.println(this.message);

            } while (Message.REENTER_AGAIN.equals(message));

            if (Message.ASK_FOR_SECURITY_QUESTION.equals(this.message))
                return pickQuestion();
        } else if (this.message.contains("Pick"))
            return pickQuestion();


        return false;
    }

    public void register() {

    }
}