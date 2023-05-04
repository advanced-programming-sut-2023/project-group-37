package model.people;

import model.game.Government;
import model.game.Movable;
import model.game.Tile;

import java.util.ArrayList;

public abstract class MilitaryUnit implements Movable {

    private final Government loyalty;
    private int hitpoints;
    private final int attackingDamage;
    private final int defencingDamage;
    private final int range;
    private final int speed;


    private Tile location;
    private ArrayList<Tile> route;


    public MilitaryUnit(Government loyalty, Quality hitpointsQuality, Quality attackingDamageQuality, Quality defencingDamageQuality,
                        int range, Quality speedQuality) {

        this.loyalty = loyalty;
        this.hitpoints = Quality.getHitpointsByQuality(hitpointsQuality);
        this.attackingDamage = Quality.getAttackingDamageByQuality(attackingDamageQuality);
        this.defencingDamage = Quality.getDefencingDamageByQuality(defencingDamageQuality);
        this.range = range;
        this.speed = Quality.getSpeedByQuality(speedQuality);
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

    public int getAttackingDamage() {
        return attackingDamage;
    }

    public int getDefencingDamage() {
        return defencingDamage;
    }

    public int getRange() {
        return this.range;
    }

    public int getSpeed() {
        return speed;
    }

    public Tile getLocation() {
        return this.location;
    }

    public void move(Tile destination){
        this.location = destination;
        destination.addMilitaryUnit(this);
    }

    public void setRoute(ArrayList<Tile> route) {
        this.route = route;
    }

    protected ArrayList<Tile> getRoute() {
        return this.route;
    }
}