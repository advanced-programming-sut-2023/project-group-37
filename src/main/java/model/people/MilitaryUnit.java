package model.people;

import model.game.Government;
import model.game.Tile;

import java.util.LinkedList;

public abstract class MilitaryUnit {

    private final Government loyalty;
    private int hitpoints;
    private final int damage;
    private final int range;

    // According to this.stance
    private int reaction_range;
    private final int speed;
    private Tile location;
    private LinkedList<Tile> route;
    private LinkedList<Tile> patrolRoute;
    private Tile target;
    private MilitaryUnitStance stance;

    public MilitaryUnit(Government loyalty, Tile location, int hitpoints, int damage, int range, int speed) {
        this.loyalty = loyalty;
        this.location = location;
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

    public int getRange() {
        return this.range;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void attack() {
        target.addReceivedDamageInTurn(this.damage);
    }

    public int takeDamage(int damage) {
        int receivedDamage = Math.min(damage, this.hitpoints);
        hitpoints -= receivedDamage;

        return damage - receivedDamage;
    }

    public void dieAndRemove() {
        if (this.hitpoints < 1)
        {
            this.loyalty.getMilitaryUnits().remove(this);
            this.location.getMilitaryUnits().remove(this);
        }
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

    public void move() {
        if (route.size() > 1) {
            LinkedList<Tile> route = this.getRoute();
            int speed = this.getSpeed();
            if (route.size() - 1 < speed)
                speed = route.size() - 1;

            this.location.getMilitaryUnits().remove(this);
            this.location = route.get(speed);
            this.location.addMilitaryUnit(this, 1);

            if (speed > 0)
                route.subList(0, speed).clear();
        }
    }

    public void setRoute(LinkedList<Tile> route) {
        this.route = route;
    }

    public void setPatrol(LinkedList<Tile> patrolRoute) {
        this.patrolRoute = patrolRoute;
    }

    public void setTarget(Tile target) {
        this.target = target;
    }

    protected LinkedList<Tile> getRoute() {
        return this.route;
    }

    public boolean isOnMove() {
        return this.route.size() > 1;
    }

    public boolean isOnPatrol() {
        if (isOnMove())
            return false;

        return this.patrolRoute.size() > 1;
    }

    public void patrol() {

    }
}