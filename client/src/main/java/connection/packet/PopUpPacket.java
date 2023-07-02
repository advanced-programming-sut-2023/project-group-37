package connection.packet;

import view.enums.Message;

public class PopUpPacket extends Packet{
    private final Message message;
    private final boolean isError;

    public PopUpPacket(Message message, boolean isError) {
        this.message = message;
        this.isError = isError;
    }

    @Override
    public PacketType getType() {
        return PacketType.POPUP_PACKET;
    }

    public Message getMessage() {
        return message;
    }

    public boolean isError() {
        return isError;
    }
}
