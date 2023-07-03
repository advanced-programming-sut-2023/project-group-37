package connection.packet.registration;

import connection.packet.Packet;
import connection.packet.PacketType;

public class AlivePacket extends Packet {
    public AlivePacket() {
        super.type = PacketType.ALIVE_PACKET;
    }
}
