package model.people;

import model.game.Item;

enum Nation {EUROPEAN, ARABIAN}

public enum TroopType {
    ;
    private final Quality maxHitpointsQuality;
    private final Quality attackingDamageQuality;
    private final Quality defencingQuality;
    private final int range;
    private final Quality speedQuality;
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

    TroopType(Quality maxHitpointsQuality, Quality attackingDamageQuality, Quality defencingQuality, Quality speedQuality,
              int range, Nation nation, Item weapon, Item armor, boolean canClimbLadder, boolean canDigMoat) {
        this.maxHitpointsQuality = maxHitpointsQuality;
        this.attackingDamageQuality = attackingDamageQuality;
        this.defencingQuality = defencingQuality;
        this.range = range;
        this.speedQuality = speedQuality;
        this.nation = nation;
        this.weapon = weapon;
        this.armor = armor;
        this.canClimbLadder = canClimbLadder;
        this.canDigMoat = canDigMoat;
    }

    public Quality getMaxHitpointsQuality() {
        return this.maxHitpointsQuality;
    }

    public Quality getAttackingDamageQuality() {
        return this.attackingDamageQuality;
    }

    public Quality getDefencingQuality() {
        return this.defencingQuality;
    }

    public int getRange() {
        return this.range;
    }

    public Quality getSpeedQuality() {
        return this.speedQuality;
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