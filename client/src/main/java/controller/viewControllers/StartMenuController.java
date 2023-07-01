package controller.viewControllers;

import model.game.Territory;
import model.game.Texture;
import model.user.User;
import view.enums.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class StartMenuController {
    private ArrayList<User> users;
    private ArrayList<Territory> territories;

    public StartMenuController() {
        this.users = new ArrayList<>();
        this.territories = new ArrayList<>();
    }

    public Message add(String username, String territoryValue) {
        User user = User.getUserByUsername(username);
        if (user == null)
            return Message.USER_NOT_EXISTS;

        int territoryNumber;
        try {
            territoryNumber = Integer.parseInt(territoryValue);
        }
        catch (Exception ex) {
            return Message.INVALID_TERRITORY_NUMBER;
        }

        boolean territoryNumberExists = false;
        for (Territory territory : this.territories) {
            if (territory.getTerritoryNumber() == territoryNumber) {
                territoryNumberExists = true;
                break;
            }
        }

        if (territoryNumberExists)
            return Message.TERRITORY_EXISTS;
        return null;
    }
}
