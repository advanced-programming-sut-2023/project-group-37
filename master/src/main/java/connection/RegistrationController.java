package connection;

import connection.packet.registration.LoginPacket;
import connection.packet.PopUpPacket;
import connection.packet.registration.RegisterPacket;
import model.user.User;
import view.enums.Message;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RegistrationController {

    private final DataOutputStream dataOutputStream;

    public RegistrationController(Socket socket) throws IOException {
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public User handleLogin(LoginPacket loginPacket) throws IOException {
        User user;
        if ((user = User.getUserByUsername(loginPacket.getUsername())) != null)
            if (DatabaseController.getInstance().getSessionByUser(user) == null &&
                    (!user.isWrongPassword(loginPacket.getPassword()) || !user.isWrongHashedPassword
                            (loginPacket.getPassword())))
                return user;
        return null;
    }

    public void handleRegister(RegisterPacket registerPacket) throws IOException {
        if (User.getUserByUsername(registerPacket.getUsername()) != null) {
            this.dataOutputStream.writeUTF(new PopUpPacket(Message.USERNAME_ALREADY_EXISTS, true).toJson());
            return;
        }
        new User(registerPacket);
        this.dataOutputStream.writeUTF(new PopUpPacket(Message.REGISTER_SUCCESSFUL, false).toJson());
    }
}