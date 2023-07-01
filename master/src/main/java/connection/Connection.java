package connection;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.GameController;
import controller.viewControllers.MainMenuController;
import model.game.Map;
import model.game.Tile;
import model.user.User;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Connection extends Thread {
    private User user;
    private final Socket socket;
    private final Database database;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.database = Database.getInstance();
    }

    @Override
    public synchronized void run() {
        DataInputStream dataInputStream;
        try {
            dataInputStream = new DataInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();
        String data;
        ArrayList<User> users;
        ArrayList<Tile> modifiedTiles;
        while (true) {
            try {
                data = dataInputStream.readUTF();
                try {
                    modifiedTiles = gson.fromJson(data, new TypeToken<List<Tile>>() {
                    }.getType());
                    Map map = GameController.getInstance().getCurrentGame().getMap();
                    map.resetSomeTiles(modifiedTiles);
                    this.database.updateEveryOneTilesExcept(modifiedTiles, this.user);
                }
                catch (Exception ignored) {
                    try {
                        users = gson.fromJson(data, new TypeToken<List<User>>() {
                        }.getType());
                        User.reset(users);
                    }
                    catch (Exception ex) {
                        User user = User.getUserByUsername(data);
                        if (user != null) {

                            this.user = user;
                            database.addConnectedUser(user, socket);
                            AliveListener aliveListener = new AliveListener(user, dataInputStream);
                        }
                    }
                }
            }
            catch (Exception ignored) {

            }
        }
    }
}
