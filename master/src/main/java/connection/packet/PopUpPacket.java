package connection.packet;

import view.enums.PopUp;

public class PopUpPacket extends Packet{
    private final PopUp popUp;

    public PopUpPacket(PopUp popUp) {
        this.popUp = popUp;
    }

    public PopUp getPopUp() {
        return popUp;
    }

    @Override
    public PacketType getType() {
        return PacketType.POPUP_PACKET;
    }
}
