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

    public Message repair(DefensiveBuilding defensiveBuilding) {
        if (!defensiveBuilding.getType().isRepairable())
            return Message.BUILDING_NOT_REPAIRABLE;

        int stoneNeededToRepair = (int)
                (1 - (double) (defensiveBuilding.getHitpoints() / defensiveBuilding.getMaxHitpoints())) *
                defensiveBuilding.getType().getRawMaterialUsesForSecond();

        if (!this.currentGovernment.removeItem(Item.STONE, stoneNeededToRepair))
            return Message.NOT_ENOUGH_STONE;

        defensiveBuilding.setHitpoints(defensiveBuilding.getMaxHitpoints());
        return Message.REPAIR_SUCCESS;
    }
}
