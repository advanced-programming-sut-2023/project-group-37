package connection;

import model.chat.Lobby;

public class RelationHandler {
    private final static RelationHandler instance = new RelationHandler();
    private Lobby currentLobby;

    public static RelationHandler getInstance() {
        return instance;
    }

    public Lobby getCurrentLobby() {
        return currentLobby;
    }

    public void setCurrentLobby(Lobby currentLobby) {
        this.currentLobby = currentLobby;
    }
}
