package controller;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.DefensiveBuilding;
import model.buildings.DefensiveBuildingType;
import model.game.Game;
import model.game.Government;
import model.game.Texture;
import model.game.Tile;
import model.people.Person;
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

    public String dropBuilding(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Tile tile;
        if ((tile = currentGame.getMap().getTileByLocation(x, y)) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        Object type = BuildingType.getBuildingTypeByName(matcher.group("type"));
        if (type == null)
            return Message.INVALID_BUILDING_TYPE.toString();

        // TODO: check territory if needed!
        // TODO: check if there is moat!
        if (!tile.getTexture().canHaveBuildingAndUnit())
            return Message.CANNOT_PLACE_BUILDING_ON_TEXTURE.toString();

        if (type instanceof BuildingType) {
            if ((type == BuildingType.APPLE_ORCHARD || type == BuildingType.DIARY_FARMER ||
                    type == BuildingType.HOPS_FARMER || type == BuildingType.WHEAT_FARMER) &&
                    tile.getTexture() != Texture.GRASS && tile.getTexture() != Texture.DENSE_MEADOW)
                return Message.CANNOT_PLACE_BUILDING_ON_TEXTURE.toString();

            if ((type == BuildingType.QUARRY && tile.getTexture() != Texture.ROCK) ||
                    (type == BuildingType.IRON_MINE && tile.getTexture() != Texture.IRON) ||
                    (type == BuildingType.OIL_SMELTER && tile.getTexture() != Texture.OIL))
                return Message.CANNOT_PLACE_BUILDING_ON_TEXTURE.toString();
        }

        if (tile.getBuilding() != null)
            return Message.TILE_ALREADY_HAS_BUILDING.toString();

        tile.setBuilding(type instanceof BuildingType ? new Building(this.currentGovernment, tile, (BuildingType) type) :
                new DefensiveBuilding(this.currentGovernment, tile, (DefensiveBuildingType) type));

        int workersNeeded;
        if (type instanceof BuildingType && (workersNeeded = ((BuildingType) type).getWorkersNeeded()) > 0) {
            int assignedOperators = 0;
            for (Person person : this.currentGovernment.getPeople()) {
                if (person.getWorkplace() == null) {
                    person.setWorkplace(tile.getBuilding());
                    tile.getBuilding().assignOperator(person);
                    assignedOperators++;
                    if (assignedOperators == workersNeeded)
                        break;
                }
            }
        }
        // TODO: handle situation if building needs more workers!
        return Message.DROP_BUILDING_SUCCESS.toString();
    }

    public String selectBuilding(Matcher matcher) {

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Tile tile;

        if ((tile = currentGame.getMap().getTileByLocation(x, y)) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        // TODO: decide on it!
//        if (tile.getTerritory() != this.currentGovernment)
//            return Message.TILE_IS_NOT_YOURS.toString();

        Building building;

        if ((building = tile.getBuilding()) == null)
            return Message.NO_BUILDING_IN_TILE.toString();

        if (building.getLoyalty() != this.currentGovernment)
            return Message.BUILDING_NOT_YOURS.toString();

        this.currentBuilding = currentGame.getMap().getTileByLocation(x, y).getBuilding();
        return building.getType().toString() + " selected!";
    }

    public Message deselectBuilding() {
        if (this.currentBuilding != null) {
            this.currentBuilding = null;
            return null;
        } else
            return Message.NO_BUILDING_SELECTED;
    }

    public Message repair() {
        return null;
    }
}
