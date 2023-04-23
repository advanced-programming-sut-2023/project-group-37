package model.people;

import model.game.Government;

public class MilitaryMachine extends MilitaryUnit{
    private final MilitaryMachineType type;

    public MilitaryMachine(Government loyalty, MilitaryMachineType type) {
        super(loyalty, type.getMaxHitpointsQuality(), type.getAttackingDamageQuality(), type.getDefencingDamageQuality(),
                type.getRange(), type.getSpeedQuality());
        this.type = type;
    }

    public MilitaryMachineType getType() {
        return this.type;
    }
}
