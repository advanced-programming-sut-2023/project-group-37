package connection.packet.relation;

import connection.packet.Packet;
import connection.packet.PacketType;
import model.user.User;

public class FriendRequestPacket extends Packet {
    private final User sender;
    private final User receiver;
    public FriendRequestPacket(User sender, User receiver) {
        super.type = PacketType.FRIEND_REQUEST_PACKET;
        this.sender = sender;
        this.receiver = receiver;
    }
    public User getSender() {
        return this.sender;
    }
    public User getReceiver() {
        return this.receiver;
    }
}