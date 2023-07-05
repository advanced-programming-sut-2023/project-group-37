package connection;

import connection.packet.registration.LogoutPacket;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SessionCloser extends Thread {
    @Override
    public void run() {
        DatabaseController databaseController = DatabaseController.getInstance();
        ArrayList<Session> currentSessions = databaseController.getCurrentSessions();

        for (int i = databaseController.getCurrentSessions().size() - 1; i >= 0; i--)
            if (currentSessions.get(i).getExpirationTime().compareTo(LocalDateTime.now()) <= 0) {
                try {
                    databaseController.getUserDataOutputStream(currentSessions.get(i).getUser().getUsername())
                            .writeUTF(new LogoutPacket().toJson());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                databaseController.getCurrentSessions().get(i).getUser().setOnline(false);
                databaseController.getCurrentSessions().remove(i);
            }

        databaseController.getCurrentSessions().removeIf(session -> session.getExpirationTime()
                .compareTo(LocalDateTime.now()) <= 0);
    }
}
