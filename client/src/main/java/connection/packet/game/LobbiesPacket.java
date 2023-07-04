package connection.packet.game;

import connection.packet.Packet;
import connection.packet.PacketType;
import model.chat.Lobby;

import java.util.ArrayList;

public class LobbiesPacket extends Packet {
    private ArrayList<Lobby> lobbies;


    public LobbiesPacket(ArrayList<Lobby> lobbies) {
        super.type = PacketType.LOBBIES_PACKET;
        this.lobbies = lobbies;
    }

    public ArrayList<Lobby> getLobbies() {
        return lobbies;
    }
}
