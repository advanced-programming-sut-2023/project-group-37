package model.people;

import model.game.Item;

enum Nation {EUROPEAN, ARABIAN}

public enum TroopType {

    // TODO: check canClimb out of the source more accurately!

    // EUROPEAN:
    ARCHER(12, 45, 40, 90, 10, Nation.EUROPEAN, Item.BOW, null,
            true, true),
    SPEARMAN(8,50, 50, 60, 1, Nation.EUROPEAN, Item.SPEAR, null,
            true,true),
    MaceMan(20,70, 75, 85, 1, Nation.EUROPEAN, Item.MACE, Item.LEATHER_ARMOR,
            true,true),
    CROSSBOWMAN(20,75, 70, 55, 8, Nation.EUROPEAN, Item.CROSSBOW, Item.LEATHER_ARMOR,
            false,false),
    PIKEMAN(20, 77, 76, 60, 1, Nation.EUROPEAN, Item.PIKE, Item.METAL_ARMOR,
            false, true),
    SWORDSMAN(40, 94,94, 40, 1, Nation.EUROPEAN, Item.SWORD, Item.METAL_ARMOR,
            false, false),
    // TODO: define horse!
    KNIGHT(40, 90, 94, 95, 1, Nation.EUROPEAN, Item.SWORD, Item.METAL_ARMOR,
            false,false),

    // ARABIAN:
    // TODO: some free weapons may be needed!
    ARABIAN_ARCHER(75, 45,45,90, 12, Nation.ARABIAN, null,null,
            false, true),
    SLAVE(5, 20,10,90, 1, Nation.ARABIAN, null,null,
            false, true),
    SLINGER(12, 20,36,90, 6, Nation.ARABIAN, null,null,
            false, false),
    HORSE_ARCHER(80, 55,50,95, 12, Nation.ARABIAN, null,null,
            false, false),
    ARABIAN_SWORDSMAN(80, 88,88,40, 1, Nation.ARABIAN, null,null,
            false, false),
    ASSASSIN(60, 73,76,67, 1, Nation.ARABIAN, null,null,
            false, false),
    FIRE_THROWER(100, 60,84,60, 4, Nation.ARABIAN, null,null,
            false, false);

    private final int cost;
    private final int maxHitpoints;
    private final int  damage;
    private final int range;
    private final int speed;
    private final Nation nation;
    private final Item weapon;
    private final Item armor;
    private final boolean canClimbLadder;
    private final boolean canDigMoat;

    public static boolean isEuropean(String type) {
        return valueOf(type.toUpperCase()).nation == Nation.EUROPEAN;
    }

    public static boolean isArabian(String type) {
        return valueOf(type.toUpperCase()).nation == Nation.ARABIAN;
    }

    TroopType(int cost, int maxHitpoints, int damage, int speed, int range, Nation nation, Item weapon, Item armor,
              boolean canClimbLadder, boolean canDigMoat) {
        this.cost = cost;
        this.maxHitpoints = maxHitpoints;
        this.damage = damage;
        this.range = range;
        this.speed = speed;
        this.nation = nation;
        this.weapon = weapon;
        this.armor = armor;
        this.canClimbLadder = canClimbLadder;
        this.canDigMoat = canDigMoat;
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

    public Item getArmor(){
        return this.armor;
    }

    public boolean canClimbLadder() {
        return this.canClimbLadder;
    }

    public boolean canDigMoat() {
        return this.canDigMoat;
    }
}