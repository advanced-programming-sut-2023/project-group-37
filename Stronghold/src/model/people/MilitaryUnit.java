package model.people;

import model.game.Government;

public abstract class MilitaryUnit {

    private final Government loyalty;
    private int hitpoints;
    private final int attackingDamage;
    private final int defencingDamage;
    private final int range;
    private final int speed;

    // TODO: do they need location?

    public MilitaryUnit(Government loyalty, Quality hitpointsQuality, Quality attackingDamageQuality, Quality defencingDamageQuality,
                        int range, Quality speedQuality) {
        this.loyalty = loyalty;
        this.hitpoints = Quality.getHitpointsByQuality(hitpointsQuality);
        this.attackingDamage = Quality.getAttackingDamageByQuality(attackingDamageQuality);
        this.defencingDamage = Quality.getDefencingDamageByQuality(defencingDamageQuality);
        this.range = range;
        this.speed = Quality.getSpeedByQuality(speedQuality);
    }

    public Government getLoyalty() {
        return this.loyalty;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    public int getAttackingDamage() {
        return attackingDamage;
    }

    public int getDefencingDamage() {
        return defencingDamage;
    }

    public int getRange() {
        return this.range;
    }

    public int getSpeed() {
        return speed;
    }
}
