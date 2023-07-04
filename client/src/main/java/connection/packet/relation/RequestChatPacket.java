package connection.packet.relation;

import connection.packet.Packet;
import connection.packet.PacketType;
import model.chat.Chat;

public class RequestChatPacket extends Packet {
    private final Chat chat;

    public RequestChatPacket(Chat chat) {
        super.type = PacketType.REQ_UPDATE_CHAT;
        this.chat = chat;
    }

    public Chat getChat() {
        return chat;
    }
}
