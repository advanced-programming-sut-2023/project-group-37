package connection.packet.game;

import connection.packet.Packet;
import connection.packet.PacketType;
import model.user.User;

import java.util.ArrayList;

public class UsersPacket extends Packet {
    private ArrayList<User> allUsers;


    public UsersPacket(ArrayList<User> allUsers) {
        super.type = PacketType.USERS_PACKET;
        this.allUsers = allUsers;
    }

    public ArrayList<User> getAllUsers() {
        return allUsers;
    }
}
