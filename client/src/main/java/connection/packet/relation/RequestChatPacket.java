package connection.packet.relation;

import connection.packet.Packet;
import model.chat.Chat;

public class RequestChatPacket extends Packet {
    private Chat chat;


    public RequestChatPacket(Chat chat) {
        this.chat = chat;
    }

    public Chat getChat() {
        return chat;
    }
}
