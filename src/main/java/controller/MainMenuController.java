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

    private final GameMenuController gameMenuController;

    public MainMenuController(GameMenuController gameMenuController) {
        this.gameMenuController = gameMenuController;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public Message enterProfileMenu() {
        ProfileMenuController.setCurrentUser(currentUser);
        return Message.ENTERED_PROFILE_MENU;
    }

    public String startGame(String[] usernames, String[] numbers, String turns, String size) {
        int length = numbers.length;
        if (length > 7)
            return Message.USER_NUMBER_LIMIT.toString();

        int[] territories = new int[length];
        try {
            for (int index = 0; index < length; index ++) {
                territories[index] = Integer.parseInt(numbers[index]);
            }
        }
        catch (Exception ex) {
            return Message.TERRITORY_NOT_ASSIGNED.toString();
        }

        ArrayList<Government> governments = new ArrayList<>();
        User user;

        governments.add(new Government(currentUser, Color.RED, territories[0]));

        int index = 1;
        for (String username : usernames) {
            user = User.getUserByUsername(username);
            if (user == null)
                return Message.USERNAME_NOT_FOUND.toString();

            governments.add(new Government(user, Color.values()[index], territories[index-1]));
            index++;
        }

        if(turns == null || size == null)
            return Message.EMPTY_FIELD.toString();

        Game game = new Game(gameMenuController, new Map(Integer.parseInt(size)) , Integer.parseInt(turns), governments);
        gameMenuController.setCurrentGame(game);

        return Message.GAME_STARTED.toString();
    }

    public Message logout() {
        setCurrentUser(null);
        User.setStayLoggedIn(null);
        return Message.LOGOUT_SUCCESSFUL;
    }
}
