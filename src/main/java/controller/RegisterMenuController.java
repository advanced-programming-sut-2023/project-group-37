package controller;

import model.user.SecurityQuestion;
import model.user.Slogan;
import model.user.User;
import model.utils.Captcha;
import view.enums.Message;

import java.security.SecureRandom;
import java.util.regex.Matcher;

public class RegisterMenuController {

    // TODO: why these fields?

    private User user;
    private String randomPassword;

    // TODO: handle countdown!
//    private int delayTime = 0;

    private void saveUser() {
        User.saveUsersToFile();
    }

    private String deleteQuotations(String string) {
        return MultiMenuFunctions.deleteQuotations(string);
    }

    static boolean checkUsernameNotOK(String username) {
        return !username.matches("[A-Za-z0-9_]+");
    }

    static boolean checkPasswordNotOK(String password) {
        return !(password.matches(".*[A-Z].*") && password.matches(".*[a-z].*") &&
                password.matches(".*[0-9].*") && password.length() > 5 && password.matches(".*[^a-zA_Z0-9].*"));
    }

    static boolean checkEmailNotOK(String email) {
        return !email.matches("[A-Za-z0-9_.]+@[a-zA-Z0-9_]+\\.[A-Za-z0-9_.]+");
    }

    private void generateRandomPassword() {
        SecureRandom random = new SecureRandom();

        String LOWER_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
        String UPPER_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String NUMBERS = "0123456789";
        String OTHER_CHARACTERS = "?!@#$%^&*_+=-/:;.><";

        StringBuilder password = new StringBuilder();
        int randomIndex;
        char randomChar;

        for (int i = 0; i < 3; i++) {
            randomIndex = random.nextInt(UPPER_CHARACTERS.length());
            randomChar = UPPER_CHARACTERS.charAt(randomIndex);
            password.append(randomChar);
        }
        randomIndex = random.nextInt(OTHER_CHARACTERS.length());
        randomChar = OTHER_CHARACTERS.charAt(randomIndex);
        password.append(randomChar);

        for (int i = 0; i < 3; i++) {
            randomIndex = random.nextInt(LOWER_CHARACTERS.length());
            randomChar = LOWER_CHARACTERS.charAt(randomIndex);
            password.append(randomChar);
        }

        for (int i = 0; i < 2; i++) {
            randomIndex = random.nextInt(NUMBERS.length());
            randomChar = NUMBERS.charAt(randomIndex);
            password.append(randomChar);
        }

        randomPassword = password.toString();
    }

    public User getUser() {
        return user;
    }

    private String generateRandomSlogan() {
        return Slogan.getRandomSlogan().toString();
    }

    public String register(Matcher matcher) { //TODO : random slogan
        String username = deleteQuotations(matcher.group("username")),
                password = deleteQuotations(matcher.group("password")),
                email = deleteQuotations(matcher.group("email")),
                nickName = deleteQuotations(matcher.group("nickName")),
                slogan = deleteQuotations(matcher.group("slogan"));

        String randomMessages = "";

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || nickName.isEmpty())
            return Message.EMPTY_FIELD.toString();
        if (checkUsernameNotOK(username))
            return Message.INCORRECT_USERNAME_FORM.toString();
        if (User.getUserByUsername(username) != null)
            return Message.USERNAME_ALREADY_EXISTS.toString();
        if (checkPasswordNotOK(password) && !password.equals("random"))
            return Message.WEAK_PASSWORD.toString();

        if (slogan.equals("random")) {
            slogan = generateRandomSlogan();
            randomMessages += "Your slogan is \"" + slogan + "\"\n";
        }

        if (!password.equals("random")) {
            String passwordConfirm = deleteQuotations(matcher.group("passwordConfirm"));
            if (passwordConfirm.isEmpty())
                return Message.EMPTY_FIELD.toString();
            if (!passwordConfirm.equals(password))
                return Message.INCOMPATIBLE_PASSWORDS.toString();
        } else {
            generateRandomPassword();
            this.user = new User(username, randomPassword, email, slogan, nickName);
            randomMessages += "Your random password is: " + randomPassword + "\nPlease re-enter your password here:";
            return randomMessages;
        }

        if (checkEmailNotOK(email))
            return Message.INCORRECT_EMAIL_FORM.toString();

        this.user = new User(username, password, email, slogan, nickName);

        return randomMessages + Message.ASK_FOR_SECURITY_QUESTION;
    }

    public String pickQuestion(Matcher matcher) { // TODO : remove user if canceled
        int questionNumber = Integer.parseInt(matcher.group("questionNumber"));

        if (questionNumber > SecurityQuestion.values().length || questionNumber < 1)
            return Message.INCORRECT_QUESTION_NUMBER.toString();

        String answer = deleteQuotations(matcher.group("answer")),
                answerConfirm = deleteQuotations(matcher.group("answerConfirm"));

        if (answer.isEmpty() || answerConfirm.isEmpty())
            return Message.EMPTY_FIELD.toString();

        if (!answer.equals(answerConfirm))
            return Message.INCOMPATIBLE_ANSWERS.toString();

        user.setSecurityQuestion(questionNumber);
        user.setSecurityQuestionAnswer(answer);
        return Message.DO_CAPTCHA.toString();
    }

    public String checkPasswordConfirm(String passwordConfirm) {
        if (passwordConfirm.equals("cancel")) {
            User.deleteUser(user);
            return Message.CANCEL.toString();
        }

        if (passwordConfirm.equals(randomPassword)) {
            return Message.ASK_FOR_SECURITY_QUESTION.toString();
        }

        return Message.REENTER_AGAIN.toString();
    }

    public Message captcha() {
        saveUser();
        return Message.REGISTER_SUCCESSFUL;
    }
}