package controller;

import model.game.Map;
import model.user.User;
import view.enums.Message;

import java.util.regex.Matcher;

public class MainMenuController {
    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public Message enterProfileMenu() {
        ProfileMenuController.setCurrentUser(currentUser);
        return Message.ENTERED_PROFILE_MENU;
    }

    public Message startGame(Matcher matcher) {

        GameMenuController.setGovernment(currentUser);

        Map map = new Map(0);//todo
//        GameMenuController.setGame(map);
        MapMenuController.setMap(map);

        return Message.GAME_STARTED;
    }

    public Message logout() {
        setCurrentUser(null);
        User.setStayLoggedIn(null);
        return Message.LOGOUT_SUCCESSFUL;
    }
}
