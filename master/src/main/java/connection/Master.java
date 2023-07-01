package connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Master {
    public Master(int port) {
        System.out.println("Starting StrongHold service...");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                new Connection(socket).start();
            }
        } catch (IOException ignored) {
            System.out.println(ignored.getMessage());
        }
    }
}
