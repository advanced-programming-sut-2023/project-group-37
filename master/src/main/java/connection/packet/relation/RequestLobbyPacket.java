package connection.packet.relation;

import connection.packet.Packet;
import connection.packet.PacketType;

public class RequestLobbyPacket extends Packet {
    private final int capacity;
    public RequestLobbyPacket(int capacity) {
        this.capacity = capacity;
        super.type = PacketType.REQUEST_LOBBY_PACKET;
    }

    public int getCapacity() {
        return capacity;
    }
}
