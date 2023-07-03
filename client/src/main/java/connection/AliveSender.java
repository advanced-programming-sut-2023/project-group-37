package connection;

import connection.packet.AlivePacket;

import java.io.DataOutputStream;
import java.io.IOException;

public class AliveSender extends Thread {
    private final DataOutputStream dataOutputStream;

    public AliveSender(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.dataOutputStream.writeUTF(new AlivePacket().toJson());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                sleep(15000);
            } catch (InterruptedException ex) {
                this.interrupt();
            }
        }
    }
}
