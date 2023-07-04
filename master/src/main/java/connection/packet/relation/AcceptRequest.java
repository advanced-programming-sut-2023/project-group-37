package connection.packet.relation;

import connection.packet.Packet;
import connection.packet.PacketType;
import model.user.User;

public class AcceptRequest extends Packet {
    private final User sender;
    private final User receiver;


    public AcceptRequest(User sender, User receiver) {
        super.type = PacketType.ACCEPT_PACKET;
        this.sender = sender;
        this.receiver = receiver;
    }

    public User getReceiver() {
        return receiver;
    }

    public User getSender() {
        return sender;
    }
}
