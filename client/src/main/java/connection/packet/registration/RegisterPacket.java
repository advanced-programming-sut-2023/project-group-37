package connection.packet.registration;

import connection.packet.Packet;
import connection.packet.PacketType;
import model.user.User;

public class RegisterPacket extends Packet {
    private final String username;
    private final String password;
    private final String nickname;
    private final String slogan;
    private final String email;
    private final String recoveryQuestion;
    private final String recoveryAnswer;

    private RegisterPacket(User user) {
        super.type = PacketType.REGISTER_PACKET;
        this.username = user.getUsername();
        this.password = user.getHashedPassword();
        this.nickname = user.getNickName();
        this.slogan = user.getSlogan();
        this.email = user.getEmail();
        this.recoveryQuestion = user.getRecoveryQuestion();
        this.recoveryAnswer = user.getRecoveryAnswer();
    }

    public RegisterPacket(String username, String password, String nickname, String slogan, String email,
                          String recoveryQuestion, String recoveryAnswer) {
        super.type = PacketType.REGISTER_PACKET;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.slogan = slogan;
        this.email = email;
        this.recoveryQuestion = recoveryQuestion;
        this.recoveryAnswer = recoveryAnswer;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
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
}