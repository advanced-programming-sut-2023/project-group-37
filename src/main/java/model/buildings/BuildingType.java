package model.buildings;

import model.game.Item;

public enum BuildingType {

    // hitpoints are : 150 250 350 450

    // Processing:
    // TODO: handle stable as processing when horse defined!
//    STABLE(400, 250, Item.WOOD, 20),
    APPLE_ORCHARD(150, Item.WOOD, 5, Item.APPLE, 4, 1),
    DIARY_FARMER(150, Item.WOOD, 10, Item.CHEESE, 4, 1),
    HOPS_FARMER(150, Item.WOOD, 15, Item.HOPS, 3, 1),
    HUNTER_POST(150, Item.WOOD, 5, Item.MEAT, 4, 1),
    WHEAT_FARMER(150, Item.WOOD, 15, Item.WHEAT, 2, 1),
    BAKERY(150, Item.WOOD, 10, Item.WHEAT, 4, Item.FLOUR, 1, 1),
    BREWER(150, Item.WOOD, 10, Item.HOPS, 1, Item.ALE, 2, 1),
    INN(100, 150, Item.WOOD, 20, Item.ALE, 1, null, 0, 1),
    MILL(150, Item.WOOD, 20, Item.WHEAT, 3, Item.FLOUR, 3, 3),
    IRON_MINE(150, Item.WOOD, 20, Item.IRON, 2, 2),
    PITCH_RIG(150, Item.WOOD, 20, Item.PITCH, 1),
    QUARRY(150, Item.WOOD, 20, Item.STONE, 3),
    WOODCUTTER(150, Item.WOOD, 3, Item.WOOD, 18, 1),
    ARMOURER(100, 150, Item.WOOD, 20, Item.IRON, 1, Item.METAL_ARMOR, 1, 1),
    BLACKSMITH(200, 150, Item.WOOD, 20, Item.IRON, 1, Item.SWORD, 1, 1),
    // TODO: rawMaterialUses is related to product mode!
    FLETCHER(100, 150, Item.WOOD, 20, Item.WOOD, 2, Item.BOW, 1, 1),
    POLETURNER(100, 150, Item.WOOD, 10, Item.WOOD, 1, Item.SPEAR, 1, 1),
    // TODO: change it after implementing cow!
    TANNER(100, 150, Item.WOOD, 10, null, 1, Item.LEATHER_ARMOR, 3, 1),

    // Non processing:
    SMALL_GATEHOUSE(350, Item.STONE, 10),
    LARGE_GATEHOUSE(450, Item.STONE, 20),
    // TODO: decide not to be destroyable (as real game) or else!
    DRAWBRIDGE(250, Item.WOOD, 10),
    // TODO: decide if they need a seperated class or category field or else!
    LOOKOUT_TOWER(250, Item.STONE, 10),
    PERIMETER_TOWER(350, Item.STONE, 10),
    DEFENCE_TOWER(350, Item.STONE, 15),
    SQUARE_TOWER(450, Item.STONE, 35),
    ROUND_TOWER(450, Item.STONE, 40),
    TUNNELER_GUILD(100, 250, Item.WOOD, 10, 0),
    OX_TETHER(150, Item.WOOD, 5, 1),
    HOVEL(150, Item.WOOD, 6),
    //    CHAPEL(250, null, 0),
    CHURCH(500, null, 0),
    CATHEDRAL(1000, null, 0),
    //    WELL(30, null, 0, 1),
//    WATER_POT(60, null, 0, 3),
    GOOD_THINGS(25, null, 0),
    BAD_THINGS(45, null, 0),
    // TODO: I set a dummy hp of 1 not to be removed!
    KILLING_PIT(1, Item.WOOD, 6),
    OIL_SMELTER(100, Item.IRON, 10, 1),
    // TODO: I set a dummy hp of 1 not to be removed!
    PITCH_DITCH(1, Item.PITCH, 1),
    CAGED_WAR_DOGS(100, 150, Item.WOOD, 10, 0),
    SIEGE_TENT(150, null, 0),
    TUNNEL_ENTRANCE(150, null, 0),

    // Our stockpile has hitpoints & can be destroyed!
    STOCKPILE(250, Item.WOOD, 5),
    GRANARY(250, Item.WOOD, 5),
    ARMORY(250, Item.WOOD, 5),
    MERCENARY_POST(250, Item.WOOD, 10),
    BARRACKS(250, Item.STONE, 15),
    ENGINEER_GUILD(100, 250, Item.WOOD, 10, 0),
    MARKET(250, Item.WOOD, 5, 1);

    private final int cost;
    private final int maxHitpoints;
    private final Item buildingMaterial;
    private final int buildingMaterialAmount;
    private final Item rawMaterial;
    private final int rawMaterialUses;
    private final Item product;
    private final int productProvides;
    private final int workersNeeded;

    // Free processing:
    BuildingType(int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount, Item rawMaterial,
                 int rawMaterialUses, Item product, int productProvides, int workersNeeded) {
        this(0, maxHitpoints, buildingMaterial, buildingMaterialAmount, rawMaterial,
                rawMaterialUses, product, productProvides, workersNeeded);
    }

    // Free one to one processing:
    BuildingType(int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount, Item rawMaterial,
                 Item product, int workersNeeded) {
        this(maxHitpoints, buildingMaterial, buildingMaterialAmount, rawMaterial,
                1, product, 1, workersNeeded);
    }

    // Free non rawMaterialNeeded processing:
    BuildingType(int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount, Item product,
                 int productProvides, int workersNeeded) {
        this(maxHitpoints, buildingMaterial, buildingMaterialAmount, null, 0, product,
                productProvides, workersNeeded);
    }

    // Free one provider non rawMaterialNeeded processing:
    BuildingType(int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount, Item product,
                 int workersNeeded) {
        this(maxHitpoints, buildingMaterial, buildingMaterialAmount, null, 0, product,
                1, workersNeeded);
    }

    // Non processing:
    BuildingType(int cost, int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount,
                 int workersNeeded) {
        this(cost, maxHitpoints, buildingMaterial, buildingMaterialAmount, null, 0, null,
                0, workersNeeded);
    }

    // Free non processing:
    BuildingType(int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount, int workersNeeded) {
        this(0, maxHitpoints, buildingMaterial, buildingMaterialAmount, workersNeeded);
    }

    // Free non workerNeeded non processing:
    BuildingType(int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount) {
        this(maxHitpoints, buildingMaterial, buildingMaterialAmount, 0);
    }

    // Main constructor:
    BuildingType(int cost, int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount,
                 Item rawMaterial, int rawMaterialUses, Item product, int productProvides, int workersNeeded) {
        this.cost = cost;
        this.maxHitpoints = maxHitpoints;
        this.buildingMaterial = buildingMaterial;
        this.buildingMaterialAmount = buildingMaterialAmount;
        this.rawMaterial = rawMaterial;
        this.rawMaterialUses = rawMaterialUses;
        this.product = product;
        this.productProvides = productProvides;
        this.workersNeeded = workersNeeded;
    }

    public static BuildingType getBuildingTypeByName(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (Exception ignored) {
            return null;
        }
    }

    public int getCost() {
        return this.cost;
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

    public int getRawMaterialUses() {
        return this.rawMaterialUses;
    }

    public Item getProduct() {
        return this.product;
    }

    public int getProductProvides() {
        return this.productProvides;
    }

    public int getWorkersNeeded() {
        return this.workersNeeded;
    }
}