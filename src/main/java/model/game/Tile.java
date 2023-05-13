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
    private boolean isPassable;
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
        this.isPassable = true;
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

    public void receiveDamage(int damage, Government government) {
        for (MilitaryUnit militaryUnit : militaryUnits) {
            if (militaryUnit.getLoyalty() != government) {
                damage -= militaryUnit.takeDamage(damage);
                if (damage < 1)
                    break;
            }
        }

        if (damage > 0 && this.building != null ) { // todo : buildingType
            this.building.takeDamage(damage);
        }
    }

    public void removeBuilding() {
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
        if (this.militaryUnits.size() > 0) {
            for (MilitaryUnit militaryUnit : this.militaryUnits) {
                if (militaryUnit.isOnMove() || militaryUnit.isOnPatrol()) {
                    state = 'M';
                    return;
                }
            }
            state = 'S';
        }

        else if (this.building != null) {
            this.state = 'B';
        }
        //todo : W for wall
        else if (this.texture == Texture.OLIVE_TREE || texture == Texture.DESERT_TREE) {
            this.state = 'T';
        }

        else this.state = 'N';
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

    public boolean hasEnemy(Government government) {
        for (MilitaryUnit militaryUnit : this.militaryUnits) {
            if (militaryUnit.getLoyalty() != government)
                return true;
        }
        return false;
    }

    public boolean equals(Tile tile) {
        return (this.x == tile.x && this.y == tile.y);
    }
}