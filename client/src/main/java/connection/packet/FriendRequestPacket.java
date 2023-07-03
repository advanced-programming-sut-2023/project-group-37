package connection.packet;

import model.user.User;

public class FriendRequestPacket extends Packet {
    private final User sender;

    public FriendRequestPacket(User sender) {
        super.type = PacketType.FRIEND_REQUEST_PACKET;
        this.sender = sender;
    }

    public User getSender() {
        return this.sender;
    }
}
