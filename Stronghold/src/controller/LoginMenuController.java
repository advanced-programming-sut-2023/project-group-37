package controller;

import model.Model;
import model.user.User;
import view.enums.commands.RegisterMenuCommands;
import view.enums.messages.LoginMenuMessages;

import java.util.regex.Matcher;

public class LoginMenuController {
    private User user;
    private String password;
    private int delayTime = 0;
    private String deleteQuotations(String string) {
        return Model.deleteQuotations(string);
    }
    private static final LoginMenuController loginMenuController = new LoginMenuController();

    LoginMenuController() {

    }

    public static LoginMenuController getInstance() {
        return loginMenuController;
    }

    public LoginMenuMessages login(Matcher matcher) {
        String username = deleteQuotations(matcher.group("username")),
                password = deleteQuotations(matcher.group("password"));

        boolean stayLoggedIn = matcher.group("stayLoggedIn") != null;
        // TODO : set stayLoggedIn

        user = User.getUserByUsername(username);
        if (user == null)
            return LoginMenuMessages.USER_NOT_EXISTS;

        if (!user.isCorrectPassword(password)) {
            // TODO : delay time
            delayTime += 5;
            return LoginMenuMessages.INCORRECT_PASSWORD;
        }

        delayTime = 0;

        MainMenuController.setCurrentUser(user);
        return LoginMenuMessages.LOGIN_SUCCESSFUL;
    }

    public LoginMenuMessages forgot(Matcher matcher) {
        String username = matcher.group("username");

        user = User.getUserByUsername(username);
        if (user == null)
            return LoginMenuMessages.USER_NOT_EXISTS;

        return LoginMenuMessages.ASK_QUESTION;
    }

    public LoginMenuMessages answerRecoveryQuestion(String answer) {
        if (user.isCorrectAnswer(answer))
            return LoginMenuMessages.ENTER_NEW_PASSWORD;

        return LoginMenuMessages.INCORRECT_ANSWER;
    }

    public LoginMenuMessages enterNewPassword(String newPassword) {
        if (RegisterMenuCommands.getMatcher(newPassword, RegisterMenuCommands.CANCEL) != null)
            return LoginMenuMessages.CANCEL;

        if (RegisterMenuController.checkPasswordNotOK(newPassword))
            return LoginMenuMessages.WEAK_PASSWORD;

        password = newPassword;
        return LoginMenuMessages.ENTER_NEW_PASSWORD_AGAIN;
    }

    public LoginMenuMessages enterNewPasswordAgain(String newPassword) {
        if (RegisterMenuCommands.getMatcher(newPassword, RegisterMenuCommands.CANCEL) != null)
            return LoginMenuMessages.CANCEL;

        if (!newPassword.equals(password))
            return LoginMenuMessages.INCOMPATIBLE_PASSWORDS;

        user.changePassword(newPassword);
        return LoginMenuMessages.CHANGE_PASSWORD_SUCCESSFUL;
    }

    public User getUser() {
        return user;
    }
}
