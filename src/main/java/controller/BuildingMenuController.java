package controller;

import model.buildings.BuildingType;
import model.game.Game;
import model.game.Government;
import model.user.User;
import view.enums.Message;

public class BuildingMenuController {
    private static Game game;
    private static Government government;
    private static BuildingType building;

    public static void setGovernment(Government government) {
        BuildingMenuController.government = government;
    }
    public static void setGame(Game game) {
        BuildingMenuController.game = game;
    }

    public static void setBuilding(BuildingType building) {
        BuildingMenuController.building = building;
    }

    public Message repair() {
        return null;
    }
}
