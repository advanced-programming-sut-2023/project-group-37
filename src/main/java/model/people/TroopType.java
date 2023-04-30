package model.people;

import model.game.Item;

enum Nation {EUROPEAN, ARABIAN}

public enum TroopType {

    // TODO: check canClimb out of the source more accurately!

    // EUROPEAN:
    ARCHER(12, 45, 40, 90, 18, Nation.EUROPEAN, Item.BOW, null,
            true, true),
    SPEARMAN(8,50, 50, 60, 1.5, Nation.EUROPEAN, Item.SPEAR, null,
            true,true),
    MaceMan(20,70, 75, 85, 1.5, Nation.EUROPEAN, Item.MACE, Item.LEATHER,
            false,false),
    CROSSBOWMAN(20,75, 70, 55, 16, Nation.EUROPEAN, Item.MACE, Item.LEATHER,
            false,false),
    PIKEMAN(20, 77, 76, 60, 1.5, Nation.EUROPEAN, Item.PIKE, Item.ARMOR,
            false, true),
    SWORDSMAN(40, 94,94, 40, 1.5, Nation.EUROPEAN, Item.SWORD, Item.ARMOR,
            false, false),
    // TODO: define horse
    KNIGHT(40, 90, 94, 95, 1.5, Nation.EUROPEAN, Item.SWORD, Item.ARMOR,
            false,false),    ;

    private int cost;
    private final int maxHitpoints;
    private final int  damage;
    private final double range;
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

    TroopType(int cost, int maxHitpoints, int damage, int speed, double range, Nation nation, Item weapon, Item armor,
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

    public double getRange() {
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