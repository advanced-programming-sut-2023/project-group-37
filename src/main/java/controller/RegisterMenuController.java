package controller;

import model.user.RecoveryQuestion;
import model.user.Slogan;
import model.user.User;
import model.utils.PasswordHashing;
import view.enums.Message;

import java.security.SecureRandom;
import java.util.regex.Matcher;

public class RegisterMenuController {

    private static final RegisterMenuController registerMenuController;
    private User user;

    static {
        registerMenuController = new RegisterMenuController();
    }

    private RegisterMenuController() {

    }

    public static RegisterMenuController getInstance() {
        return registerMenuController;
    }

    private void saveUser() {
        User.updateDatabase();
    }

    private String deleteQuotations(String string) {
        return MultiMenuFunctions.deleteQuotations(string);
    }

    public String generateRandomPassword() {
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
       return password.toString();
    }

    public String generateRandomSlogan() {
        return Slogan.getRandomSlogan().toString();
    }

    public Message register(String username, String password, String nickName, String email,
                           String recoveryQuestion, String recoveryAnswer, String slogan) {
        if (User.getUserByUsername(username) != null)
            return Message.USERNAME_ALREADY_EXISTS;

        this.user = new User(username, PasswordHashing.encode(password), nickName, email,
                recoveryQuestion, recoveryAnswer, slogan);

        return Message.GO_FOR_CAPTCHA;
    }

    public String pickQuestion(Matcher matcher) {
        int questionNumber = Integer.parseInt(matcher.group("questionNumber"));

        if (questionNumber > RecoveryQuestion.values().length || questionNumber < 1)
            return Message.INCORRECT_QUESTION_NUMBER.toString();

        String answer = deleteQuotations(matcher.group("answer")),
                answerConfirm = deleteQuotations(matcher.group("answerConfirm"));

        if (answer.isEmpty() || answerConfirm.isEmpty())
            return Message.EMPTY_FIELD.toString();

        if (!answer.equals(answerConfirm))
            return Message.INCOMPATIBLE_ANSWERS.toString();

        user.setRecoveryQuestion(questionNumber);
        user.setRecoveryAnswer(answer);
        return Message.DO_CAPTCHA.toString();
    }

    public String checkPasswordConfirm(String passwordConfirm) {
        if (passwordConfirm.equals("cancel")) {
            User.deleteUser(user);
            return Message.CANCEL.toString();
        }

        if (!user.isWrongPassword(passwordConfirm)) {
            return Message.ASK_FOR_SECURITY_QUESTION.toString();
        }

        return Message.REENTER_AGAIN.toString();
    }

    public Message captcha() {
        saveUser();
        return Message.REGISTER_SUCCESSFUL;
    }
}