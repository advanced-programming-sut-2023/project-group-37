package model.game;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.DefensiveBuilding;
import model.people.*;

import java.util.ArrayList;

public class Tile extends Rectangle {

    private final int x;
    private final int y;
    private Texture texture;
    private Image image;
    private final ArrayList<Person> people;
    private ArrayList<MilitaryUnit> militaryUnits;
    private Building building;
    private boolean isPassable;
    private Territory territory;
    public int number;
    private boolean hasBuilding;

    public Tile(int x, int y) {
        super(20, 20);
        this.x = x;
        this.y = y;
        this.texture = Texture.GROUND;
        this.setImage(this.texture.getImage());

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
        this.updateImage();
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

        if (damage == firstDamage && this.building != null) {
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

    private void setImage(Image inputImage) {
        this.image = inputImage;
        this.setFill(new ImagePattern(inputImage));
    }

    public void setBuilding(Building building) {
        this.building = building;

        if (building != null) {
            this.hasBuilding = true;

            if (building instanceof DefensiveBuilding defensiveBuilding)
                this.setImage(defensiveBuilding.getDefensiveType().getImage());
            else this.setImage(building.getType().getImage());
        }

        else this.hasBuilding = false;
    }

    public void updateImage() {
        if (this.militaryUnits.size() > 0) {
            for (MilitaryUnit militaryUnit : this.militaryUnits) {
                if (militaryUnit instanceof MilitaryMachine militaryMachine) {
                    this.setImage(militaryMachine.getType().getImage());
                    return;
                }
            }

            this.setImage(((Troop) this.militaryUnits.get(this.militaryUnits.size() -1)).getType().getImage());
            return;
        }

        if (this.hasBuilding) {
            if (this.building instanceof DefensiveBuilding defensiveBuilding)
                this.setImage(defensiveBuilding.getDefensiveType().getImage());
            else this.setImage(this.building.getType().getImage());
            return;
        }

        this.setImage(this.texture.getImage());
    }

    public boolean isPassable() {
        return isPassable;
    }

    public Territory getTerritory() {
        return this.territory;
    }

    public ArrayList<Person> getPeople() {
        return this.people;
    }

    public void addPerson(Person person) {
        this.people.add(person);
    }

    public void addMilitaryUnit(MilitaryUnit troop) {
        this.militaryUnits.add(troop);
    }

    public void setPassability(boolean passability) {
        isPassable = passability;
    }

    public boolean isTotallyNotEmpty() {
        return !this.people.isEmpty() || !this.militaryUnits.isEmpty() || this.getBuilding() != null;
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
        } else {
            texture = Texture.MOAT;
            this.isPassable = false;
        }
    }

    public boolean hasBuilding() {
        return hasBuilding;
    }
}