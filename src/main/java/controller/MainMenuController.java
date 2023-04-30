package controller;

import model.game.Color;
import model.game.Game;
import model.game.Government;
import model.game.Map;
import model.user.User;
import view.enums.Message;

import java.util.ArrayList;

public class MainMenuController {
    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public Message enterProfileMenu() {
        ProfileMenuController.setCurrentUser(currentUser);
        return Message.ENTERED_PROFILE_MENU;
    }

    public String startGame(String[] usernames, String turns, String size) {
        if (usernames.length > 7)
            return Message.USER_NUMBER_LIMIT.toString();

        ArrayList<Government> governments = new ArrayList<>();
        User user;

        governments.add(new Government(currentUser, Color.BLUE));

        int i = 1;
        for (String username : usernames) {
            user = User.getUserByUsername(username);
            if (user == null)
                return Message.USERNAME_NOT_FOUND.toString();

            governments.add(new Government(user, Color.values()[i]));
            i++;
        }

        if(turns == null || size == null)
            return Message.EMPTY_FIELD.toString();

        Game game = new Game(new Map(Integer.parseInt(size)) , Integer.parseInt(turns), governments);
        GameMenuController.setGame(game);

        return Message.GAME_STARTED.toString();
    }

    public Message logout() {
        setCurrentUser(null);
        User.setStayLoggedIn(null);
        return Message.LOGOUT_SUCCESSFUL;
    }
}
