package model.people;

import model.buildings.Building;
import model.game.Movable;
import model.game.Tile;

public class Person implements Movable {

    private int hitpoints;
    private boolean isWorking;
    private Building workplace;
    private int speed;

    private Tile location;

    public Person(){
        // TODO: set default hitpoints!
        this.isWorking = false;
        this.workplace = null;
    }

    public Person(int speed){
        this.isWorking = false;
        this.workplace = null;
        // TODO: handle default people speed
        this.speed = Quality.getSpeedByQuality(Quality.MEDIUM);
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
