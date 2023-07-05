package connection.packet.game;

import connection.packet.Packet;
import connection.packet.PacketType;
import model.chat.Lobby;

public class LeaveRequestPacket extends Packet {
    private final int lobbyId;

    public LeaveRequestPacket(Lobby lobby) {
        super.type = PacketType.LEAVE_REQUEST_PACKET;
        this.lobbyId = lobby.getId();
    }

    public int getLobbyId() {
        return this.lobbyId;
    }
}
