package connection;

import connection.packet.PopUpPacket;
import model.chat.Lobby;
import view.enums.Message;

import java.io.IOException;
import java.util.ArrayList;

public class LobbyAliveListener extends Thread {
    private final DatabaseController databaseController;

    public LobbyAliveListener() {
        this.databaseController = DatabaseController.getInstance();
    }

    @Override
    public void run() {
        while (true) {
            ArrayList<Lobby> currentLobbies = new ArrayList<>(databaseController.getLobbies());
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (Lobby lobby : currentLobbies) {
                if (lobby.getUsers().size() <= 1) {
                    System.out.println("Lobby" + lobby.getId() + " removed!");
                    this.databaseController.removeLobby(lobby);
                    try {
                        this.databaseController.getUserDataOutputStream(lobby.getUsers().get(0).getUsername()).writeUTF(
                                new PopUpPacket(Message.REMOVE_LOBBY, true).toJson());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
