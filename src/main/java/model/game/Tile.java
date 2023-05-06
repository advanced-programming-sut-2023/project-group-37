package model.game;

import model.buildings.Building;
import model.people.MilitaryUnit;
import model.people.Person;

import java.util.ArrayList;

public class Tile {
    private final int x;
    private final int y;
    private Texture texture;

    private final ArrayList<Person> people;
    private final ArrayList<MilitaryUnit> militaryUnits;
    private Building building;
    private Character state;
    public boolean canCross = true;
    private Government territory;

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

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Character getState() {
        return state;
    }

    public void setState() {
        //todo
    }

    public void addPerson(Person person){
        this.people.add(person);
    }

    public void addMilitaryUnit(MilitaryUnit unit){
        this.militaryUnits.add(unit);
    }

    public ArrayList<Person> getPeople() {
        return people;
    }


    public boolean equals(Tile tile) {
        return (this.x == tile.x && this.y == tile.y);
    }
}