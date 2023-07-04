package connection.packet;

import com.google.gson.Gson;

import java.io.Serializable;

public class Packet implements Serializable {
    protected PacketType type;

    public Packet() {

    }

    public PacketType getType() {
        return this.type;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}