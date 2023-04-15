package model.people;

import model.Government;
import model.buildings.Building;
import model.people.enums.Troops;

public class Person {

    private final Government government;
    private final int speed;
    private final int maxHitPoints;

    private final Building workingPlace;

    // default person:
    public Person(Government government){
        this.government = government;
        //TODO: change maxHitPoint & speed!
        this.maxHitPoints = 50;
        this.speed = 50;
        this.workingPlace = null;
    }

    // Unemployed Person.
    public Person(Government government, int speed, int maxHitPoints) {
        this.government = government;
        this.speed = speed;
        this.maxHitPoints = maxHitPoints;
        this.workingPlace = null;
    }

    // Employed Person.
    public Person(Government government, int speed, int maxHitPoints, Building workingPlace) {
        this.government = government;
        this.speed = speed;
        this.maxHitPoints = maxHitPoints;
        this.workingPlace = workingPlace;
    }

    public Government getGovernment() {
        return this.government;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getMaxHitPoints() {
        return this.maxHitPoints;
    }

    public boolean isUnemployed(){
        return this.workingPlace == null;
    }

    public boolean isMilitary(){
        return this instanceof Troop;
    }
}
