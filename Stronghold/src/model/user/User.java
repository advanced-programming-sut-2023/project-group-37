package model.user;

import java.util.ArrayList;

public class User {

    private static User currentUser;

    private String username;
    private String password;
    private String nickname;
    private String slogan;
    private String email;
    private String securityQuestion;
    private String securityQuestionAnswer;
    private int highScore;
    private int rank;
    private static final ArrayList<User> users = new ArrayList<>();

    public static User getCurrentUser() {
        return User.currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }

    public User(String username, String password, String email, String slogan) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.slogan = slogan;
        users.add(this);
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }

        return null;
    }

    public static User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email))
                return user;
        }

        return null;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public User(String username, String password, String nickname, String slogan, String email, int questionNumber,
                String answer) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.slogan = slogan;
        this.email = email;
        this.securityQuestion = SecurityQuestion.getQuestion(questionNumber);
        this.securityQuestionAnswer = answer;
        this.highScore = 0;
    }

    public int getRank() {
        return rank;
    }

    public String getUsername() {
        return this.username;
    }

    public String getNickName() {
        return this.nickname;
    }

    public String getSlogan() {
        return this.slogan;
    }

    public String getEmail() {
        return this.email;
    }

    public String getSecurityQuestion() {
        return this.securityQuestion;
    }

    public int getHighScore() {
        return this.highScore;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void setNickName(String nickname) {
        this.nickname = nickname;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSecurityQuestion(int questionNumber) {
        this.securityQuestion = SecurityQuestion.getQuestion(questionNumber);
    }

    public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public boolean isCorrectPassword(String password) {
        return this.password.equals(password);
    }

    public boolean isCorrectAnswer(String answer) {
        return this.securityQuestionAnswer.equals(answer);
    }

    public String getPasswordRecoveryQuestion() {
        return securityQuestion;
    }
}
