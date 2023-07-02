package connection.packet;

import com.google.gson.Gson;

import java.io.Serializable;

public abstract class Packet implements Serializable {

    public abstract PacketType getType();

    public String toJson() {
        return new Gson().toJson(this);
    }
}
