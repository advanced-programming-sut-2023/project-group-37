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
    private int receivedDamageInTurn;

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

    public int getReceivedDamageInTurn() {
        return receivedDamageInTurn;
    }

    public void addReceivedDamageInTurn(int damage) {
        this.receivedDamageInTurn += damage;
    }

    public void receiveDamage() {
        for (MilitaryUnit militaryUnit : militaryUnits) {
            receivedDamageInTurn -= militaryUnit.takeDamage(receivedDamageInTurn);
            if (receivedDamageInTurn < 1)
                break;
        }
        for (MilitaryUnit militaryUnit : militaryUnits) {
            militaryUnit.dieAndRemove();
        }

        if (receivedDamageInTurn > 0 && this.building != null) {
            this.building.takeDamage(receivedDamageInTurn);
        }
        receivedDamageInTurn = 0;
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
        if (militaryUnits.size() > 0) {
            state = 'S';
        }

        else if (building != null) {
            state = 'B';
        }
        //todo : W for wall
        else if (texture == Texture.OLIVE_TREE || texture == Texture.DESERT_TREE) {
            state = 'T';
        }

        //  todo : if (texture == harchi) state = yechi;
        else state = 'N';
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