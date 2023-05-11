package model.buildings;

import model.game.Item;

public enum DefensiveBuildingType {

    // TODO: set real capacity for them!
    // TODO: set real damage & range for them!

    SMALL_GATEHOUSE("smallgatehouse", 0, 350, 10, 0, 0, 8),
    LARGE_GATEHOUSE("largegatehouse", 0, 450, 20, 0, 0, 12),
    // TODO: decide if they need a seperated class or category field or else!
    LOOKOUT_TOWER("lookouttower", 0, 250, 10, -1, -1, -1),
    PERIMETER_TOWER("perimetertower", 0, 350, 10, -1, -1, -1),
    DEFENCE_TOWER("defencetower", 0, 350, 15, -1, -1, -1),
    SQUARE_TOWER("squaretower", 0, 450, 35, -1, -1, -1),
    ROUND_TOWER("roundtower", 0, 450, 40, -1, -1, -1),
    ;
    private final String name;
    private final int cost;
    private final int maxHitpoints;
    private final int stoneAmount;
    private final int damage;
    private final int range;
    private final int capacity;
    private final boolean isRepairable;

    DefensiveBuildingType(String name, int cost, int maxHitpoints, int stoneAmount, int damage, int range, int capacity) {
        this.name = name;
        this.cost = cost;
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
