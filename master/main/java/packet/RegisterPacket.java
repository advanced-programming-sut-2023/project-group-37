package packet;

import java.io.Serializable;

public record RegisterPacket(String username, String hashedPassword, String nickname, String slogan, String email,
                             String recoveryQuestion, String recoveryAnswer) implements Serializable {
}
