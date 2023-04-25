package model.buildings;

import model.game.Item;

public enum BuildingType {
    ;
    private final String name;
    private final int maxHitpoints;
    private final int goldNeededToBuild;
    private final Item buildingMaterial;
    private final int buildingMaterialAmount;
    private final Item rawMaterial;
    private final Item product;
    private final int workersNeeded;

    BuildingType(String name, int maxHitpoints, int goldNeededToBuild, Item buildingMaterial,
                 int buildingMaterialAmount, Item rawMaterial, Item product, int workersNeeded) {
        this.name = name;
        this.maxHitpoints = maxHitpoints;
        this.goldNeededToBuild = goldNeededToBuild;
        this.buildingMaterial = buildingMaterial;
        this.buildingMaterialAmount = buildingMaterialAmount;
        this.rawMaterial = rawMaterial;
        this.product = product;
        this.workersNeeded = workersNeeded;
    }

    public String getName() {
        return this.name;
    }

    public int getMaxHitpoints() {
        return this.maxHitpoints;
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

    public Item getRawMaterial() {
        return this.rawMaterial;
    }

    public Item getProduct() {
        return this.product;
    }

    public int getWorkersNeeded() {
        return this.workersNeeded;
    }

    public BuildingType getBuildingTypeByName(String name) {
        return valueOf(name.toUpperCase());
    }
}