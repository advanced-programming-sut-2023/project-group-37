package connection.packet.game;

import connection.packet.Packet;
import connection.packet.PacketType;

public class StartRequestPacket extends Packet {
    private final int lobbyId;
    public StartRequestPacket(int lobbyId) {
        super.type = PacketType.START_REQUEST_PACKET;
        this.lobbyId = lobbyId;
    }

    public int getLobbyId() {
        return lobbyId;
    }

}
