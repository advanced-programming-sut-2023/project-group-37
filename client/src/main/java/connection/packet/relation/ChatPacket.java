package connection.packet.relation;

import connection.packet.Packet;
import connection.packet.PacketType;
import model.chat.Chat;

public class ChatPacket extends Packet {
    private final Chat chat;

    public ChatPacket(Chat chat) {
        super.type = PacketType.CHAT_PACKET;
        this.chat = chat;
    }

    public Chat getChat() {
        return this.chat;
    }
}