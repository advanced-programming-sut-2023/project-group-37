package connection;

import java.io.DataInputStream;

public class PacketReceiveHandler extends Thread {
    private final DataInputStream dataInputStream;

    public PacketReceiveHandler(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    @Override
    public void run() {
        // TODO: fill here...
    }
}