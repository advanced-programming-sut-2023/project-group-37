package packet;

import java.io.Serializable;

public class RegisterPacket implements Serializable {
    private final String username;
    private final String hashedPassword;
    private final String nickname;
    private final String slogan;
    private final String email;
    private final String recoveryQuestion;
    private final String recoveryAnswer;
    private final int highScore;
    private final int rank;
    private final int avatarNum;

    public RegisterPacket(String username, String hashedPassword, String nickname, String slogan, String email,
                          String recoveryQuestion, String recoveryAnswer, int highScore, int rank, int avatarNum) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.nickname = nickname;
        this.slogan = slogan;
        this.email = email;
        this.recoveryQuestion = recoveryQuestion;
        this.recoveryAnswer = recoveryAnswer;
        this.highScore = highScore;
        this.rank = rank;
        this.avatarNum = avatarNum;
    }
}
