package model.game;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.DefensiveBuilding;
import model.people.MilitaryUnit;
import model.people.Person;
import model.people.Troop;
import model.people.TroopType;

import java.util.ArrayList;

public class Tile extends Rectangle {

    private final int x;
    private final int y;
    private Texture texture;
    private final ArrayList<Person> people;
    private ArrayList<MilitaryUnit> militaryUnits;
    private Building building;
    private Character state;
    private boolean isPassable;
    private Territory territory;
    public int number;
    private boolean hasBuilding;

    public Tile(int x, int y) {
        super(20, 20);
        this.x = x;
        this.y = y;
        this.texture = Texture.GROUND;
        this.setFill(new ImagePattern(this.texture.getImage()));

        this.people = new ArrayList<>();
        this.militaryUnits = new ArrayList<>();
        this.building = null;
        this.isPassable = true;
        this.hasBuilding = false;
    }

    public int getLocationX() {
        return x;
    }

    public int getLocationY() {
        return y;
    }

    public Texture getTexture() {
        return texture;
    }

    public void changeTexture(Texture texture) {
        this.texture = texture;
        this.setFill(new ImagePattern(this.texture.getImage()));
    }

    public ArrayList<MilitaryUnit> getMilitaryUnits() {
        return militaryUnits;
    }

    public Building getBuilding() {
        if (!this.hasBuilding)
            return null;

        return building;
    }

    public void receiveDamage(int damage, Government government) {
        int firstDamage = damage;
        for (MilitaryUnit militaryUnit : militaryUnits) {
            if (militaryUnit.getLoyalty() != government) {
                damage -= militaryUnit.takeDamage(damage);
                if (damage < 1)
                    break;
            }
        }

        if (damage == firstDamage && this.building != null ) {
            if (!(this.building instanceof DefensiveBuilding) && this.building.getType() != BuildingType.KILLING_PIT &&
                    this.building.getLoyalty() != government)
                this.building.takeDamage(damage);
        }
    }

    public void receiveBuildingDamage(int damage, Government government) {
        if (this.building instanceof DefensiveBuilding && this.building.getType() != BuildingType.KILLING_PIT &&
                this.building.getLoyalty() != government)
            this.building.takeDamage(damage);
    }

    public void removeBuilding() {
        this.hasBuilding = false;
        this.building = null;
    }

    public void setBuilding(Building building) {
        this.building = building;
        this.hasBuilding = true;
    }

    public Character getState() {
        return state;
    }

    public boolean isPassable() {
        return isPassable;
    }

    public Territory getTerritory() {
        return this.territory;
    }

    public void setState() {
        boolean hasLord = false;
        for (MilitaryUnit militaryUnit : this.militaryUnits) {
            if (militaryUnit instanceof Troop troop) {
                if (troop.getType() == TroopType.LORD) {
                    hasLord = true;
                    break;
                }
            }
        }
        if (hasLord)
            this.state = 'L';

        else if (this.militaryUnits.size() > 0) {
            for (MilitaryUnit militaryUnit : this.militaryUnits) {
                if (militaryUnit.isOnMove() || militaryUnit.isOnPatrol()) {
                    this.state = 'M';
                    return;
                }
            }
            this.state = 'S';
        } else if (this.hasBuilding) {
            if (this.building instanceof DefensiveBuilding)
                this.state = 'W';
            else this.state = 'B';
        }

        else if (this.texture == Texture.OLIVE_TREE || texture == Texture.DESERT_TREE) {
            this.state = 'T';
        } else this.state = 'N';
    }

    public ArrayList<Person> getPeople() {
        return this.people;
    }

    public void addPerson(Person person) {
        this.people.add(person);
    }

    public void addMilitaryUnit(MilitaryUnit troop){
        this.militaryUnits.add(troop);
    }

    public void setPassability(boolean passability) {
        isPassable = passability;
    }

    public boolean isTotallyEmpty() {
        return this.people.isEmpty() && this.militaryUnits.isEmpty() && this.getBuilding() == null;
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

    public void setTerritory(Territory territory) {
        this.territory = territory;
    }

    public void removeMilitaryUnits() {
        militaryUnits = new ArrayList<>();
    }

    public void changeMoat() {
        if (texture == Texture.MOAT) {
            texture = Texture.GROUND;
            this.isPassable = true;
        }
        else {
            texture = Texture.MOAT;
            this.isPassable = false;
        }
    }

    public boolean hasBuilding() {
        return hasBuilding;
    }
}