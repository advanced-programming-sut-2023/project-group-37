package connection.packet.relation;

import connection.packet.Packet;
import model.user.User;

public class FoundUserPacket extends Packet {
    private final User foundUser;


    public FoundUserPacket(User foundUser) {
        this.foundUser = foundUser;
    }

    public User getFoundUser() {
        return foundUser;
    }
}
