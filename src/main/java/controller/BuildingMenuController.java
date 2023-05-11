package controller;

import model.buildings.Building;
import model.game.Game;
import model.game.Government;
import model.game.Item;
import view.enums.Message;

public class BuildingMenuController {

    // TODO: I couldn't understand why they was static so I turned to non static!

    private static Game currentGame;
    private Building currentBuilding;
    private Government currentGovernment;

    public void setCurrentGovernment(Government currentGovernment) {
        this.currentGovernment = currentGovernment;
    }

    public Building getCurrentBuilding() {
        return this.currentBuilding;
    }

    public void setCurrentBuilding(Building currentBuilding) {
        this.currentBuilding = currentBuilding;
    }

    public void setCurrentGame(Game game) {
        currentGame = game;
    }

    public Message deselectBuilding() {
        if (this.currentBuilding != null) {
            this.currentBuilding = null;
            return null;
        } else
            return Message.NO_BUILDING_SELECTED;
    }

    public Message repair() {

        if (!this.currentBuilding.getType().isRepairable())
            return Message.BUILDING_NOT_REPAIRABLE;

        int stoneNeededToRepair = (int)
                (1 - (double) (this.currentBuilding.getHitpoints() / this.getCurrentBuilding().getMaxHitpoints())) *
                this.currentBuilding.getType().getRawMaterialUsesForSecond();

        if (!this.currentGovernment.removeFromTargetRepository(this.currentGovernment.getStockpile(), Item.STONE,
                stoneNeededToRepair))
            return Message.STONE_NOT_ENOUGH;

        this.currentBuilding.setHitpoints(this.currentBuilding.getMaxHitpoints());
        return Message.REPAIR_SUCCESS;
    }
}
