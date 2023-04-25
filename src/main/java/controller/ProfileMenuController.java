package controller;

import model.Model;
import model.user.User;
import view.enums.Command;
import view.enums.Message;

import java.util.regex.Matcher;

public class ProfileMenuController {
    private static User currentUser;
    private String password;

    public static void setCurrentUser(User currentUser) {
        ProfileMenuController.currentUser = currentUser;
    }

    public String deleteQuotation(String string) {
        return Model.deleteQuotations(string);
    }

    public Message changeUsername(Matcher matcher) {
        if (matcher.group("username") == null) {
            return Message.CHANGE_USERNAME_ERROR1;
        }
        String username = deleteQuotation(matcher.group("username"));
        if (RegisterMenuController.checkUsernameNotOK(username)) {
            return Message.CHANGE_USERNAME_ERROR2;
        }
        if (username.equals(currentUser.getUsername())) {
            return Message.CHANGE_USERNAME_ERROR3;
        }
        currentUser.setUsername(username);
        return Message.CHANGE_USERNAME;
    }

    public Message changeNickName(Matcher matcher) {
        if (matcher.group("nickname") == null) {
            return Message.CHANGE_NICKNAME_ERROR1;
        }
        String nickname = matcher.group("nickname");
        if (nickname.equals(currentUser.getNickName())) {
            return Message.CHANGE_NICKNAME_ERROR2;
        }
        currentUser.setNickName(nickname);
        return Message.CHANGE_NICKNAME;
    }

    public Message changePassword(Matcher matcher) {
        if (matcher.group("oldPassword") == null || matcher.group("newPassword") == null) {
            return Message.CHANGE_PASSWORD_ERROR1;
        }
        String oldPass = matcher.group("oldPassword");
        String newPass = matcher.group("newPassword");
        if (currentUser.isWrongPassword(oldPass)) {
            return Message.CHANGE_PASSWORD_ERROR2;
        }
        if (RegisterMenuController.checkPasswordNotOK(newPass)) {
            return Message.CHANGE_PASSWORD_ERROR4;
        }
        password = matcher.group("newPassword");
        return Message.ENTER_PASSWORD_AGAIN;
    }

    public Message checkPasswordAgain(String newPassword){
        if (Command.CANCEL.getMatcher(newPassword) != null)
            return Message.CANCEL;

        if(!newPassword.equals(password))
            return Message.CHANGE_PASSWORD_ERROR5;

        currentUser.changePassword(newPassword);
        return Message.CHANGE_PASSWORD;
    }


    public Message changeEmail(Matcher matcher) {
        String email = matcher.group("email");
        if (email.isEmpty()) {
            return Message.CHANGE_EMAIL_ERROR3;
        }
        if (RegisterMenuController.checkEmailNotOK(email)) {
            return Message.CHANGE_EMAIL_ERROR1;
        }
        if (User.getUserByEmail(email) != null) {
            return Message.CHANGE_EMAIL_ERROR2;
        }
        currentUser.setEmail(matcher.group("email"));
        return Message.CHANGE_EMAIL;
    }

    public Message changeSlogan(Matcher matcher) {
        if (matcher.group("slogan").isEmpty()) {
            return Message.CHANGE_SLOGAN_ERROR1;
        }
        currentUser.setSlogan(matcher.group("slogan"));
        return Message.CHANGE_SLOGAN;
    }

    public Message removeSlogan() {
        currentUser.setSlogan(null);
        return Message.REMOVE_SLOGAN;
    }

    public int showScore() {
        return currentUser.getHighScore();
    }

    public int showRank() {
        return currentUser.getRank();
    }

    public String showSlogan() {
        if (currentUser.getSlogan() == null) {
            return "Empty slogan!";
        }
        return currentUser.getSlogan();
    }

    public String showProfile() {
        String info = "Info:\n";
        info += "Username: " + currentUser.getUsername() + "\n";
        info += "Nickname: " + currentUser.getNickName() + "\n";
        info += "Email: " + currentUser.getEmail() + "\n";
        info += "Slogan: " + currentUser.getSlogan() + "\n";
        info += "HighScore: " + currentUser.getHighScore() + "\n";
        info += "Rank: " + currentUser.getRank() + "\n";
        return info.trim();
    }
}
