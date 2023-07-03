package connection;

import com.google.gson.Gson;
import connection.packet.*;
import model.ChatMessage;
import model.user.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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

    private void handleLoginPacket(LoginPacket packet) {
        boolean userExists = false;
        for (User item : User.getUsers()) {
            if (item.getUsername().equals(packet.getUsername())) {
                userExists = true;
                if (item.isWrongPassword(packet.getPassword()))
                    // TODO: should change later...
                    System.out.println("wrong password");
                else
                    User.setCurrentUser(item);
                break;
            }
        }
        if (!userExists)
            // TODO: should change later...
            System.out.println("user not exist");
    }

    private void handleFriendRequestPacket(FriendRequestPacket packet) throws IOException {
        DatabaseController.getInstance().getUserDataOutputStream(packet.getSender()).writeUTF(packet.toJson());
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
                        case LOGIN_PACKET:
                            LoginPacket loginPacket = (LoginPacket) packet;
                            handleLoginPacket(loginPacket);
                        case FRIEND_REQUEST_PACKET:
                            FriendRequestPacket friendRequestPacket = (FriendRequestPacket) packet;
                            handleFriendRequestPacket(friendRequestPacket);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}