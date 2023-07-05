package connection.packet.game;

import connection.packet.Packet;
import connection.packet.PacketType;
import model.chat.Lobby;

public class JoinedLobbyPacket extends Packet {
    private final Lobby lobby;

    public JoinedLobbyPacket(Lobby lobby) {
        super.type = PacketType.JOINED_LOBBY_PACKET;
        this.lobby = lobby;
    }

    public Lobby getLobby() {
        return lobby;
    }
}
