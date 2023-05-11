package model.people;

import model.buildings.Building;
import model.game.Movable;
import model.game.Tile;

public class Person implements Movable {

    private int hitpoints;
    private Building workplace;
    private int speed;
    private Tile location;

    public Person(){
        // TODO: set default hitpoints!
        this.workplace = null;
    }

    public Person(int speed){
        this.workplace = null;
        // TODO: handle default people speed
        this.speed = 10;
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

    // TODO: it's a dummy teleport! has to move according to speed!
    public void move(Tile destination){
        this.location = destination;
        destination.addPerson(this);
    }
}
