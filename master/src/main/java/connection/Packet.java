package connection;

import com.google.gson.Gson;

import java.io.Serializable;

public abstract class Packet implements Serializable {
    public String toJson() {
        return new Gson().toJson(this);
    }
}
