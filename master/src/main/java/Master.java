import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Master {
    public Master(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server starting...");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Someone connected!");
            new Connection(clientSocket).start();
        }
    }

    public static void main(String[] args) throws IOException {
        new Master(8080);
    }
}