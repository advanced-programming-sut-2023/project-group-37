package connection;

import com.google.gson.Gson;
import packet.LoginPacket;
import packet.RegisterPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {
    private final Gson gson;
    private static Connection connection;
    private final DataOutputStream dataOutputStream;

    public Connection(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.gson = new Gson();

        this.dataOutputStream.writeUTF("");
        System.out.println(dataInputStream.readUTF());

        new NotificationReceiver(dataInputStream).start();
        connection = this;
    }

    public static Connection getInstance() {
        return connection;
    }

    public void register(String username, String password, String nickName, String email,
                          String recoveryQuestion, String recoveryAnswer, String slogan) throws IOException {
        this.dataOutputStream.writeUTF(gson.toJson(new RegisterPacket(username, password, nickName, email,
                recoveryQuestion, recoveryAnswer, slogan)));
    }

    public void login(String username, String password) throws IOException {
        this.dataOutputStream.writeUTF(gson.toJson(new LoginPacket(username, password)));
    }
}
