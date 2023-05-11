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
    private final ArrayList<MilitaryUnit> militaryUnits;
    private Building building;
    private Character state;
    private boolean isPassable = true;
    private Government territory;
    private int territoryNumber;
    public int number; // it is just for rootFinder



    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.texture = Texture.GROUND;
        this.people = new ArrayList<>();
        this.militaryUnits = new ArrayList<>();
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

    public ArrayList<MilitaryUnit> getMilitaryUnits() {
        return militaryUnits;
    }

    public Building getBuilding() {
        return building;
    }
    public void removeBuilding(){
        this.building = null;
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

    public Government getTerritory() {
        return this.territory;
    }
    public int getTerritoryNumber() {
        return territoryNumber;
    }

    public void setTerritory(Government territory) {
        this.territory = territory;
    }
    public void setTerritoryNumber(int territoryNumber) {
        this.territoryNumber = territoryNumber;
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

        //  todo : if (texture == harchi) state = yechi;
    }

    public void addPerson(Person person) {
        this.people.add(person);
    }

    public void addMilitaryUnit(MilitaryUnit troop, int count) {
        for (int i = 0; i < count; i++)
            militaryUnits.add(troop);
    }

    public void setPassability(boolean passability) {
        isPassable = passability;
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