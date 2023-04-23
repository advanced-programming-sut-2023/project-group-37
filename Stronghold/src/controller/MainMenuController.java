package controller;

import model.game.Map;
import model.user.User;
import view.enums.messages.MainMenuMessages;

import java.util.regex.Matcher;

public class MainMenuController {
    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public MainMenuMessages enterProfileMenu() {
        ProfileMenuController.setCurrentUser(currentUser);
        return MainMenuMessages.ENTERED_PROFILE_MENU;
    }

    public MainMenuMessages startGame(Matcher matcher) {

        GameMenuController.setCurrentUser(currentUser);

        Map map = new Map(0);//todo
//        GameMenuController.setGame(map);
        MapMenuController.setMap(map);

        return MainMenuMessages.GAME_STARTED;
    }

    public MainMenuMessages logout() {
        setCurrentUser(null);
        return MainMenuMessages.LOGOUT_SUCCESSFUL;
    }
}
