package model.buildings;

import model.game.Government;
import model.game.Tile;

import java.util.ArrayList;

public class DefensiveBuilding extends Building {

    private Government owner;
    private final DefensiveBuildingType type;
    private final ArrayList<DefensiveBuilding> defensiveNeighbors;
    private boolean hasLadderAttached;
    private boolean canBeReached;
    private char direction;

    public DefensiveBuilding(Government loyalty, Tile location, DefensiveBuildingType type, char direction) {
        super(loyalty, location);
        this.owner = loyalty;
        this.type = type;
        super.setHitpoints(type.getMaxHitpoints());
        this.defensiveNeighbors = new ArrayList<>();
        this.direction = direction;
    }

    public void setOwner() {
        Government temp = null;
        if (this.getLocation().getMilitaryUnits().isEmpty())
            return;
        for (int i = 0; i < this.getLocation().getMilitaryUnits().size(); i++) {
            if (i == 0)
                temp = this.getLocation().getMilitaryUnits().get(0).getLoyalty();
            else if (this.getLocation().getMilitaryUnits().get(0).getLoyalty() != temp)
                return;
        }
        this.owner = temp;
    }

    public DefensiveBuildingType getDefensiveType() {
        return this.type;
    }

    public ArrayList<DefensiveBuilding> getDefensiveNeighbors() {
        return this.defensiveNeighbors;
    }

    public boolean hasLadderAttached() {
        return this.hasLadderAttached;
    }

    public void setHasLadderAttached(boolean hasLadderAttached) {
        this.hasLadderAttached = hasLadderAttached;
    }

    public boolean canBeReached() {
        return this.canBeReached;
    }

    public void setCanBeReached(boolean canBeReached) {
        this.canBeReached = canBeReached;
    }

    public char getDirection() {
        return this.direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public void addDefensiveNeighbor(DefensiveBuilding building) {
        this.defensiveNeighbors.add(building);
    }

    public boolean isFull() {
        return super.getLocation().getMilitaryUnits().size() == this.type.getCapacity();
    }

}
