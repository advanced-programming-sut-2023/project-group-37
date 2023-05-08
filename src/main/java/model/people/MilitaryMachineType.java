package model.people;

public enum MilitaryMachineType {

    PORTABLE_SHIELD(5, 120, 0, 0, 80, 1),
    BATTERING_RAM(150, 500, 200, 1, 45, 40),
    SIEGE_TOWER(150, 120, 0, 1, 50, 4),
    CATAPULT(150, 80, 100, 35, 45, 2),
    TREBUCHET(150, 80, 125, 45, 0, 3),
    FIRE_BALLISTA(150, 75, 40, 30, 30, 2),
    ;
    private final int cost;
    private final int maxHitpoints;
    private final int damage;
    private final int range;
    private final int speed;
    private final int operatorsNeeded;

    MilitaryMachineType(int cost, int maxHitpoints, int damage, int range, int speed, int engineersNeeded) {
        this.cost = cost;
        this.maxHitpoints = maxHitpoints;
        this.damage = damage;
        this.range = range;
        this.speed = speed;
        this.operatorsNeeded = engineersNeeded;
    }

    public static MilitaryMachineType getByName(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (Exception ignored) {
            return null;
        }
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

    public int getOperatorsNeeded() {
        return this.operatorsNeeded;
    }
}