package model.people.enums;

import model.enums.Weapons;

import java.util.ArrayList;
import java.util.Collections;

public enum Troops {

    // Arab Troops (Don't have weapons!):

    // European Troops (have weapon[s]!):
    ;
    private final TroopRace race;
    private final int speed;
    private final int maxHitPoints;
    private final int attackingDamage;
    private final int defencingDamage;

    private final ArrayList<Weapons> weapons;

    Troops(int speed, int maxHitPoints, int attackingDamage, int defencingDamage) {
        this.race = TroopRace.ARAB;
        this.speed = speed;
        this.maxHitPoints = maxHitPoints;
        this.attackingDamage = attackingDamage;
        this.defencingDamage = defencingDamage;
        this.weapons = null;
    }

    Troops(int speed, int maxHitPoints, int attackingDamage, int defencingDamage, Weapons... weapons) {
        this.race = TroopRace.EUROPEAN;
        this.speed = speed;
        this.maxHitPoints = maxHitPoints;
        this.attackingDamage = attackingDamage;
        this.defencingDamage = defencingDamage;
        this.weapons = new ArrayList<>();
        Collections.addAll(this.weapons, weapons);
    }

    public TroopRace getRace() {
        return this.race;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getMaxHitPoints() {
        return this.maxHitPoints;
    }

    public int getAttackingDamage() {
        return this.attackingDamage;
    }

    public int getDefencingDamage() {
        return this.defencingDamage;
    }

    public ArrayList<Weapons> getWeapons() {
        return this.weapons;
    }
}