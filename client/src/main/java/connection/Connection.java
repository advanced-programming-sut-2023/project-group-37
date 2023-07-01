package connection;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {
    private final Gson gson;
    private static Connection connection;

    public Connection(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.gson = new Gson();

        new NotificationReceiver(dataInputStream).start();
        connection = this;
    }

    public static Connection getInstance() {
        return connection;
    }
}
