package controller.viewControllers;

import controller.MultiMenuFunctions;
import model.user.User;
import model.utils.PasswordHashing;
import view.enums.Command;
import view.enums.Message;

import java.util.regex.Matcher;

public class LoginMenuController {
    private static final LoginMenuController loginMenuController;
    private User user;
    private String password;
    private int delayTime = 0;

    static {
        loginMenuController = new LoginMenuController();
    }

    private LoginMenuController() {

    }

    public static LoginMenuController getInstance() {
        return loginMenuController;
    }

    public int getDelayTime() {
        return delayTime;
    }

    private String deleteQuotations(String string) {
        return MultiMenuFunctions.deleteQuotations(string);
    }

    public String login(Matcher matcher) {
        String username = deleteQuotations(matcher.group("username")),
                password = deleteQuotations(matcher.group("password"));

        boolean stayLoggedIn = matcher.group("stayLoggedIn") != null;

        user = User.getUserByUsername(username);
        if (user == null)
            return Message.USER_NOT_EXISTS.toString();

        if (user.isWrongPassword(password)) {
            delayTime += 5;
            return Message.INCORRECT_PASSWORD.toString();
        }

        delayTime = 0;

        MainMenuController.setCurrentUser(user);
        if (stayLoggedIn)
            User.setStayLoggedIn(user);

        return Message.LOGIN_SUCCESSFUL.toString();
    }

    public String forgotPassword(Matcher matcher) {
        String username = matcher.group("username");

        user = User.getUserByUsername(username);
        if (user == null)
            return Message.USER_NOT_EXISTS.toString();

        return Message.ASK_QUESTION.toString();
    }

    public String answerSecurityQuestion(String answer) {
        if (user.isCorrectAnswer(answer))
            return Message.ENTER_NEW_PASSWORD.toString();

        return Message.INCORRECT_ANSWER.toString();
    }

    public String getNewPassword(String newPassword) {
        if (Command.CANCEL.getMatcher(newPassword) != null)
            return Message.CANCEL.toString();

        if (MultiMenuFunctions.checkPasswordNotOK(newPassword))
            return Message.WEAK_PASSWORD.toString();
        //save hashed password
        password = PasswordHashing.encode(newPassword);
        return Message.ENTER_NEW_PASSWORD_AGAIN.toString();
    }

    public String getNewPasswordAgain(String newPassword) {
        if (Command.CANCEL.getMatcher(newPassword) != null)
            return Message.CANCEL.toString();

        if (!PasswordHashing.checkPassword(newPassword, password))
            return Message.INCOMPATIBLE_PASSWORDS.toString();
        user.setHashedPassword(password);

        User.updateDatabase();
        return Message.CHANGE_PASSWORD_SUCCESSFUL.toString();
    }

    public User getUser() {
        return user;
    }
}
