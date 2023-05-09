package controller;

import model.buildings.BuildingType;
import model.game.Game;
import model.game.Government;
import view.enums.Message;

public class BuildingMenuController {
    private static Game game;
    private static BuildingType currentBuilding;
    private Government currentBuilingLoyalty;

    public void setCurrentBuilingLoyalty(Government currentBuilingLoyalty) {
        this.currentBuilingLoyalty = currentBuilingLoyalty;
    }

    public static void setGame(Game game) {
        BuildingMenuController.game = game;
    }

    public static void setCurrentBuilding(BuildingType currentBuilding) {
        BuildingMenuController.currentBuilding = currentBuilding;
    }

    public Message repair() {
        return null;
    }
}
