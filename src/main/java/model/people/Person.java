package model.people;

import model.buildings.Building;
import model.game.Tile;

public class Person {

    private int hitpoints;
    private Building workplace;
    private int speed;
    private Tile location;

    public Person() {
        // TODO: set default hitpoints!
        this.workplace = null;
    }

    public int getHitpoints() {
        return this.hitpoints;
    }

    public Building getWorkplace() {
        return this.workplace;
    }

    public void setWorkplace(Building workplace) {
        this.workplace = workplace;

    }

    public int getSpeed() {
        return this.speed;
    }

    public Tile getLocation() {
        return this.location;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

}
