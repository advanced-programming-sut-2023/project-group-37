package view.menus;

import controller.AppController;
import controller.CaptchaController;
import controller.MultiMenuFunctions;
import controller.viewControllers.ChangePasswordMenuController;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.enums.Error;
import view.enums.Message;
import view.enums.Result;

import java.net.URL;
import java.util.Objects;

public class ChangePasswordMenu extends Application {
    private final AppController appController;
    private final ChangePasswordMenuController changePasswordMenuController;
    private final CaptchaController captchaController;
    @FXML
    private TextField captcha;
    @FXML
    private TextField oldPassword;
    @FXML
    private TextField newPassword;

    @FXML
    private Label captchaErrorLabel;
    @FXML
    private Label errorLabel;

    @FXML
    private ImageView captchaImage;


    public ChangePasswordMenu() {
        this.appController = AppController.getInstance();
        this.changePasswordMenuController = ChangePasswordMenuController.getInstance();
        this.captchaController = new CaptchaController();
    }


    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/ChangePasswordMenu.fxml");
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(url));

        MultiMenuFunctions.setBackground(anchorPane, "registration-bg.jpg");

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        errorLabel.setText(Error.NECESSARY_FIELD.toString());
        this.initializeCaptcha();
        this.updateError();
    }

    private void updateError() {
        newPassword.textProperty().addListener((observable, oldText, newText) -> {

            if (MultiMenuFunctions.checkPasswordNotOK(newText))
                errorLabel.setText(Error.WEAK_PASSWORD.toString());

            else if (newText.isEmpty())
                errorLabel.setText(Error.NECESSARY_FIELD.toString());

            else errorLabel.setText("");
        });
    }

    private void initializeCaptcha() {
        this.captchaController.generateCaptcha();
        this.captchaImage.setImage(captchaController.getCaptchaImage());

        this.captchaErrorLabel.setText(Error.INCORRECT_CAPTCHA.toString());

        this.captcha.textProperty().addListener((
                ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            if (this.captchaController.isCaptchaInCorrect(new_val))
                this.captchaErrorLabel.setText(Error.INCORRECT_CAPTCHA.toString());

            else if (new_val.isEmpty()) this.captchaErrorLabel.setText(Error.NECESSARY_FIELD.toString());

            else this.captchaErrorLabel.setText("");
        });
    }

    private boolean hasError() {
        return !(errorLabel.getText().isEmpty() && captchaErrorLabel.getText().isEmpty());
    }


    public void changePassword(MouseEvent mouseEvent) throws Exception {
        if (hasError()) {
            new Alert(Alert.AlertType.ERROR, Message.EMPTY_FIELD.toString()).show();
            return;
        }
        Message message = changePasswordMenuController.changePassword(oldPassword.getText(), newPassword.getText());
        if (message.equals(Message.CHANGE_PASSWORD_ERROR2)) {
            new Alert(Alert.AlertType.ERROR, Message.CHANGE_PASSWORD_ERROR2.toString()).show();
        } else {
            new Alert(Alert.AlertType.INFORMATION, Message.CHANGE_PASSWORD.toString()).show();
            appController.runMenu(Result.ENTER_PROFILE_MENU);
        }
    }

    public void refreshCaptcha(MouseEvent mouseEvent) {
        this.captchaController.generateCaptcha();
        this.captchaImage.setImage(captchaController.getCaptchaImage());
    }

    public void enterProfileMenu(MouseEvent mouseEvent) throws Exception {
        appController.runMenu(Result.ENTER_PROFILE_MENU);
    }
}
