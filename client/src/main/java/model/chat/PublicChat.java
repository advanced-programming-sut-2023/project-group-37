package model.chat;

import model.user.User;

public class PublicChat extends Chat {

    private static PublicChat publicChat;

    public PublicChat(User owner, User... subscribers) {
        super(owner, ChatType.PUBLIC, subscribers);
    }

    public static PublicChat getInstance() {
        return publicChat;
    }
}
