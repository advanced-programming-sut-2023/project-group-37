package connection.packet.game;

import connection.packet.Packet;
import connection.packet.PacketType;
import model.chat.Lobby;

public class RefreshLobbyPacket extends Packet {
    private final Lobby lobby;

    public RefreshLobbyPacket(Lobby lobby) {
        super.type = PacketType.REFRESH_LOBBY_PACKET;
        this.lobby = lobby;
    }

    public Lobby getLobby() {
        return this.lobby;
    }
}