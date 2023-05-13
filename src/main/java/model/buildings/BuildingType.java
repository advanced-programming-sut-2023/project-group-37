package model.buildings;

import model.game.Item;
import model.people.Person;

import java.util.ArrayList;

public enum BuildingType {

    // hitpoints are : 150 250 350 450

    // Processing:
    // TODO: handle stable as processing when horse defined!
    // TODO: define wall, stairs!
//    STABLE(400, 250, Item.WOOD, 20),
    APPLE_ORCHARD("apple orchard", 150, Item.WOOD, 5, Item.APPLE, 4,
            1, false, false, false),
    DIARY_FARMER("diary farmer", 150, Item.WOOD, 10, Item.CHEESE, 4,
            1, false, false, false),
    HOPS_FARMER("hops farmer", 150, Item.WOOD, 15, Item.HOPS, 3,
            1, false, false, false),
    HUNTER_POST("hunter post", 150, Item.WOOD, 5, Item.MEAT, 4,
            1, false, false, false),
    WHEAT_FARMER("wheat farmer", 150, Item.WOOD, 15, Item.WHEAT, 2,
            1, false, false, false),
    BAKERY("bakery", 150, Item.WOOD, 10, Item.FLOUR, 4, Item.BREAD,
            1, 1, false, false, false),
    BREWER("brewer", 150, Item.WOOD, 10, Item.HOPS, 1, Item.ALE,
            2, 1, false, false, false),
    INN("inn", 100, 150, Item.WOOD, 20, Item.ALE, 1, null,
            0, 1, false, false, false),
    MILL("mill", 150, Item.WOOD, 20, Item.WHEAT, 3, Item.FLOUR,
            3, 3, false, false, false),
    IRON_MINE("iron mine", 150, Item.WOOD, 20, Item.IRON, 2,
            2, false, false, false),
    PITCH_RIG("pitch rig", 150, Item.WOOD, 20, Item.PITCH, 1,
            false, false, false),
    QUARRY("quarry", 150, Item.WOOD, 20, Item.STONE, 3,
            false, false, false),
    WOODCUTTER("woodcutter", 150, Item.WOOD, 3, Item.WOOD, 18,
            1, false, false, false),
    ARMOURER("armourer", 100, 150, Item.WOOD, 20, Item.IRON, 1,
            Item.METAL_ARMOR, 1, 1, false, false, false),
    BLACKSMITH("blacksmith", 200, 150, Item.WOOD, 20, Item.IRON,
            1, 1, Item.SWORD, Item.MACE, 1, 1,
            false, false, false),
    // TODO: rawMaterialUses is related to product mode!
    FLETCHER("fletcher", 100, 150, Item.WOOD, 20, Item.WOOD,
            2, 3, Item.BOW, Item.CROSSBOW, 1, 1,
            false, false, false),
    POLETURNER("poleturner", 100, 150, Item.WOOD, 10, Item.WOOD,
            1, 2, Item.SPEAR, Item.PIKE, 1, 1,
            false, false, false),
    // TODO: change it after implementing cow!
    TANNER("tanner", 100, 150, Item.WOOD, 10, null, 1,
            Item.LEATHER_ARMOR, 3, 1, false, false, false),

    // Non processing:
    // TODO: decide not to be destroyable (as real game) or else!
    DRAWBRIDGE("drawbridge", 250, Item.WOOD, 10, false,
            false, false),
    TUNNELER_GUILD("tunneler guild", 100, 250, Item.WOOD, 10, 0,
            true, true, false),
    OX_TETHER("ox tether", 150, Item.WOOD, 5, 1, false,
            false, false),
    HOVEL("hovel", 150, Item.WOOD, 6, false, false,
            false),
    //    CHAPEL(250, null, 0),
    CHURCH("church", 500, null, 0, false,
            false, false),
    CATHEDRAL("cathedral", 1000, null, 0, false,
            false, false),
//    GOOD_THINGS("good things", 25, null, 0),
//    BAD_THINGS("bad things", 45, null, 0),
    // TODO: I set a dummy hp of 1 not to be removed!
    KILLING_PIT("killing pit", 1, Item.WOOD, 6, true, true,
            false),
    OIL_SMELTER("oil smelter", 100, Item.IRON, 10, 1, true,
            false, false),
    CAGED_WAR_DOGS("caged war dogs", 100, 150, Item.WOOD, 10, 0,
            false, false, false),
    // TODO: name is null because only we can create it!
    SIEGE_TENT(null, 150, null, 0, true,
            false, false),
    TUNNEL_ENTRANCE("tunnel entrance", 150, null, 0, true,
            false, false),

