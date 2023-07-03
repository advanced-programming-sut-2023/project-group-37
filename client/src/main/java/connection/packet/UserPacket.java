package connection.packet;

import model.user.User;

public class UserPacket extends Packet {
    private final User user;

    public UserPacket(User user) {
        super.type = PacketType.USER_PACKET;
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}