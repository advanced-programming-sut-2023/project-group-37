package model.buildings;

import model.game.Item;

public enum BuildingType {

    // hitpoints are : 150 250 350 450

    // Processing:
    // TODO: handle stable as processing when horse defined!
//    STABLE(400, 250, Item.WOOD, 20),
    APPLE_ORCHARD("appleorchard", 150, Item.WOOD, 5, Item.APPLE, 4, 1),
    DIARY_FARMER("diaryfarmer", 150, Item.WOOD, 10, Item.CHEESE, 4, 1),
    HOPS_FARMER("hopsfarmer", 150, Item.WOOD, 15, Item.HOPS, 3, 1),
    HUNTER_POST("hunterpost", 150, Item.WOOD, 5, Item.MEAT, 4, 1),
    WHEAT_FARMER("wheatfarmer", 150, Item.WOOD, 15, Item.WHEAT, 2, 1),
    BAKERY("bakery", 150, Item.WOOD, 10, Item.WHEAT, 4, Item.FLOUR, 1, 1),
    BREWER("brewer", 150, Item.WOOD, 10, Item.HOPS, 1, Item.ALE, 2, 1),
    INN("inn",100, 150, Item.WOOD, 20, Item.ALE, 1, null, 0, 1),
    MILL("mill", 150, Item.WOOD, 20, Item.WHEAT, 3, Item.FLOUR, 3, 3),
    IRON_MINE("ironmine", 150, Item.WOOD, 20, Item.IRON, 2, 2),
    PITCH_RIG("pitchrig", 150, Item.WOOD, 20, Item.PITCH, 1),
    QUARRY("quarry", 150, Item.WOOD, 20, Item.STONE, 3),
    WOODCUTTER("woodcutter", 150, Item.WOOD, 3, Item.WOOD, 18, 1),
    ARMOURER("armourer", 100, 150, Item.WOOD, 20, Item.IRON, 1, Item.METAL_ARMOR, 1, 1),
    BLACKSMITH("blacksmith", 200, 150, Item.WOOD, 20, Item.IRON, 1,1, Item.SWORD, Item.MACE, 1, 1),
    // TODO: rawMaterialUses is related to product mode!
    FLETCHER("fletcher", 100, 150, Item.WOOD, 20, Item.WOOD, 2, 3, Item.BOW, Item.CROSSBOW, 1, 1),
    POLETURNER("poleturner", 100, 150, Item.WOOD, 10, Item.WOOD, 1, 2, Item.SPEAR, Item.PIKE, 1, 1),
    // TODO: change it after implementing cow!
    TANNER("tanner", 100, 150, Item.WOOD, 10, null, 1, Item.LEATHER_ARMOR, 3, 1),

    // Non processing:
    SMALL_GATEHOUSE("smallgatehouse", 350, Item.STONE, 10),
    LARGE_GATEHOUSE("largegatehouse", 450, Item.STONE, 20),
    // TODO: decide not to be destroyable (as real game) or else!
    DRAWBRIDGE("drawbridge", 250, Item.WOOD, 10),
    // TODO: decide if they need a seperated class or category field or else!
    LOOKOUT_TOWER("lookouttower", 250, Item.STONE, 10),
    PERIMETER_TOWER("perimetertower", 350, Item.STONE, 10),
    DEFENCE_TOWER("defencetower", 350, Item.STONE, 15),
    SQUARE_TOWER("squaretower", 450, Item.STONE, 35),
    ROUND_TOWER("roundtower", 450, Item.STONE, 40),
    TUNNELER_GUILD("tunnelerguild", 100, 250, Item.WOOD, 10, 0),
    OX_TETHER("oxtether",150, Item.WOOD, 5, 1),
    HOVEL("hovel", 150, Item.WOOD, 6),
    //    CHAPEL(250, null, 0),
    CHURCH("church", 500, null, 0),
    CATHEDRAL("cathedral", 1000, null, 0),
    //    WELL(30, null, 0, 1),
//    WATER_POT(60, null, 0, 3),
    GOOD_THINGS("goodthings", 25, null, 0),
    BAD_THINGS("badthings", 45, null, 0),
    // TODO: I set a dummy hp of 1 not to be removed!
    KILLING_PIT("killingpit", 1, Item.WOOD, 6),
    OIL_SMELTER("oilsmelter", 100, Item.IRON, 10, 1),
    // TODO: I set a dummy hp of 1 not to be removed!
    PITCH_DITCH("pitchditch", 1, Item.PITCH, 1),
    CAGED_WAR_DOGS("cagedwardogs", 100, 150, Item.WOOD, 10, 0),
    SIEGE_TENT("siegetent", 150, null, 0),
    TUNNEL_ENTRANCE("tunnelentrance", 150, null, 0),

    // Our stockpile has hitpoints & can be destroyed!
    STOCKPILE("stockpile", 250, Item.WOOD, 5),
    GRANARY("granary", 250, Item.WOOD, 5),
    ARMORY("armory", 250, Item.WOOD, 5),
    MERCENARY_POST("mercenarypost", 250, Item.WOOD, 10),
    BARRACKS("barracks", 250, Item.STONE, 15),
    ENGINEER_GUILD("engineerguild", 100, 250, Item.WOOD, 10, 0),
    MARKET("market",250, Item.WOOD, 5, 1);

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

    // multi item producers:

    BuildingType(String name, int cost, int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount, Item rawMaterial,
                 int rawMaterialUsesForFirst, int rawMaterialUsesForSecond, Item firstProduct, Item secondProduct,
                 int productProvides, int workersNeeded) {
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
    }

    // Free processing:
    BuildingType(String name, int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount, Item rawMaterial,
                 int rawMaterialUses, Item product, int productProvides, int workersNeeded) {
        this(name,0, maxHitpoints, buildingMaterial, buildingMaterialAmount, rawMaterial, rawMaterialUses,
                product, productProvides, workersNeeded);
    }

    // Free non rawMaterialNeeded processing:
    BuildingType(String name, int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount, Item product,
                 int productProvides, int workersNeeded) {
        this(name, maxHitpoints, buildingMaterial, buildingMaterialAmount, null, 0, product,
                productProvides, workersNeeded);
    }

    // Free one provider non rawMaterialNeeded processing:
    BuildingType(String name, int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount, Item product,
                 int workersNeeded) {
        this(name, maxHitpoints, buildingMaterial, buildingMaterialAmount, null, 0, product,
                1, workersNeeded);
    }

    // Non processing:
    BuildingType(String name, int cost, int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount,
                 int workersNeeded) {
        this(name, cost, maxHitpoints, buildingMaterial, buildingMaterialAmount, null, 0, null,
                0, workersNeeded);
    }

    // Free non processing:
    BuildingType(String name, int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount, int workersNeeded) {
        this(name,0, maxHitpoints, buildingMaterial, buildingMaterialAmount, workersNeeded);
    }

    // Free non workerNeeded non processing:
    BuildingType(String name, int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount) {
        this(name, maxHitpoints, buildingMaterial, buildingMaterialAmount, 0);
    }

    // Main constructor:
    BuildingType(String name, int cost, int maxHitpoints, Item buildingMaterial, int buildingMaterialAmount,
                 Item rawMaterial, int rawMaterialUses, Item product, int productProvides, int workersNeeded) {
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
    }

    public static BuildingType getBuildingTypeByName(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (Exception ignored) {
            return null;
        }
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

    @Override
    public String toString() {
        return this.name();
    }
}