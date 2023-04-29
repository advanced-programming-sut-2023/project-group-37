package controller;

import model.game.Color;
import model.game.Game;
import model.game.Government;
import model.game.Map;
import model.user.User;
import view.enums.Message;

import java.util.ArrayList;
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

    public Message startGame(String[] usernames, int turns, int size) {

        ArrayList<Government> governments = new ArrayList<>();
        User user;

        governments.add(new Government(currentUser, Color.GREEN)); // TODO : color

        for (String username : usernames) {
            user = User.getUserByUsername(username);
            if (user == null)
                return Message.USERNAME_NOT_FOUND;

            governments.add(new Government(user, Color.GREEN)); // TODO : color
        }

        Game game = new Game(new Map(size) ,turns, governments);
        GameMenuController.setGame(game);

        return Message.GAME_STARTED;
    }

    public Message logout() {
        setCurrentUser(null);
        User.setStayLoggedIn(null);
        return Message.LOGOUT_SUCCESSFUL;
    }
}
