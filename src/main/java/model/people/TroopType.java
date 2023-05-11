package model.people;

import model.buildings.BuildingType;
import model.game.Item;

public enum TroopType {

    //LORD:
    LORD(null, 0, 100, 100, 55, 1, null, null, false, false),

    // Barracks:
    ARCHER(BuildingType.BARRACKS, 12, 45, 40, 90, 10,
            Item.BOW, null, true, true),
    SPEARMAN(BuildingType.BARRACKS, 8, 50, 50, 60, 1,
            Item.SPEAR, null, true, true),
    MaceMan(BuildingType.BARRACKS, 20, 70, 75, 85, 1,
            Item.MACE, Item.LEATHER_ARMOR, true, true),
    CROSSBOWMAN(BuildingType.BARRACKS, 20, 75, 70, 55, 8,
            Item.CROSSBOW, Item.LEATHER_ARMOR, false, false),
    PIKEMAN(BuildingType.BARRACKS, 20, 77, 76, 60, 1,
            Item.PIKE, Item.METAL_ARMOR, false, true),
    SWORDSMAN(BuildingType.BARRACKS, 40, 94, 94, 40, 1,
            Item.SWORD, Item.METAL_ARMOR, false, false),
    // TODO: define horse!
    KNIGHT(BuildingType.BARRACKS, 40, 90, 94, 95, 1,
            Item.SWORD, Item.METAL_ARMOR, false, false),

    // Mercenary post:
    // TODO: some free weapons may be needed!
    ARABIAN_ARCHER(BuildingType.MERCENARY_POST, 75, 45, 45, 90, 12,
            null, null, false, true),
    SLAVE(BuildingType.MERCENARY_POST, 5, 20, 10, 90, 1,
            null, null, false, true),
    SLINGER(BuildingType.MERCENARY_POST, 12, 20, 36, 90, 6,
            null, null, false, false),
    HORSE_ARCHER(BuildingType.MERCENARY_POST, 80, 55, 50, 95, 12,
            null, null, false, false),
    ARABIAN_SWORDSMAN(BuildingType.MERCENARY_POST, 80, 88, 88, 40, 1,
            null, null, false, false),
    ASSASSIN(BuildingType.MERCENARY_POST, 60, 73, 76, 67, 1,
            null, null, false, false),
    FIRE_THROWER(BuildingType.MERCENARY_POST, 100, 60, 84, 60, 4,
            null, null, false, false),

    // Engineer guild:
    // Hitpoints are set as low as slinger damage!
    ENGINEER(BuildingType.ENGINEER_GUILD, 30, 10, 0, 60, 1,
            null, null, false, false),
    LADDERMAN(BuildingType.ENGINEER_GUILD, 4, 5, 0, 60, 1,
            null, null, false, false);

    // TODO: implement monk!

    private final BuildingType trainingCamp;
    private final int cost;
    private final int maxHitpoints;
    private final int damage;
    private final int range;
    private final int speed;
    private final Item weapon;
    private final Item armor;
    private final boolean canClimbLadder;
    private final boolean canDigMoat;

    TroopType(BuildingType trainingCamp, int cost, int maxHitpoints, int damage, int speed, int range, Item weapon,
              Item armor, boolean canClimbLadder, boolean canDigMoat) {
        this.trainingCamp = trainingCamp;
        this.cost = cost;
        this.maxHitpoints = maxHitpoints;
        this.damage = damage;
        this.range = range;
        this.speed = speed;
        this.weapon = weapon;
        this.armor = armor;
        this.canClimbLadder = canClimbLadder;
        this.canDigMoat = canDigMoat;
    }

    public static TroopType getTroopTypeByName(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (Exception ignored) {
            return null;
        }
    }

    public BuildingType getTrainingCamp() {
        return this.trainingCamp;
    }

    public int getCost() {
        return this.cost;
    }

    public int getMaxHitpoints() {
        return this.maxHitpoints;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getRange() {
        return this.range;
    }

    public int getSpeed() {
        return this.speed;
    }

    public Item getWeapon() {
        return this.weapon;
    }

    public Item getArmor() {
        return this.armor;
    }

    public boolean canClimbLadder() {
        return this.canClimbLadder;
    }

    public boolean canDigMoat() {
        return this.canDigMoat;
    }
}