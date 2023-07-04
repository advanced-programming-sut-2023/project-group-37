package connection.packet;

public enum PacketType {
    REGISTER_PACKET,
    LOGIN_PACKET,
    LOGOUT_PACKET,
    ALIVE_PACKET,
    USER_PACKET,
    REQUEST_LOBBY_PACKET,
    LOBBY_PACKET,
    FRIEND_REQUEST_PACKET,
    CHAT_PACKET,
    POPUP_PACKET,
    TILES_PACKET,
    SEARCH_PACKET,
    FOUND_USER_PACKET,

    REQ_UPDATE_CHAT,
    TRADE_REQUEST_PACKET,
    ACCEPT_PACKET,
    REFRESH_LOBBY_PACKET,
    LOBBIES_PACKET;
}
