package controller;

import model.buildings.Building;
import model.game.Game;
import model.game.Government;
import model.game.Tile;
import view.enums.Message;

import java.util.regex.Matcher;

public class BuildingMenuController {

    // TODO: I couldn't understand why they was static so I turned to non static!

    private static Game currentGame;
    private Building currentBuilding;
    private Government currentGovernment;

    public void setCurrentGovernment(Government currentGovernment) {
        this.currentGovernment = currentGovernment;
    }

    public static void setCurrentGame(Game game) {
        currentGame = game;
    }

    public String selectBuilding(Matcher matcher) {

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Tile tile;

        if ((tile = currentGame.getMap().getTileByLocation(x, y)) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        if (tile.getTerritory() != this.currentGovernment)
            return Message.TILE_IS_NOT_YOURS.toString();

        Building building;

        if ((building = tile.getBuilding()) == null)
            return Message.NO_BUILDING_IN_TILE.toString();

        if (building.getLoyalty() != this.currentGovernment)
            return Message.BUILDING_NOT_YOURS.toString();

        this.currentBuilding = currentGame.getMap().getTileByLocation(x, y).getBuilding();
        return building.getType().toString() + " selected!";
    }

    public Message repair() {
        return null;
    }
}
