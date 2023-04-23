package model.people;

import java.util.ArrayList;

public enum MilitaryMachineType {
    ;
    private final Quality maxHitpointsQuality;
    private final Quality attackingDamageQuality;
    private final Quality defencingDamageQuality;
    private final int range;
    private final Quality speedQuality;
    private final int engineersNeeded;
    private final ArrayList<Troop> operators;

    MilitaryMachineType(Quality maxHitpointsQuality, Quality attackingDamageQuality, Quality defencingDamageQuality,
                        int range , Quality speedQuality, int engineersNeeded) {
        this.maxHitpointsQuality = maxHitpointsQuality;
        this.attackingDamageQuality = attackingDamageQuality;
        this.defencingDamageQuality = defencingDamageQuality;
        this.range = range;
        this.speedQuality = speedQuality;
        this.engineersNeeded = engineersNeeded;
        operators = new ArrayList<>();
    }

    public Quality getMaxHitpointsQuality() {
        return this.maxHitpointsQuality;
    }

    public Quality getAttackingDamageQuality() {
        return this.attackingDamageQuality;
    }

    public Quality getDefencingDamageQuality() {
        return this.defencingDamageQuality;
    }

    public int getRange() {
        return this.range;
    }

    public Quality getSpeedQuality() {
        return this.speedQuality;
    }

    public int getEngineersNeeded() {
        return this.engineersNeeded;
    }

    public ArrayList<Troop> getOperators() {
        return this.operators;
    }
}
