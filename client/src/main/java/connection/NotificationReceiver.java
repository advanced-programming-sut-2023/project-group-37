package connection;

import java.io.DataInputStream;
import java.io.IOException;

public class NotificationReceiver extends Thread{
    private final DataInputStream dataInputStream;

    public NotificationReceiver(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println(this.dataInputStream.readUTF());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
