package connection;

import com.google.gson.Gson;
import connection.packet.*;
import controller.AppController;
import controller.MultiMenuFunctions;
import model.user.User;
import view.enums.Result;

import java.io.DataInputStream;
import java.io.IOException;

public class NotificationReceiver extends Thread {
    private final AppController appController;
    private final DataInputStream dataInputStream;
    private boolean stayLoggedIn;

    public NotificationReceiver(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
        this.stayLoggedIn = false;
        this.appController = AppController.getInstance();
    }

    @Override
    public void run() {
        Gson gson = new Gson();
        String data;

        while (true) {
            try {
                data = this.dataInputStream.readUTF();
                System.out.println("Response got");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Packet packet = gson.fromJson(data, Packet.class);
            PacketType type = packet.getType();
            switch (type) {
                case POPUP_PACKET -> this.appController.handleAlert(gson.fromJson(data, PopUpPacket.class));
                case TILES_PACKET -> this.handleTiles(gson.fromJson(data, TilesPacket.class));
                case USER_PACKET -> this.login(gson.fromJson(data, UserPacket.class));
            }
        }
    }

    private void login(UserPacket userPacket) {
        MultiMenuFunctions.setAllCurrentUsers(userPacket.getUser());
        if (this.stayLoggedIn)
            User.setStayLoggedIn(userPacket.getUser());
    }

    private void handleTiles(TilesPacket tilesPacket) {
    }

    public void setStayLoggedIn(boolean stayLoggedIn) {
        this.stayLoggedIn = stayLoggedIn;
    }
}
