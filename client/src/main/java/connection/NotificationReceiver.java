package connection;

import com.google.gson.Gson;
import connection.packet.*;
import controller.AppController;
import controller.MultiMenuFunctions;
import javafx.scene.control.Alert;
import view.enums.Result;

import java.io.DataInputStream;
import java.io.IOException;

public class NotificationReceiver extends Thread {
    private final DataInputStream dataInputStream;

    public NotificationReceiver(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
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
                case POPUP_PACKET -> this.handlePopUp((PopUpPacket) packet);
                case TILES_PACKET -> this.handleTiles((TilesPacket) packet);
                case USER_PACKET -> this.login((UserPacket) packet);
            }
        }
    }

    private void login(UserPacket userPacket) {
        MultiMenuFunctions.setAllCurrentUsers(userPacket.getUser());
        try {
            AppController.getInstance().runMenu(Result.ENTER_MAIN_MENU);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handlePopUp(PopUpPacket popUpPacket) {
        if (popUpPacket.isError())
            new Alert(Alert.AlertType.ERROR, popUpPacket.getMessage().toString());
        else new Alert(Alert.AlertType.INFORMATION, popUpPacket.getMessage().toString());
    }

    private void handleTiles(TilesPacket tilesPacket) {
    }
}
