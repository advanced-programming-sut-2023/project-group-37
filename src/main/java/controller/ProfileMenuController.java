package controller;

import model.user.User;
import model.utils.PasswordHashing;
import view.enums.Command;
import view.enums.Message;


public class ProfileMenuController {
    private static final ProfileMenuController profileMenuController;
    private static User currentUser;
    private String password;

    static {
        profileMenuController = new ProfileMenuController();
    }

    private ProfileMenuController() {

    }

    public static ProfileMenuController getInstance() {
        return profileMenuController;
    }

    public static void setCurrentUser(User currentUser) {
        ProfileMenuController.currentUser = currentUser;
    }

    public String deleteQuotation(String string) {
        return MultiMenuFunctions.deleteQuotations(string);
    }

    public Message changeUsername(String username) {
        if (username == null) {
            return Message.CHANGE_USERNAME_ERROR1;
        }
        username = deleteQuotation(username);
        if (RegisterMenuController.checkUsernameNotOK(username)) {
            return Message.CHANGE_USERNAME_ERROR2;
        }
        if (username.equals(currentUser.getUsername())) {
            return Message.CHANGE_USERNAME_ERROR3;
        }
        currentUser.setUsername(username);
        return Message.CHANGE_USERNAME;
    }

    public Message changeNickName(String nickname) {
        if (nickname == null) {
            return Message.CHANGE_NICKNAME_ERROR1;
        }
        if (nickname.equals(currentUser.getNickName())) {
            return Message.CHANGE_NICKNAME_ERROR2;
        }
        currentUser.setNickName(nickname);
        return Message.CHANGE_NICKNAME;
    }

    public String changePassword(String oldPass, String newPass) {
        MultiMenuFunctions.deleteQuotations(oldPass);
        MultiMenuFunctions.deleteQuotations(newPass);
        if (oldPass == null || newPass == null) {
            return Message.CHANGE_PASSWORD_ERROR1.toString();
        }
        if (currentUser.isWrongPassword(oldPass)) {
            return Message.CHANGE_PASSWORD_ERROR2.toString();
        }
        if (RegisterMenuController.checkPasswordNotOK(newPass)) {
            return Message.CHANGE_PASSWORD_ERROR4.toString();
        }
        password = newPass;
        return Message.ENTER_PASSWORD_AGAIN.toString();
    }

    public String checkPasswordAgain(String newPassword) {
        if (Command.CANCEL.getMatcher(newPassword) != null)
            return Message.CANCEL.toString();

        if (!newPassword.equals(password))
            return Message.CHANGE_PASSWORD_ERROR5.toString();
        String encoded = PasswordHashing.encode(newPassword);
        currentUser.setHashedPassword(encoded);
        return Message.CHANGE_PASSWORD.toString();
    }


    public Message changeEmail(String newEmail) {
        if (newEmail.isEmpty()) {
            return Message.CHANGE_EMAIL_ERROR3;
        }
        if (RegisterMenuController.checkEmailNotOK(newEmail)) {
            return Message.CHANGE_EMAIL_ERROR1;
        }
        if (User.getUserByEmail(newEmail) != null) {
            return Message.CHANGE_EMAIL_ERROR2;
        }
        currentUser.setEmail(newEmail);
        return Message.CHANGE_EMAIL;
    }

    public Message changeSlogan(String newSlogan) {
        if (newSlogan.isEmpty()) {
            return Message.CHANGE_SLOGAN_ERROR1;
        }
        currentUser.setSlogan(newSlogan);
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
