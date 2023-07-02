package connection.packet;

public class LoginPacket extends Packet {

    private final String username;
    private final String password;

    public LoginPacket(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public PacketType getType() {
        return PacketType.LOGIN_PACKET;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

}
