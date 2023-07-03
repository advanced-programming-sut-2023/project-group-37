package controller.viewControllers;

import connection.Connection;
import connection.packet.RegisterPacket;
import model.user.Slogan;
import model.user.User;
import model.utils.PasswordHashing;
import view.enums.Message;

import java.io.IOException;
import java.security.SecureRandom;

public class RegisterMenuController {

    private static final RegisterMenuController registerMenuController;

    static {
        registerMenuController = new RegisterMenuController();
    }

    private RegisterMenuController() {

    }

    public static RegisterMenuController getInstance() {
        return registerMenuController;
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

    public void register(String username, String password, String nickName, String email,
                           String recoveryQuestion, String recoveryAnswer, String slogan) {

        try {
            Connection.getInstance().getDataOutputStream().writeUTF(new RegisterPacket(username, password, nickName,
                    slogan, email, recoveryQuestion, recoveryAnswer).toJson());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}