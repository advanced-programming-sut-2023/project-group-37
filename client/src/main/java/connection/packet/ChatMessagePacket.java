package connection.packet;

import model.ChatMessage;

public class ChatMessagePacket extends Packet {
    private final ChatMessage message;

    public ChatMessagePacket(ChatMessage message) {
        super.type = PacketType.CHAT_MESSAGE_PACKET;
        this.message = message;
    }

    public ChatMessage getMessage() {
        return this.message;
    }
}
