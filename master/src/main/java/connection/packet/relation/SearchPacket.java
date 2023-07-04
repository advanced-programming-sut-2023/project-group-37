package connection.packet.relation;

import connection.packet.Packet;

public class SearchPacket extends Packet {
    private final String username;

    public SearchPacket(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
