package controller;

import model.Model;
import model.User;
import model.enums.Slogans;
import view.enums.messages.RegisterMenuMessages;

import java.security.SecureRandom;
import java.util.regex.Matcher;

public class RegisterMenuController {
    private static final RegisterMenuController registerMenuController = new RegisterMenuController();

    public static RegisterMenuController getInstance() {
        return registerMenuController;
    }

    RegisterMenuController() {

    }

    private User user;
    private String password;
    private String randomPassword;
    private String randomSlogan = null;
    private int delayTime = 0;

    private void saveUser() {
        //TODO : if <user> not null save <user> in file
    }

    private String deleteQuotations(String string) {
        return Model.deleteQuotations(string);
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
        SecureRandom random = new SecureRandom();

        String LOWER_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
        String UPPER_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String NUMBERS = "0123456789";
        String OTHER_CHARACTERS = "?!@#$%^&*_+=-/:;.><";

        StringBuilder password = new StringBuilder();
        int randomIndex;
        char randomChar;

        for (int i=0; i<3; i++) {
            randomIndex = random.nextInt(UPPER_CHARACTERS.length());
            randomChar = UPPER_CHARACTERS.charAt(randomIndex);
            password.append(randomChar);
        }
        randomIndex = random.nextInt(OTHER_CHARACTERS.length());
        randomChar = OTHER_CHARACTERS.charAt(randomIndex);
        password.append(randomChar);

        for (int i=0; i<3; i++) {
            randomIndex = random.nextInt(LOWER_CHARACTERS.length());
            randomChar = LOWER_CHARACTERS.charAt(randomIndex);
            password.append(randomChar);
        }

        for (int i=0; i<2; i++) {
            randomIndex = random.nextInt(NUMBERS.length());
            randomChar = NUMBERS.charAt(randomIndex);
            password.append(randomChar);
        }

        randomPassword = password.toString();
        return randomPassword;
    }

    public User getUser() {
        return user;
    }

    public String getRandomPassword() {
        return randomPassword;
    }

    public String getRandomSlogan() {
        String changer = randomSlogan;
        randomSlogan = null;
        return changer;
    }

    private String generateRandomSlogan() {
        return Slogans.getRandomSlogan().toString();
    }

    public RegisterMenuMessages register(Matcher matcher) { //TODO : random slogan
        String username = deleteQuotations(matcher.group("username")),
                password = deleteQuotations(matcher.group("password")),
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
            String passwordConfirm = deleteQuotations(matcher.group("passwordConfirm"));

            if (passwordConfirm.isEmpty())
                return RegisterMenuMessages.EMPTY_FIELD;

            if (!passwordConfirm.equals(password))
                return RegisterMenuMessages.INCOMPATIBLE_PASSWORDS;
        }
        else {
            randomPassword = generateRandomPassword();
            password = randomPassword;
        }

        if (checkEmailNotOK(email))
            return RegisterMenuMessages.INCORRECT_EMAIL_FORM;

        if (slogan.isEmpty())
            slogan = "";

        else if (slogan.equals("random")) {
            randomSlogan = generateRandomSlogan();
            slogan = randomSlogan;
        }

        user = new User(username, password, email, slogan);

        if (password.equals(randomPassword))
            return RegisterMenuMessages.RANDOM_PASSWORD;

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
            return RegisterMenuMessages.ASK_FOR_SECURITY_QUESTION;
        }

        return RegisterMenuMessages.REENTER_AGAIN;
    }
}