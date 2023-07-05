package connection.packet.game;

import connection.packet.Packet;
import connection.packet.PacketType;
import model.user.User;

public class JoinRequestPacket extends Packet {
    private final String senderUsername;
    private final int lobbyId;

    public JoinRequestPacket(User sender, int lobbyId) {
        super.type = PacketType.JOIN_REQUEST_PACKET;
        this.senderUsername = sender.getUsername();
        this.lobbyId = lobbyId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public int getLobbyId() {
        return lobbyId;
    }
}
