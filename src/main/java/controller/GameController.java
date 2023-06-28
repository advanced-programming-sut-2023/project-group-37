package controller;

import model.buildings.DefensiveBuilding;
import model.game.Government;
import model.game.Item;
import view.enums.Message;

public class GameController {
    private static final GameController gameController = new GameController();
    private Government currentGovernment;

    private GameController() {
        currentGovernment = null; // todo : server
    }

    public static GameController getInstance() {
        return gameController;
    }

}
