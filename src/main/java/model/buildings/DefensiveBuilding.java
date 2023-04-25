package model.buildings;

import model.game.Government;
import model.game.Tile;

public class DefensiveBuilding extends Building{

    public DefensiveBuilding(Government loyalty, Tile location, BuildingType type) {
        super(loyalty, location, type);
    }
}
