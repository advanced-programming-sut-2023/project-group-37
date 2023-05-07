package model.buildings;

import model.game.Government;
import model.game.Item;
import model.game.Tile;

public class Building {
    private final Government loyalty;
    private final Tile location;
    private final BuildingType type;
    private int hitpoints;

    public Building(Government loyalty, Tile location, BuildingType type) {
        this.loyalty = loyalty;
        this.location = location;
        this.type = type;
        this.hitpoints = type.getMaxHitpoints();
    }

    public Government getLoyalty() {
        return this.loyalty;
    }

    public Tile getLocation() {
        return this.location;
    }

    public BuildingType getType() {
        return this.type;
    }

    public int getCost() {
        return this.type.getCost();
    }

    public int getMaxHitpoints() {
        return this.type.getMaxHitpoints();
    }

    public int getHitpoints() {
        return this.hitpoints;
    }

    public Item getBuildingMaterial() {
        return this.type.getBuildingMaterial();
    }

    public int getBuildingMaterialAmount() {
        return this.type.getBuildingMaterialAmount();
    }

    public void takeDamage(int amount) {
        this.hitpoints -= amount;
    }
}
