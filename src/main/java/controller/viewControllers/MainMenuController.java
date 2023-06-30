package controller.viewControllers;

import controller.GameController;
import model.game.GameColor;
import model.game.Game;
import model.game.Government;
import model.game.Map;
import model.user.User;
import view.enums.Message;

import java.util.ArrayList;

public class MainMenuController {
    private static final MainMenuController mainMenuController;
    private static User currentUser;
    private final GameController gameController;

    static {
        mainMenuController = new MainMenuController();
    }

    {
        gameController = GameController.getInstance();
    }

    private MainMenuController() {

    }

    public static MainMenuController getInstance() {
        return mainMenuController;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public Message enterProfileMenu() {
        ProfileMenuController.setCurrentUser(currentUser);
        return Message.ENTERED_PROFILE_MENU;
    }

    public String startGame(String[] usernames, String[] numbers, String turns, String name) {
        int length = numbers.length;
        if (length > 7)
            return Message.USER_NUMBER_LIMIT.toString();

        if (turns == null || name == null)
            return Message.EMPTY_FIELD.toString();

        int[] territories = new int[length];
        try {
            for (int index = 0; index < length; index++) {
                territories[index] = Integer.parseInt(numbers[index]);
                if (territories[index] > 8 || territories[index] == 1)
                    return Message.INVALID_TERRITORY_NUMBER.toString();
            }
        } catch (Exception ex) {
            return Message.TERRITORY_NOT_ASSIGNED.toString();
        }

        ArrayList<Government> governments = new ArrayList<>();
        User user;

        Map map = Map.getMapCopyByName(name);
        if (map == null)
            return Message.INVALID_MAP_NAME.toString();

        governments.add(new Government(currentUser, GameColor.RED.getColor(), map, 1));

        int index = 1;
        for (String username : usernames) {
            user = User.getUserByUsername(username);
            if (user == null)
                return Message.USERNAME_NOT_FOUND.toString();

            governments.add(new Government(user, GameColor.values()[index].getColor(), map, territories[index - 1]));
            index++;
        }

        Game game = new Game(map, governments);
        gameController.setCurrentGame(game);

        return Message.GAME_STARTED.toString();
    }

    public void logout() {
        setCurrentUser(null);
        User.setStayLoggedIn(null);
    }
}
