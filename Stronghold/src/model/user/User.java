package model.user;

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
    }

    public static User getUserByUsername(String username) {
        // TODO: get data from db!
        return null;
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

    public String getUsername() {
        return this.username;
    }

    public String getNickname() {
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

    public void setNickname(String nickname) {
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
}
