package connection.packet;

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