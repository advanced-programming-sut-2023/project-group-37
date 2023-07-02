package connection.packet;

import javafx.scene.image.Image;
import model.user.User;

public class RegisterPacket extends Packet {
    private final String username;
    private final String hashedPassword;
    private final String nickname;
    private final String slogan;
    private final String email;
    private final String recoveryQuestion;
    private final String recoveryAnswer;
    private final int highScore;
    private final Image avatar;

    private RegisterPacket(User user) {
        this.username = user.getUsername();
        this.hashedPassword = user.getHashedPassword();
        this.nickname = user.getNickName();
        this.slogan = user.getSlogan();
        this.email = user.getEmail();
        this.recoveryQuestion = user.getRecoveryQuestion();
        this.recoveryAnswer = user.getRecoveryAnswer();
        this.highScore = user.getHighScore();
        this.avatar = user.getAvatar();
    }

    public RegisterPacket(String username, String hashedPassword, String nickname, String slogan, String email,
                          String recoveryQuestion, String recoveryAnswer, Image avatar) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.nickname = nickname;
        this.slogan = slogan;
        this.email = email;
        this.recoveryQuestion = recoveryQuestion;
        this.recoveryAnswer = recoveryAnswer;
        this.highScore = 0;
        this.avatar = avatar;
    }

    public String getUsername() {
        return this.username;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
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

    public String getRecoveryQuestion() {
        return this.recoveryQuestion;
    }

    public String getRecoveryAnswer() {
        return this.recoveryAnswer;
    }

    public int getHighScore() {
        return this.highScore;
    }

    public Image getAvatar() {
        return this.avatar;
    }
}