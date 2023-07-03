package connection.packet.registration;

import connection.packet.Packet;
import connection.packet.PacketType;

public class LogoutPacket extends Packet {
    public LogoutPacket() {
        super.type = PacketType.LOGOUT_PACKET;
    }
}
