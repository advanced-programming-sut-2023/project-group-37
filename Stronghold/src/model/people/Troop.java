package model.people;

import model.Government;
import model.Tile;
import model.enums.Weapons;
import model.people.enums.Troops;

import java.util.ArrayList;

public class Troop extends Person {

    private final ArrayList<Weapons> weapons;
    private static ArrayList<Troop> unit = new ArrayList<>();


    public Troop(Government government, Troops troop) {
        super(government, troop.getSpeed(), troop.getMaxHitPoints());
        this.weapons = troop.getWeapons();
    }

    public void setUnit(ArrayList<Troop> unit) {
        Troop.unit = unit;
    }

    public ArrayList<Troop> getUnit() {
        return unit;
    }

    public static void airAttack(Tile tile) {

    }
}
