package controller.viewControllers;

import connection.Connection;
import connection.packet.LoginPacket;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.user.User;
import view.enums.Message;

import java.io.IOException;


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

    public void login(String username, String password, boolean stayLoggedIn) {
        try {
            Connection.getInstance().getDataOutputStream().writeUTF(new LoginPacket(username, password).toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Connection.getInstance().setStayLoggedIn(stayLoggedIn);
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
