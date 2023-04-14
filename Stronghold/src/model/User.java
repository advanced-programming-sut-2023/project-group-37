package model;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String nickName;
    private String email;
    private String slogan;
    private String passwordRecoveryQuestion;
    private String passwordRecoveryAnswer;
    private int highScore = 0;
    private int rank;

    private static final ArrayList<User> users = new ArrayList<>();
    private static User currentUser;

    public User(String username, String password, String email, String slogan) {
        this.username= username;
        this.password = password;
        this.email = email;
        this.slogan = slogan;
        users.add(this);
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setPasswordRecoveryQuestion(int number) {
        switch (number) {
            case 1 -> passwordRecoveryQuestion = "What is my father’s name?";
            case 2 -> passwordRecoveryQuestion = "What was my first pet’s name?";
            case 3 -> passwordRecoveryQuestion = "What is my mother’s last name?";
        }
    }

    public void setPasswordRecoveryAnswer(String passwordRecoveryAnswer) {
        this.passwordRecoveryAnswer = passwordRecoveryAnswer;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeUsername(String username) {
        this.username = username;
    }

    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeSlogan(String slogan) {
        this.slogan = slogan;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
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

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

}
