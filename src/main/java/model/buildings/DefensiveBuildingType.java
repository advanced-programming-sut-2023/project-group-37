package model.buildings;

public enum DefensiveBuildingType {

    KEEP(null, 1, 0, 0, 16),
    WALL("wall", 250, 1, 1.1, 1),
    STAIRS("stairs", 150, 1, 0, 1),
    SMALL_GATEHOUSE("small gatehouse", 350, 10, 1.2, 8),
    LARGE_GATEHOUSE("large gatehouse", 450, 20, 1.4, 12),
    LOOKOUT_TOWER("lookout tower", 250, 10, 2.2, 4),
    PERIMETER_TOWER("perimeter tower", 350, 10, 1.6, 6),
    DEFENCE_TOWER("defence tower", 350, 15, 1.6, 6),
    SQUARE_TOWER("square tower", 450, 35, 2, 10),
    ROUND_TOWER("round tower", 450, 40, 1.8, 10),
    ;
    private final String name;
    private final int cost;
    private final int maxHitpoints;
    private final int stoneAmount;
    private final double rangeRate;
    private final int capacity;
    private final boolean isRepairable;

    DefensiveBuildingType(String name, int maxHitpoints, int stoneAmount, double rangeRate, int capacity) {
        this.name = name;
        this.cost = 0;
        this.maxHitpoints = maxHitpoints;
        this.stoneAmount = stoneAmount;
        this.rangeRate = rangeRate;
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

    public double getRangeRate() {
        return this.rangeRate;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public boolean isRepairable() {
        return this.isRepairable;
    }
}
