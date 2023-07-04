package connection.packet;

import model.game.TradeRequest;

public class TradePacket extends Packet {
    private final TradeRequest tradeRequest;

    public TradePacket(TradeRequest tradeRequest) {
        super.type = PacketType.TRADE_REQUEST_PACKET;
        this.tradeRequest = tradeRequest;
    }

    public TradeRequest getTradeRequest() {
        return this.tradeRequest;
    }
}