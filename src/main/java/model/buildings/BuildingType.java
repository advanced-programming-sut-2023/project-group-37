package model.buildings;

import model.game.Item;

public enum BuildingType {
    // Our stockpile has hitpoints & can be destroyed!
    // hitpoints are : 150 250 350
    STOCKPILE(250, Item.WOOD, 5),
    GRANARY(250, Item.WOOD, 5),
    ARMORY(250, Item.WOOD, 5),
    MERCENARY_POST(250, Item.WOOD, 10),
    BARRACKS(250,  Item.STONE, 15),
    ENGINEER_GUILD(100,250, Item.WOOD, 10);

    private final int cost;
    private final int maxHitpoints;
    private final Item buildingMaterial;
    private final int buildingMaterialAmount;
    private final Item rawMaterial;
    private final Item product;
    private final int workersNeeded;

    // not processing
    BuildingType(int cost, int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount, int workersNeeded) {
        this(cost, maxHitpoints, buildingMaterial, buildingMaterialAmount, null, null, workersNeeded);
    }

    // not processing + not workerNeeded
    BuildingType(int cost, int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount) {
        this(cost, maxHitpoints, buildingMaterial, buildingMaterialAmount, 0);
    }

    // not processing + not workerNeeded + free
    BuildingType(int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount) {
        this(0, maxHitpoints, buildingMaterial, buildingMaterialAmount);
    }

    BuildingType(int cost, int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount, Item rawMaterial,
                 Item product, int workersNeeded) {
        this.cost = cost;
        this.maxHitpoints = maxHitpoints;
        this.buildingMaterial = buildingMaterial;
        this.buildingMaterialAmount = buildingMaterialAmount;
        this.rawMaterial = rawMaterial;
        this.product = product;
        this.workersNeeded = workersNeeded;
    }

    public int getMaxHitpoints() {
        return this.maxHitpoints;
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