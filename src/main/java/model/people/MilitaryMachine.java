package model.people;

import model.game.Government;
import model.game.Tile;

import java.util.ArrayList;

public class MilitaryMachine extends MilitaryUnit{

    private final MilitaryMachineType type;
    private int hitpoints;
    private final ArrayList<Troop> operators;

    public MilitaryMachine(Government loyalty, MilitaryMachineType type, Tile location) {
        super(loyalty, location,type.getMaxHitpoints(), type.getDamage(), type.getRange(), type.getSpeed());
        this.type = type;
        this.hitpoints = this.type.getMaxHitpoints();
        this.operators = new ArrayList<>();
    }

    public MilitaryMachineType getType() {
        return this.type;
    }

    public ArrayList<Troop> getOperators() {
        return this.operators;
    }

    public void assignOperator(Troop operator){
        if(this.operators.size() < this.type.getOperatorsNeeded())
            this.operators.add(operator);
    }
}