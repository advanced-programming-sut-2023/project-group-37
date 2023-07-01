package connection;

import com.google.gson.Gson;
import view.enums.PopUp;

import java.io.DataInputStream;

public class NotificationReceiver extends Thread{
    private final DataInputStream dataInputStream;

    public NotificationReceiver(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    @Override
    public void run() {
        Gson gson = new Gson();
        while (true) {
            PopUp popUp;
            try {
                popUp = gson.fromJson(this.dataInputStream.readUTF(), PopUp.class);
                popUp.show();
            }
            catch (Exception ignored) {

            }
        }
    }
}
