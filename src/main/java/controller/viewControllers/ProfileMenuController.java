package controller.viewControllers;

import controller.CaptchaController;
import controller.MultiMenuFunctions;
import javafx.scene.image.Image;
import model.user.User;
import view.enums.Command;
import view.enums.Message;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class ProfileMenuController {
    private static final ProfileMenuController profileMenuController;
    private static User currentUser;
    private String password;
    private static ArrayList<File> allAvatarImages;
    private static final Random random = new Random();

    static {
        profileMenuController = new ProfileMenuController();
    }

    static {
        allAvatarImages = new ArrayList<>();

        File file;
        try {
            file = new File(Objects.requireNonNull(CaptchaController.class.getResource("/Image/Avatars")).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        allAvatarImages.addAll(MultiMenuFunctions.getAllImageFilesFromFolder(file));
    }

    private ProfileMenuController() {

    }

    public static ArrayList<File> getAllAvatarImages() {
        return allAvatarImages;
    }

    public static String getRandomAvatarURL() {
        int randomNumber = random.nextInt(allAvatarImages.size());
        return allAvatarImages.get(randomNumber).getAbsolutePath();
    }

    public static User getCurrentUser() {
        return currentUser;
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
        if (MultiMenuFunctions.checkUsernameNotOK(username)) {
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
        if (MultiMenuFunctions.checkPasswordNotOK(newPass)) {
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

        currentUser.setPassword(newPassword);
        return Message.CHANGE_PASSWORD.toString();
    }


    public Message changeEmail(String newEmail) {
        if (newEmail.isEmpty()) {
            return Message.CHANGE_EMAIL_ERROR3;
        }
        if (MultiMenuFunctions.checkEmailNotOK(newEmail)) {
            return Message.CHANGE_EMAIL_ERROR1;
        }
        if (User.getUserByEmail(newEmail) != null) {
            return Message.EMAIL_ALREADY_EXISTS;
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
