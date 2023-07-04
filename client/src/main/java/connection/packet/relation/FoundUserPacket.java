package connection.packet.relation;

import connection.packet.Packet;
import connection.packet.PacketType;
import model.user.User;

public class FoundUserPacket extends Packet {
    private final User foundUser;

    public FoundUserPacket(User foundUser) {
        super.type = PacketType.FOUND_USER_PACKET;
        this.foundUser = foundUser;
    }

    public User getFoundUser() {
        return foundUser;
    }
}
