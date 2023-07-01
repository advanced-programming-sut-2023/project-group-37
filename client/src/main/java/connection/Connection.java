package connection;

import com.google.gson.Gson;
import packet.RegisterPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;

public class Connection {
    private final DataOutputStream dataOutputStream;
    private final DataInputStream dataInputStream;
    private final Gson gson;
    public Connection(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.gson = new Gson();

        this.dataOutputStream.writeUTF("");
        System.out.println(this.dataInputStream.readUTF());

        new NotificationReceiver(this.dataInputStream).start();
    }

    private void register() {

    }

    private void login() {

    }
}
