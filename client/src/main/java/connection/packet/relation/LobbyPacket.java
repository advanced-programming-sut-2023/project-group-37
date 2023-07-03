package connection.packet.relation;

import connection.packet.Packet;
import connection.packet.PacketType;

public class LobbyPacket extends Packet {
    private final int capacity;
    public LobbyPacket(int capacity) {
        this.capacity = capacity;
        super.type = PacketType.LOBBY_PACKET;
    }

    public int getCapacity() {
        return capacity;
    }
}
