package model.people;

import model.Government;
import model.enums.Weapons;
import model.people.enums.Troops;

import java.util.ArrayList;

public class Troop extends Person {

    private String name;
    private int attackingDamage;
    private int defensiveDamage;
    private final ArrayList<Weapons> weapons;

    public Troop(Government government, Troops troop) {
        super(government, troop.getSpeed(), troop.getMaxHitPoints());
        this.weapons = troop.getWeapons();
    }
}