package connection;

import connection.packet.LoginPacket;
import connection.packet.PopUpPacket;
import connection.packet.RegisterPacket;
import model.user.User;
import view.enums.Message;
import view.enums.PopUp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RegistrationController {
    private final DataOutputStream dataOutputStream;
    public RegistrationController(Socket socket) throws IOException {
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public User handleLogin(LoginPacket loginPacket) throws IOException {
        System.out.println("RC : handle login");
        User user;
        if ((user = User.getUserByUsername(loginPacket.getUsername())) != null) {
            this.dataOutputStream.writeUTF(new PopUpPacket(Message.LOGIN_SUCCESSFUL, false).toJson());
            return user;
        }
        this.dataOutputStream.writeUTF(new PopUpPacket(Message.CANT_LOGIN, true).toJson());
        return null;
    }

    public void handleRegister(RegisterPacket registerPacket) throws IOException {
        if (User.getUserByUsername(registerPacket.getUsername()) == null) {
            this.dataOutputStream.writeUTF(new PopUpPacket(Message.USERNAME_ALREADY_EXISTS, true).toJson());
            return;
        }
        new User(registerPacket);
    }
}
