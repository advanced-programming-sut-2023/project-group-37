package connection;

import com.google.gson.Gson;
import connection.packet.Packet;
import connection.packet.PacketType;
import connection.packet.RegisterPacket;
import model.user.User;

import java.io.DataInputStream;
import java.io.IOException;

public class QueryReceiver extends Thread {
    private final User user;
    private final DataInputStream dataInputStream;

    public QueryReceiver(User user, DataInputStream dataInputStream) {
        this.user = user;
        this.dataInputStream = dataInputStream;
    }

    private void handleRegisterPacket(RegisterPacket packet) {
        boolean flag = true;
        for (User item : User.getUsers())
            if (item.getUsername().equals(packet.getUsername())) {
                flag = false;
                break;
            }
        if (flag) {
            new User(packet.getUsername(), packet.getHashedPassword(), packet.getEmail(), packet.getSlogan(),
                    packet.getNickname());
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (this.dataInputStream.available() != 0) {
                    String data = this.dataInputStream.readUTF();
                    Packet packet = new Gson().fromJson(data, Packet.class);
                    PacketType type = packet.getType();
                    switch (type) {
                        case REGISTER_PACKET:
                            RegisterPacket registerPacket = (RegisterPacket) packet;
                            handleRegisterPacket(registerPacket);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}