package connection;

import java.io.DataInputStream;
import java.io.IOException;

public class ClientInputHandler extends Thread {
    private final DataInputStream dataInputStream;

    public ClientInputHandler(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    @Override
    public synchronized void run() {
        while (true) {
            try {
                System.out.println(dataInputStream.readUTF());
            } catch (IOException ignored) {
            }
        }
    }
}
