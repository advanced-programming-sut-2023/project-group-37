package connection;

import javafx.concurrent.Worker;
import model.user.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {
    private final Socket socket;
    private User user;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private final Database database;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.database = Database.getInstance();
    }

    @Override
    public synchronized void run() {
        while (true) {
            try {
                String data = this.dataInputStream.readUTF();
            }
            catch (Exception ignored) {

            }
        }
    }

    private void handleUser() throws IOException {

    }

    private void listenToAlive() {
        new AliveListener(this.user, this.dataInputStream).start();
    }
}
