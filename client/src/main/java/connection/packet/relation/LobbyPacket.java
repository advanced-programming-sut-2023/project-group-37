package connection.packet.relation;

import connection.packet.Packet;
import connection.packet.PacketType;
import model.chat.Lobby;

public class LobbyPacket extends Packet {
    private final Lobby lobby;
    public LobbyPacket(Lobby lobby) {
        super.type = PacketType.LOBBY_PACKET;
        this.lobby = lobby;
    }
    public Lobby getLobby() {
        return lobby;
    }
}
