package connection;

import connection.packet.TilesPacket;
import model.user.User;

public class GamingController {
    private final User user;

    public GamingController(User user) {
        this.user = user;
    }

    public void handleTiles(TilesPacket tilesPacket) {

    }
}
