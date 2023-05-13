package model.buildings;

public enum DefensiveBuildingType {

    // TODO: set real capacity for them!
    // TODO: set real damage & range for them!
    // TODO: these should only be placed in own territory!

    KEEP(null, 1, 0, 0, 0, 16),
    WALL("wall", 250, 1, 0, 0, 1),
    STAIRS("stairs", 150, 1, 0, 0, 1),
    SMALL_GATEHOUSE("small gatehouse", 350, 10, 0, 0, 8),
    LARGE_GATEHOUSE("large gatehouse", 450, 20, 0, 0, 12),
    // TODO: decide if they need a seperated class or category field or else!
    LOOKOUT_TOWER("lookout tower", 250, 10, -1, -1, -1),
    PERIMETER_TOWER("perimeter tower", 350, 10, -1, -1, -1),
    DEFENCE_TOWER("defence tower", 350, 15, -1, -1, -1),
    SQUARE_TOWER("square tower", 450, 35, -1, -1, -1),
    ROUND_TOWER("round tower", 450, 40, -1, -1, -1),
    ;
    private final String name;
    private final int cost;
    private final int maxHitpoints;
    private final int stoneAmount;
    private final int damage;
    private final int range;
    private final int capacity;
    private final boolean isRepairable;

    DefensiveBuildingType(String name, int maxHitpoints, int stoneAmount, int damage, int range, int capacity) {
        this.name = name;
        this.cost = 0;
        this.maxHitpoints = maxHitpoints;
        this.stoneAmount = stoneAmount;
        this.damage = damage;
        this.range = range;
        this.capacity = capacity;
        this.isRepairable = true;
    }

    public static DefensiveBuildingType getDefensiveBuildingTypeByName(String name) {
        for (DefensiveBuildingType type : values())
            if (type.getName().equals(name))
                return type;
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

    public int getStoneAmount() {
        return this.stoneAmount;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getRange() {
        return this.range;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public boolean isRepairable() {
        return this.isRepairable;
    }
}
