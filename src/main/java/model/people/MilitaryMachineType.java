package model.people;

import java.util.ArrayList;

public enum MilitaryMachineType {
    ;
    private final int maxHitpoints;
    private final int damage;
    private final int range;
    private final int speed;
    private final int engineersNeeded;
    private final ArrayList<Troop> operators;

    MilitaryMachineType(int maxHitpoints, int damage, int range , int speedQuality, int engineersNeeded) {
        this.maxHitpoints = maxHitpoints;
        this.damage = damage;
        this.range = range;
        this.speed = speedQuality;
        this.engineersNeeded = engineersNeeded;
        this.operators = new ArrayList<>();
    }

    public int getMaxHitpointsQuality() {
        return this.maxHitpoints;
    }

    public int getAttackingDamageQuality() {
        return this.damage;
    }

    public int getRange() {
        return this.range;
    }

    public int getSpeedQuality() {
        return this.speed;
    }

    public int getEngineersNeeded() {
        return this.engineersNeeded;
    }

    public ArrayList<Troop> getOperators() {
        return this.operators;
    }
}