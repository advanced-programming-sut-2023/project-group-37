package connection;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.GameController;
import model.game.Map;
import model.game.Tile;
import model.user.User;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotificationReceiver extends Thread {
    private final DataInputStream dataInputStream;

    public NotificationReceiver(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    @Override
    public void run() {
        Gson gson = new Gson();
        String data;
        ArrayList<Tile> modifiedTiles;
        ArrayList<User> users;
        while (true) {
            try {
                data = this.dataInputStream.readUTF();
                System.out.println("Response got");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                try {
                    modifiedTiles = gson.fromJson(data, new TypeToken<List<Tile>>() {
                    }.getType());
                    Map map = GameController.getInstance().getCurrentGame().getMap();
                    map.resetSomeTiles(modifiedTiles);
                } catch (Exception ignored) {
                    users = gson.fromJson(data, new TypeToken<List<User>>() {
                    }.getType());
                    User.reset(users);
                    System.out.println("USERA");
                }
            } catch (Exception ignored) {

            }
        }
    }
}
