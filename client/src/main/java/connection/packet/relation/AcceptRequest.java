package connection.packet.relation;

import connection.packet.Packet;
import model.user.User;

public class AcceptRequest extends Packet {
    private User sender;
    private User receiver;


    public AcceptRequest(User sender, User receiver) {
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
