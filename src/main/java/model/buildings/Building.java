package model.buildings;

import model.game.Government;
import model.game.Tile;
import model.people.Person;

import java.util.ArrayList;

public class Building {
    private final Government loyalty;
    private final Tile location;
    private final BuildingType type;
    private int hitpoints;
    private final ArrayList<Person> operators;

    // This ctor is for other classes of building!
    public Building(Government loyalty, Tile location) {
        this.loyalty = loyalty;
        this.location = location;
        this.type = null;
        this.hitpoints = 0;
        this.operators = null;
    }

    public Building(Government loyalty, Tile location, BuildingType type) {
        this.loyalty = loyalty;
        this.location = location;
        this.type = type;
        this.hitpoints = type.getMaxHitpoints();
        if (type.getWorkersNeeded() != 0)
            this.operators = new ArrayList<>();
        else
            this.operators = null;
    }

    public Government getLoyalty() {
        return this.loyalty;
    }

    public Tile getLocation() {
        return this.location;
    }

    public BuildingType getType() {
        return this.type;
    }

    public int getMaxHitpoints() {
        if (this instanceof DefensiveBuilding defensiveBuilding) {
            return defensiveBuilding.getDefensiveType().getMaxHitpoints();
        }
        return this.type.getMaxHitpoints();
    }

    public int getHitpoints() {
        return this.hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    public void assignOperator(Person operator) {
        this.operators.add(operator);
    }

    public boolean isFull() {
        try {
            return this.operators.size() == this.type.getWorkersNeeded();
        } catch (Exception ignored) {
            return false;
        }
    }

    public void takeDamage(int amount) {
        int damage = Math.min(amount, this.hitpoints);
        this.hitpoints -= damage;
    }

    public void destroy() {
        if (this.operators != null)
            this.getLoyalty().addPeasant(operators.size());

        this.location.removeBuilding();
        this.location.removeMilitaryUnits();
        this.getLoyalty().getBuildings().remove(this);
    }

}