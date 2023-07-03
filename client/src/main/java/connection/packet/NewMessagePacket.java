package connection.packet;

import model.chat.Chat;
import model.chat.ChatMessage;

public class NewMessagePacket extends Packet {
    private final Chat chat;
    private final ChatMessage message;

    public NewMessagePacket(Chat chat, ChatMessage message) {
        super.type = PacketType.NEW_MESSAGE_PACKET;
        this.chat = chat;
        this.message = message;
    }

    public Chat getChat() {
        return this.chat;
    }

    public ChatMessage getMessage() {
        return message;
    }
}