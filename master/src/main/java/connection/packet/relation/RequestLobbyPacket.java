package connection.packet.relation;

import connection.packet.Packet;
import connection.packet.PacketType;

public class RequestLobbyPacket extends Packet {
    private final int capacity;
    private final boolean isPublic;
    public RequestLobbyPacket(int capacity, boolean isPublic) {
        super.type = PacketType.REQUEST_LOBBY_PACKET;
        this.capacity = capacity;
        this.isPublic = isPublic;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
