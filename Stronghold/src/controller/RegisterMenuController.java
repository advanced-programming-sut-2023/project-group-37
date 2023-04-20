package controller;

import model.User;
import view.enums.commands.RegisterMenuCommands;
import view.enums.messages.RegisterMenuMessages;
import java.util.regex.Matcher;

public class RegisterMenuController {
    private static final RegisterMenuController registerMenuController = new RegisterMenuController();

    public static RegisterMenuController getInstance() {
        return registerMenuController;
    }

    private RegisterMenuController() {

    }

    private User user;
    private String password;
    private String randomPassword;
    private int delayTime = 0;

    private void saveUser() {
        //TODO : if <user> not null save <user> in file
    }

    private String deleteQuotations(String string) {
        return Controller.deleteQuotations(string);
    }

    private static boolean checkUsernameNotOK(String username) {
        return !username.matches("[A-Za-z0-9_]+");
    }

    static boolean checkPasswordNotOK(String password) {
        return !(password.matches(".*[A-Z].*") && password.matches(".*[a-z].*") &&
                password.matches(".*[0-9].*") && password.length() > 5 && password.matches(".*[^a-zA_Z0-9].*"));
    }

    private static boolean checkEmailNotOK(String email) {
        return !email.matches("[A-Za-z0-9_.]+@[a-zA-Z0-9_]+\\.[A-Za-z0-9_.]+");
    }

    private String generateRandomPassword() {
        //TODO : generate password
        return null;
    }

    public User getUser() {
        return user;
    }

    public String getRandomPassword() {
        return randomPassword;
    }

    private String generateRandomSlogan() {
        // ToDO : generate slogan
        return null;
    }

    public RegisterMenuMessages register(Matcher matcher) { //TODO : random slogan
        String username = deleteQuotations(matcher.group("username")),
                password = deleteQuotations(matcher.group("password")),
                passwordConfirm = deleteQuotations(matcher.group("passwordConfirm")),
                email = deleteQuotations(matcher.group("email")),
                nickName = deleteQuotations(matcher.group("nickName")),
                slogan = deleteQuotations(matcher.group("slogan"));

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || nickName.isEmpty())
            return RegisterMenuMessages.EMPTY_FIELD;

        if (checkUsernameNotOK(username))
            return RegisterMenuMessages.INCORRECT_USERNAME_FORM;

        if (checkPasswordNotOK(password) && !password.equals("random"))
            return RegisterMenuMessages.WEAK_PASSWORD;

        if (!password.equals("random")) {
            if (passwordConfirm.isEmpty())
                return RegisterMenuMessages.EMPTY_FIELD;

            if (!passwordConfirm.equals(password))
                return RegisterMenuMessages.INCOMPATIBLE_PASSWORDS;
        }

        if (checkEmailNotOK(email))
            return RegisterMenuMessages.INCORRECT_EMAIL_FORM;

        if (slogan.isEmpty())
            slogan = "";

        else if (slogan.equals("random"))
            slogan = generateRandomSlogan();

        user = new User(username, password, email, slogan);

        if (password.equals("random")) {
            randomPassword = generateRandomPassword();

            return RegisterMenuMessages.RANDOM_PASSWORD;
        }
        return RegisterMenuMessages.ASK_FOR_SECURITY_QUESTION;
    }

    public RegisterMenuMessages pickQuestion(Matcher matcher) {
        int questionNumber = Integer.parseInt(matcher.group("questionNumber"));

        if (questionNumber > 3 || questionNumber < 1)
            return RegisterMenuMessages.INCORRECT_QUESTION_NUMBER;

        String answer = deleteQuotations(matcher.group("answer")),
                answerConfirm = deleteQuotations(matcher.group("answerConfirm"));

        if (answer.isEmpty() || answerConfirm.isEmpty())
            return RegisterMenuMessages.EMPTY_FIELD;

        if (!answer.equals(answerConfirm))
            return RegisterMenuMessages.INCOMPATIBLE_ANSWERS;

        user.setPasswordRecoveryQuestion(questionNumber);
        user.setPasswordRecoveryAnswer(answer);

        saveUser();
        return RegisterMenuMessages.REGISTER_SUCCESSFUL;
    }

    public RegisterMenuMessages checkPasswordConfirm(String passwordConfirm) {
        if (passwordConfirm.equals("cancel"))
            return RegisterMenuMessages.CANCEL;

        if (passwordConfirm.matches("\\s*" + randomPassword + "\\s*")) {
            saveUser();
            return RegisterMenuMessages.REGISTER_SUCCESSFUL;
        }

        return RegisterMenuMessages.REENTER_AGAIN;
    }

    public RegisterMenuMessages logout() {
        if (User.getCurrentUser() == null)
            return RegisterMenuMessages.NOT_LOGGED_IN;

        User.setCurrentUser(null);
        return RegisterMenuMessages.LOGOUT_SUCCESSFUL;
    }
}