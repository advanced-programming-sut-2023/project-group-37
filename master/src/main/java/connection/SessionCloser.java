package connection;

import java.time.LocalDateTime;

public class SessionCloser extends Thread {
    @Override
    public void run() {
        DatabaseController databaseController = DatabaseController.getInstance();
        databaseController.getCurrentSessions().removeIf(session -> session.getExpirationTime().compareTo
                (LocalDateTime.now()) <= 0);
    }
}
