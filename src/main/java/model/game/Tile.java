package model.game;

import model.buildings.Building;
import model.people.MilitaryUnit;
import model.people.Person;

import java.util.ArrayList;
import java.util.HashMap;

public class Tile {
    private final int x;
    private final int y;
    private Texture texture;

    private final ArrayList<Person> people;
    private final HashMap<MilitaryUnit, Integer> militaryUnits;
    private Building building;
    private Character state;
    private boolean isPassable = true;
    private Government territory;
    public int number; // it is just for rootFinder

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.texture = Texture.GROUND;
        this.people = new ArrayList<>();
        this.militaryUnits = new HashMap<>();
        this.building = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Texture getTexture() {
        return texture;
    }

    public void changeTexture(Texture texture) {
        this.texture = texture;
    }

    public HashMap<MilitaryUnit, Integer> getMilitaryUnits() {
        return militaryUnits;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Character getState() {
        return state;
    }

    public boolean isPassable() {
        return isPassable;
    }

    public void setState() {
        if (militaryUnits.size() > 0) {
            state = 'S';
            return;
        }

        if (building != null) {
            state = 'B';
            return;
        }
        //todo : W for wall
        if (texture == Texture.OLIVE_TREE || texture == Texture.DESERT_TREE) {
            state = 'T';
            return;
        }

//  todo :       if (texture == harchi) state = yechi;
    }

    public void addPerson(Person person) {
        this.people.add(person);
    }

    public void addMilitaryUnit(MilitaryUnit troop, int count) {
        this.militaryUnits.put(troop, count + militaryUnits.getOrDefault(troop, 0));
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public String showDetails() {
        StringBuilder message = new StringBuilder();
        message.append("");


        return message.toString().trim();
    }

    public boolean equals(Tile tile) {
        return (this.x == tile.x && this.y == tile.y);
    }
}