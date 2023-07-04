package connection;

import controller.AppController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {
    private static Connection connection;
    private final DataOutputStream dataOutputStream;
    private final NotificationReceiver notificationReceiver;

    public Connection(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());

        this.notificationReceiver = new NotificationReceiver(dataInputStream);
        this.notificationReceiver.start();
        new AliveSender(this.dataOutputStream).start();

        connection = this;
        if (AppController.stayLoggedInPacket != null)
            this.dataOutputStream.writeUTF(AppController.stayLoggedInPacket.toJson());

        this.setStayLoggedIn(AppController.getInstance().isStayLoggedIn());
    }

    public static Connection getInstance() {
        return connection;
    }

    public DataOutputStream getDataOutputStream() {
        return this.dataOutputStream;
    }

    public void setStayLoggedIn(boolean stayLoggedIn) {
        this.notificationReceiver.setStayLoggedIn(stayLoggedIn);
    }
}
