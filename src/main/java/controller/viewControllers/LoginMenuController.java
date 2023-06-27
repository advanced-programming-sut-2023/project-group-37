package controller.viewControllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.user.User;
import view.enums.Message;


public class LoginMenuController {
    private static final LoginMenuController loginMenuController;

    private User user;

    static {
        loginMenuController = new LoginMenuController();
    }

    private LoginMenuController() {

    }

    public static LoginMenuController getInstance() {
        return loginMenuController;
    }

    public Message login(String username, String password, boolean stayLoggedIn) {
        User user = User.getUserByUsername(username);

        if (user == null)
            return Message.USER_NOT_EXISTS;

        if (user.isWrongPassword(password))
            return Message.INCORRECT_PASSWORD;

        MainMenuController.setCurrentUser(user);
        if (stayLoggedIn)
            User.setStayLoggedIn(user);

        return Message.LOGIN_SUCCESSFUL;
    }

    public Message findUser(String username, Label questionLabel) {
        if (User.getUserByUsername(username) == null)
            return Message.USER_NOT_EXISTS;

        this.user = User.getUserByUsername(username);

        questionLabel.setText("Question:  " + this.user.getRecoveryQuestion());
        return null;
    }

    public Message changePassword(TextField answerField, PasswordField passwordField) {
        if (this.user.isWrongAnswer(answerField.getText()))
            return Message.INCORRECT_ANSWER;

        this.user.setPassword(passwordField.getText());
        User.updateDatabase();
        return Message.CHANGE_PASSWORD;
    }
}
