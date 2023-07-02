package connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Master {
    public Master(int port) {
        System.out.println("Starting Stronghold service...");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                new QueryReceiver(socket).start();
            }
        } catch (IOException e) {
            // todo: try to reconnect?
            System.out.println(e.getMessage());
        }
    }
}
