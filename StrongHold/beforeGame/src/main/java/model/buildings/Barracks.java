package model.buildings;

import model.game.Government;
import model.game.Tile;

public class Barracks extends Building{
    public Barracks(Government loyalty, Tile location, BuildingType type) {
        super(loyalty, location, type);
    }
}
