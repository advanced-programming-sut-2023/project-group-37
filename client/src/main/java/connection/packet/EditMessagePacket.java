package connection.packet;

import model.chat.Chat;

public class EditMessagePacket extends Packet {
    private final Chat chat;
    private final int messageId;
    private final String newContent;

    public EditMessagePacket(Chat chat, int messageId, String newContent) {
        super.type = PacketType.EDIT_MESSAGE_PACKET;
        this.chat = chat;
        this.messageId = messageId;
        this.newContent = newContent;
    }

    public Chat getChat() {
        return this.chat;
    }

    public int getMessageId() {
        return this.messageId;
    }

    public String getNewContent() {
        return this.newContent;
    }
}