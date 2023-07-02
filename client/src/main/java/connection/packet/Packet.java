package connection.packet;

import com.google.gson.Gson;

import java.io.Serializable;

public class Packet implements Serializable {
    private final PacketType type;

    public Packet(PacketType type) {
        this.type = type;
    }

    public PacketType getType() {
        return this.type;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
