package connection.packet.relation;

import connection.packet.Packet;
import connection.packet.PacketType;

public class SearchPacket extends Packet {
    private final String username;

    public SearchPacket(String username) {
        super.type = PacketType.SEARCH_PACKET;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
