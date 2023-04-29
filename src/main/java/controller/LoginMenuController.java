package controller;

import model.user.User;
import view.enums.Command;
import view.enums.Message;

import java.util.regex.Matcher;

public class LoginMenuController {
    private User user;
    private String password;
    private int delayTime = 0;
    private String deleteQuotations(String string) {
        return MultiMenuFunctions.deleteQuotations(string);
    }
    private static final LoginMenuController loginMenuController = new LoginMenuController();

    // TODO: what tf does these fields & ctor do here?
    LoginMenuController() {

    }

    public static LoginMenuController getInstance() {
        return loginMenuController;
    }

    public Message login(Matcher matcher) {
        String username = deleteQuotations(matcher.group("username")),
                password = deleteQuotations(matcher.group("password"));

        boolean stayLoggedIn = matcher.group("stayLoggedIn") != null;

        user = User.getUserByUsername(username);
        if (user == null)
            return Message.USER_NOT_EXISTS;

        if (user.isWrongPassword(password)) {
            // TODO : delay time
            delayTime += 5;
            return Message.INCORRECT_PASSWORD;
        }

        delayTime = 0;

        MainMenuController.setCurrentUser(user);
        if (stayLoggedIn)
            User.setStayLoggedIn(user);

        return Message.LOGIN_SUCCESSFUL;
    }

    public Message forgotPassword(Matcher matcher) {
        String username = matcher.group("username");

        user = User.getUserByUsername(username);
        if (user == null)
            return Message.USER_NOT_EXISTS;

        return Message.ASK_QUESTION;
    }

    public Message answerSecurityQuestion(String answer) {
        if (user.isCorrectAnswer(answer))
            return Message.ENTER_NEW_PASSWORD;

        return Message.INCORRECT_ANSWER;
    }

    public Message getNewPassword(String newPassword) {
        if (Command.CANCEL.getMatcher(newPassword) != null)
            return Message.CANCEL;

        if (RegisterMenuController.checkPasswordNotOK(newPassword))
            return Message.WEAK_PASSWORD;

        password = newPassword;
        return Message.ENTER_NEW_PASSWORD_AGAIN;
    }

    // TODO: merge!
    public Message getNewPasswordAgain(String newPassword) {
        if (Command.CANCEL.getMatcher(newPassword) != null)
            return Message.CANCEL;

        if (!newPassword.equals(password))
            return Message.INCOMPATIBLE_PASSWORDS;

        user.changePassword(newPassword);
        return Message.CHANGE_PASSWORD_SUCCESSFUL;
    }

    public User getUser() {
        return user;
    }
}
