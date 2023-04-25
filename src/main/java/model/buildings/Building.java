package model.buildings;

import model.game.Government;
import model.game.Item;
import model.game.Tile;

public class Building {
    private final Government loyalty;
    private final Tile location;
    private final BuildingType type;
    private final int maxHitpoints;
    private int hitpoints;
    private final int goldNeededToBuild;
    private final Item buildingMaterial;
    private final int buildingMaterialAmount;

    public Building(Government loyalty, Tile location, BuildingType type) {
        this.loyalty = loyalty;
        this.location = location;
        this.type = type;
        this.maxHitpoints = type.getMaxHitpoints();
        this.hitpoints = type.getMaxHitpoints();
        this.goldNeededToBuild = type.getGoldNeededToBuild();
        this.buildingMaterial = type.getBuildingMaterial();
        this.buildingMaterialAmount = type.getBuildingMaterialAmount();
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

    public int getMaxHitpoints() {
        return this.maxHitpoints;
    }

    public int getHitpoints() {
        return this.hitpoints;
    }

    public int getGoldNeededToBuild() {
        return this.goldNeededToBuild;
    }

    public Item getBuildingMaterial() {
        return this.buildingMaterial;
    }

    public int getBuildingMaterialAmount() {
        return this.buildingMaterialAmount;
    }

    public void takeDamage(int amount) {
        this.hitpoints -= amount;
    }
}
