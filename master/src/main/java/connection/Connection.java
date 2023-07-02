package connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection {
    public Connection(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                new QueryReceiver(socket).start();
            }
        } catch (IOException ignored) {

        }
    }
}