    // Our stockpile has hitpoints & can be destroyed!
    STOCKPILE("stockpile", 250, 150),
    GRANARY("granary", 250, 250),
    ARMORY("armory", 250, 50),
    MERCENARY_POST("mercenary post", 250, Item.WOOD, 10, true,
            false, false),
    BARRACKS("barracks", 250, Item.STONE, 15, true, false,
            false),
    ENGINEER_GUILD("engineer guild", 100, 250, Item.WOOD, 10, 0,
            true, true, false),
    MARKET("market", 250, Item.WOOD, 5, 1, false,
            false, false);

    private final String name;
    private final int cost;
    private final int maxHitpoints;
    private final Item buildingMaterial;
    private final int buildingMaterialAmount;
    private final Item rawMaterial;
    private final int rawMaterialUsesForFirst;
    private final int rawMaterialUsesForSecond;
    private final Item firstProduct;
    private final Item secondProduct;
    private final int productProvides;
    private final int workersNeeded;
    private final boolean canHoldTroop;
    private final boolean canHoldMachine;
    private final boolean isRepairable;
    private int capacity;

    // storage:
    BuildingType(String name, int maxHitpoints, int capacity) {
        this.name = name;
        this.cost = 0;
        this.maxHitpoints = maxHitpoints;
        this.buildingMaterial = Item.WOOD;
        this.buildingMaterialAmount = 5;
        this.rawMaterial = null;
        this.rawMaterialUsesForFirst = 0;
        this.rawMaterialUsesForSecond = 0;
        this.firstProduct = null;
        this.secondProduct = null;
        this.productProvides = 0;
        this.workersNeeded = 0;
        this.canHoldTroop = false;
        this.canHoldMachine = false;
        this.isRepairable = false;
        this.capacity = capacity;
    }

    // multi item producers:
    BuildingType(String name, int cost, int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount,
                 Item rawMaterial, int rawMaterialUsesForFirst, int rawMaterialUsesForSecond, Item firstProduct,
                 Item secondProduct, int productProvides, int workersNeeded, boolean canHoldTroop,
                 boolean canHoldMachine, boolean isRepairable) {
        this.name = name;
        this.cost = cost;
        this.maxHitpoints = maxHitpoints;
        this.buildingMaterial = buildingMaterial;
        this.buildingMaterialAmount = buildingMaterialAmount;
        this.rawMaterial = rawMaterial;
        this.rawMaterialUsesForFirst = rawMaterialUsesForFirst;
        this.rawMaterialUsesForSecond = rawMaterialUsesForSecond;
        this.firstProduct = firstProduct;
        this.secondProduct = secondProduct;
        this.productProvides = productProvides;
        this.workersNeeded = workersNeeded;
        this.canHoldTroop = canHoldTroop;
        this.canHoldMachine = canHoldMachine;
        this.isRepairable = isRepairable;
    }

    // Free processing:
    BuildingType(String name, int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount, Item rawMaterial,
                 int rawMaterialUses, Item product, int productProvides, int workersNeeded,
                 boolean canHoldTroop, boolean canHoldMachine, boolean isRepairable) {
        this(name, 0, maxHitpoints, buildingMaterial, buildingMaterialAmount, rawMaterial, rawMaterialUses,
                product, productProvides, workersNeeded, canHoldTroop, canHoldMachine, isRepairable);
    }

