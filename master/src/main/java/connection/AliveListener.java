package connection;

import model.user.User;

import java.io.DataInputStream;

public class AliveListener extends Thread {
    private final User user;
    private final DataInputStream dataInputStream;

    public AliveListener(User user, DataInputStream dataInputStream) {
        this.user = user;
        this.dataInputStream = dataInputStream;
    }
}
