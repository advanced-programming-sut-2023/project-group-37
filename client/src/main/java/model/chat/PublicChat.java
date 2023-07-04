package model.chat;

public class PublicChat extends Chat {
    private final static PublicChat publicChat = new PublicChat();

    public PublicChat() {
        super(null, ChatType.PUBLIC);
    }

    public static PublicChat getInstance() {
        return publicChat;
    }
}
