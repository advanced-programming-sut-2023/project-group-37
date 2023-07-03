package connection.packet;

public class LoginPacket extends Packet {
    private final String username;
    private final String password;

    public LoginPacket(String username, String password) {
        super.type = PacketType.LOGIN_PACKET;
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

}
