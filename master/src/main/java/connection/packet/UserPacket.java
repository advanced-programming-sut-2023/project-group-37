package connection.packet;

import model.user.User;

public class UserPacket extends Packet {
    private final User user;

    public UserPacket(User user) {
        super(PacketType.USER_PACKET);
        this.user = user;
    }

    @Override
    public PacketType getType() {
        return PacketType.USER_PACKET;
    }

    public User getUser() {
        return user;
    }
}
