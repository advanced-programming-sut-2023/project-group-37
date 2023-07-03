package connection.packet;

import view.enums.Message;

public class PopUpPacket extends Packet{
    private final Message message;
    private final boolean isError;

    public PopUpPacket(Message message, boolean isError) {
        super.type = PacketType.POPUP_PACKET;
        this.message = message;
        this.isError = isError;
    }

    public Message getMessage() {
        return message;
    }

    public boolean isError() {
        return isError;
    }
}
