package model.people;

import model.game.Government;
import model.game.Movable;
import model.game.Tile;

public abstract class MilitaryUnit implements Movable {

    private final Government loyalty;
    private int hitpoints;
    private final int damage;
    private final double range;
    private final int speed;
    private Tile location;

    public MilitaryUnit(Government loyalty, int hitpoints, int damage, double range, int speed) {

        this.loyalty = loyalty;
        this.hitpoints = hitpoints;
        this.damage = damage;
        this.range = range;
        this.speed = speed;
    }

    public Government getLoyalty() {
        return this.loyalty;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    public int getDamage() {
        return this.damage;
    }

    public double getRange() {
        return this.range;
    }

    public int getSpeed() {
        return this.speed;
    }

    public Tile getLocation() {
        return this.location;
    }

    public void move(Tile destination) {
        this.location = destination;
        destination.addMilitaryUnit(this);
    }
}