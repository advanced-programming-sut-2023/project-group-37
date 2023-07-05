package connection.packet.game;

import connection.packet.Packet;
import connection.packet.PacketType;

public class StartGamePacket extends Packet {

    public StartGamePacket() {
        super.type = PacketType.START_PACKET;
    }

}
