package model.buildings;

import model.game.Government;
import model.game.Tile;
import model.people.MilitaryUnit;
import model.people.Troop;

import java.util.ArrayList;

public class DefensiveBuilding extends Building {

    private final DefensiveBuildingType type;

    // TODO: How about catapult?
    private final ArrayList<MilitaryUnit> troops;

    public DefensiveBuilding(Government loyalty, Tile location, DefensiveBuildingType type) {
        super(loyalty, location);
        this.type = type;
        super.setHitpoints(type.getMaxHitpoints());
        this.troops = new ArrayList<>();
    }

    public boolean isFull(){
        return this.troops.size() == this.type.getCapacity();
    }

    @Override
    public int getHitpoints() {
        return super.getHitpoints();
    }
}
