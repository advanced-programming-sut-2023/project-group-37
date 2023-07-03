package connection.packet;

import model.chat.Chat;

public class DeleteMessagePacket extends Packet {
    private final Chat chat;
    private final int messageId;

    public DeleteMessagePacket(Chat chat, int messageId) {
        super.type = PacketType.DELETE_MESSAGE_PACKET;
        this.chat = chat;
        this.messageId = messageId;
    }

    public Chat getChat() {
        return this.chat;
    }

    public int getMessageId() {
        return this.messageId;
    }
}