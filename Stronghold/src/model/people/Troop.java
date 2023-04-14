package model.people;

import model.Government;
import model.enums.Weapons;
import model.people.enums.TroopType;

import java.util.ArrayList;

public class Troop extends Person {

    private final ArrayList<Weapons> weapons;

    public Troop(Government government, TroopType type) {
        super(government, type.getSpeed(), type.getMaxHitPoints());
        this.weapons = type.getWeapons();
    }
}
