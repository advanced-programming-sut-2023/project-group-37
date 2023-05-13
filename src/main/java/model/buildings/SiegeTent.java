package model.buildings;

import model.game.Government;
import model.game.Tile;
import model.people.MilitaryMachineType;

public class SiegeTent extends Building{

    private final MilitaryMachineType formingMachine;

    public SiegeTent(Government loyalty, Tile location, MilitaryMachineType type) {
        super(loyalty, location, BuildingType.SIEGE_TENT);
        this.formingMachine = type;
    }

    public MilitaryMachineType getFormingMachine() {
        return this.formingMachine;
    }
}
