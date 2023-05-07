package model.people;

import model.game.Government;
import model.game.Movable;
import model.game.Tile;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class MilitaryUnit implements Movable {

    private final Government loyalty;
    private int hitpoints;
    private final int damage;
    private final int range;

    // According to this.stance
    private int reaction_range;
    private final int speed;


    private Tile location;
    private LinkedList<Tile> route;

    private MilitaryUnitStance stance;

    public MilitaryUnit(Government loyalty, int hitpoints, int damage, int range, int speed) {
        this.loyalty = loyalty;
        this.hitpoints = hitpoints;
        this.damage = damage;
        this.range = range;
        this.reaction_range = range;
        this.speed = speed;
        this.stance = MilitaryUnitStance.STANDING;
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

    public int getRange() {
        return this.range;
    }

    public int getSpeed() {
        return this.speed;
    }

    public Tile getLocation() {
        return this.location;
    }

    public MilitaryUnitStance getStance() {
        return this.stance;
    }

    public void setStance(MilitaryUnitStance stance) {
        this.stance = stance;

        if (stance == MilitaryUnitStance.DEFENSIVE)
            this.reaction_range = 2 * this.range;
        else if (stance == MilitaryUnitStance.AGGRESSIVE)
            this.reaction_range = 3 * this.range;
        else
            this.reaction_range = this.range;
    }

    public void move(Tile destination) {
        this.location = destination;
        destination.addMilitaryUnit(this);
    }

    public void setRoute(LinkedList<Tile> route) {
        this.route = route;
    }

    protected LinkedList<Tile> getRoute() {
        return this.route;
    }
}