    // Free non rawMaterialNeeded processing:
    BuildingType(String name, int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount, Item product,
                 int productProvides, int workersNeeded, boolean canHoldTroop, boolean canHoldMachine,
                 boolean isRepairable) {
        this(name, maxHitpoints, buildingMaterial, buildingMaterialAmount, null, 0, product,
                productProvides, workersNeeded, canHoldTroop, canHoldMachine, isRepairable);
    }

    // Free one provider non rawMaterialNeeded processing:
    BuildingType(String name, int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount, Item product,
                 int workersNeeded, boolean canHoldTroop, boolean canHoldMachine, boolean isRepairable) {
        this(name, maxHitpoints, buildingMaterial, buildingMaterialAmount, null, 0, product,
                1, workersNeeded, canHoldTroop, canHoldMachine, isRepairable);
    }

    // Non processing:
    BuildingType(String name, int cost, int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount,
                 int workersNeeded, boolean canHoldTroop, boolean canHoldMachine, boolean isRepairable) {
        this(name, cost, maxHitpoints, buildingMaterial, buildingMaterialAmount, null, 0, null,
                0, workersNeeded, canHoldTroop, canHoldMachine, isRepairable);
    }

    // Free non processing:
    BuildingType(String name, int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount, int workersNeeded,
                 boolean canHoldTroop, boolean canHoldMachine, boolean isRepairable) {
        this(name, 0, maxHitpoints, buildingMaterial, buildingMaterialAmount, workersNeeded,
                canHoldTroop, canHoldMachine, isRepairable);
    }

    // Free non workerNeeded non processing:
    BuildingType(String name, int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount,
                 boolean canHoldTroop, boolean canHoldMachine, boolean isRepairable) {
        this(name, maxHitpoints, buildingMaterial, buildingMaterialAmount, 0, canHoldTroop, canHoldMachine,
                isRepairable);
    }

    // Main constructor:
    BuildingType(String name, int cost, int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount,
                 Item rawMaterial, int rawMaterialUses, Item product, int productProvides, int workersNeeded,
                 boolean canHoldTroop, boolean canHoldMachine, boolean isRepairable) {
        this.name = name;
        this.cost = cost;
        this.maxHitpoints = maxHitpoints;
        this.buildingMaterial = buildingMaterial;
        this.buildingMaterialAmount = buildingMaterialAmount;
        this.rawMaterial = rawMaterial;
        this.rawMaterialUsesForFirst = rawMaterialUses;
        this.rawMaterialUsesForSecond = 0;
        this.firstProduct = product;
        this.secondProduct = null;
        this.productProvides = productProvides;
        this.workersNeeded = workersNeeded;
        this.canHoldTroop = canHoldTroop;
        this.canHoldMachine = canHoldMachine;
        this.isRepairable = isRepairable;
    }

    public static Object getBuildingTypeByName(String name) {
        for (BuildingType type : values())
            if (type.getName().equals(name))
                return type;
        for (DefensiveBuildingType type : DefensiveBuildingType.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    public String getName() {
        return this.name;
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
        return this.rawMaterialUsesForFirst;
    }

    public int getRawMaterialUsesForSecond() {
        return this.rawMaterialUsesForSecond;
    }

    public Item getProduct() {
        return this.firstProduct;
    }

    public Item getSecondProduct() {
        return this.secondProduct;
    }

    public int getProductProvides() {
        return this.productProvides;
    }

    public int getWorkersNeeded() {
        return this.workersNeeded;
    }

    public boolean canHoldTroop() {
        return this.canHoldTroop;
    }

    public boolean canHoldMachine() {
        return this.canHoldMachine;
    }

    public boolean isRepairable() {
        return this.isRepairable;
    }

    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public String toString() {
        return this.name();
    }
}