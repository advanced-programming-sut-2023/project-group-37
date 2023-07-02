package controller;

import connection.packet.LoginPacket;
import connection.packet.PopUpPacket;
import connection.packet.RegisterPacket;
import model.user.User;
import view.enums.PopUp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class AuthorizationController {
    private final DataOutputStream dataOutputStream;
    public AuthorizationController(Socket socket) throws IOException {
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public User handleLogin(LoginPacket loginPacket) throws IOException {
        User user;
        if ((user = User.getUserByUsername(loginPacket.getUsername())) != null) {
            this.dataOutputStream.writeUTF(new PopUpPacket(PopUp.LOGIN_SUCCESSFUL).toJson());
            return user;
        }
        this.dataOutputStream.writeUTF(new PopUpPacket(PopUp.CANT_LOGIN).toJson());
        return null;
    }

    public void handleRegister(RegisterPacket registerPacket) throws IOException {
        if (User.getUserByUsername(registerPacket.getUsername()) == null) {
            this.dataOutputStream.writeUTF(new PopUpPacket(PopUp.CANT_LOGIN).toJson());
            return;
        }
        new User(registerPacket);
    }
}
