package model.people;

import model.buildings.Building;

public class Person {

    private int hitpoints;
    private boolean isWorking;
    private Building workplace;

    // TODO: add field of workplace!
    // TODO: add field of location!
    // TODO: do they have speed?

    public Person(){
        // TODO: set default hitpoints!
        this.isWorking = false;
        this.workplace = null;
    }

    public int getHitpoints() {
        return this.hitpoints;
    }

    public boolean isWorking() {
        return this.isWorking;
    }

    public Building getWorkplace() {
        return this.workplace;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }
}
