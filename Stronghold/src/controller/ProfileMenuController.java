package controller;

import model.user.User;
import view.enums.commands.ProfileMenuCommands;
import view.enums.messages.ProfileMenuMessages;

import java.util.regex.Matcher;

public class ProfileMenuController {
    private static User currentUser;
    private String password;

    public static void setCurrentUser(User currentUser) {
        ProfileMenuController.currentUser = currentUser;
    }

    public String deleteQuotation(String s) {
        return null;
    }

    public ProfileMenuMessages changeUsername(Matcher matcher) {
        if (matcher.group("username") == null) {
            return ProfileMenuMessages.CHANGE_USERNAME_ERROR1;
        }
        String username = deleteQuotation(matcher.group("username"));
        if (RegisterMenuController.checkUsernameNotOK(username)) {
            return ProfileMenuMessages.CHANGE_USERNAME_ERROR2;
        }
        if (username.equals(currentUser.getUsername())) {
            return ProfileMenuMessages.CHANGE_USERNAME_ERROR3;
        }
        currentUser.setUsername(username);
        return ProfileMenuMessages.CHANGE_USERNAME;
    }

    public ProfileMenuMessages changeNickName(Matcher matcher) {
        if (matcher.group("nickname") == null) {
            return ProfileMenuMessages.CHANGE_NICKNAME_ERROR1;
        }
        String nickname = matcher.group("nickname");
        if (nickname.equals(currentUser.getNickName())) {
            return ProfileMenuMessages.CHANGE_NICKNAME_ERROR2;
        }
        currentUser.setNickName(nickname);
        return ProfileMenuMessages.CHANGE_NICKNAME;
    }

    public ProfileMenuMessages changePassword(Matcher matcher) {
        if (matcher.group("oldPassword") == null || matcher.group("newPassword") == null) {
            return ProfileMenuMessages.CHANGE_PASSWORD_ERROR1;
        }
        String oldPass = matcher.group("oldPassword");
        String newPass = matcher.group("newPassword");
        if (!currentUser.isCorrectPassword(oldPass)) {
            return ProfileMenuMessages.CHANGE_PASSWORD_ERROR2;
        }
        if (RegisterMenuController.checkPasswordNotOK(newPass)) {
            return ProfileMenuMessages.CHANGE_PASSWORD_ERROR4;
        }
        password = matcher.group("newPassword");
        return ProfileMenuMessages.ENTER_PASSWORD_AGAIN;
    }

    public ProfileMenuMessages checkPasswordAgain(String newPassword){
        if (ProfileMenuCommands.getMatcher(newPassword, ProfileMenuCommands.CANCEL) != null)
            return ProfileMenuMessages.CANCEL;

        if(!newPassword.equals(password))
            return ProfileMenuMessages.CHANGE_PASSWORD_ERROR5;

        currentUser.changePassword(newPassword);
        return ProfileMenuMessages.CHANGE_PASSWORD;
    }


    public ProfileMenuMessages changeEmail(Matcher matcher) {
        String email = matcher.group("email");
        if (email.isEmpty()) {
            return ProfileMenuMessages.CHANGE_EMAIL_ERROR3;
        }
        if (RegisterMenuController.checkEmailNotOK(email)) {
            return ProfileMenuMessages.CHANGE_EMAIL_ERROR1;
        }
        if (User.getUserByEmail(email) != null) {
            return ProfileMenuMessages.CHANGE_EMAIL_ERROR2;
        }
        currentUser.setEmail(matcher.group("email"));
        return ProfileMenuMessages.CHANGE_EMAIL;
    }

    public ProfileMenuMessages changeSlogan(Matcher matcher) {
        if (matcher.group("slogan").isEmpty()) {
            return ProfileMenuMessages.CHANGE_SLOGAN_ERROR1;
        }
        currentUser.setSlogan(matcher.group("slogan"));
        return ProfileMenuMessages.CHANGE_SLOGAN;
    }

    public ProfileMenuMessages removeSlogan(Matcher matcher) {
        currentUser.setSlogan(null);
        return ProfileMenuMessages.REMOVE_SLOGAN;
    }

    public int showScore() {
        return currentUser.getHighScore();
    }

    public int showRank() {
        return currentUser.getRank();
    }

    public String showSlogan() {
        if (currentUser.getSlogan() == null) {
            return "Empty Slogan!";
        }
        return currentUser.getSlogan();
    }

    public String showProfile() {
        String info = "Info:\n";
        info += "Username: " + currentUser.getUsername() + "\n";
        info += "Nickname: " + currentUser.getNickName() + "\n";
        info += "Email: " + currentUser.getEmail() + "\n";
        info += "Slogan: " + currentUser.getSlogan() + "\n";
        info += "highscore: " + currentUser.getHighScore() + "\n";
        info += "rank: " + currentUser.getRank() + "\n";
        return info;
    }
}
