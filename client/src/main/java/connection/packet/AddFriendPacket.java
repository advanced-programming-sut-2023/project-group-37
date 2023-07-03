package connection.packet;

import model.user.User;

public class AddFriendPacket extends Packet {
    private final User sender;

    public AddFriendPacket(User sender) {
        super.type = PacketType.ADD_FRIEND_PACKET;
        this.sender = sender;
    }

    public User getSender() {
        return this.sender;
    }
}
