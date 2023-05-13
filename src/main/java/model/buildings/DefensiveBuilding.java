package model.buildings;

import model.game.Government;
import model.game.Tile;
import model.people.MilitaryUnit;

import java.util.ArrayList;

public class DefensiveBuilding extends Building {

    private final DefensiveBuildingType type;

    // TODO: How about catapult?

    private final ArrayList<DefensiveBuilding> defensiveNeighbors;

    public DefensiveBuilding(Government loyalty, Tile location, DefensiveBuildingType type) {
        super(loyalty, location);
        this.type = type;
        super.setHitpoints(type.getMaxHitpoints());
        this.defensiveNeighbors = new ArrayList<>();
    }

    public DefensiveBuildingType getDefensiveType() {
        return this.type;
    }

    public ArrayList<DefensiveBuilding> getDefensiveNeighbors() {
        return this.defensiveNeighbors;
    }

    public void addDefensiveNeighbor(DefensiveBuilding building){
        this.defensiveNeighbors.add(building);
    }

    public boolean isFull() {
        return super.getLocation().getMilitaryUnits().size() == this.type.getCapacity();
    }
}
