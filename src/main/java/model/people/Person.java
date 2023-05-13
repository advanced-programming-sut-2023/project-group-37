package model.people;

import model.buildings.Building;
import model.game.Government;
import model.game.Tile;

public class Person {

    private final Government loyalty;
    private int hitpoints;
    private Building workplace;
    private final int speed;
    private Tile location;

    public Person(Government loyalty) {
        this.loyalty = loyalty;
        this.hitpoints = 10;
        this.workplace = null;
        this.speed = 70;
        this.location = loyalty.getTerritory().getVillage();
    }

    public int getHitpoints() {
        return this.hitpoints;
    }

    public Building getWorkplace() {
        return this.workplace;
    }

    public void setWorkplace(Building workplace) {
        this.workplace = workplace;
        this.location = workplace.getLocation();
    }

    // TODO: handle workSpace destruction!

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
