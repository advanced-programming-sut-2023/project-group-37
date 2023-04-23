package model.game;

import model.buildings.Building;
import model.people.MilitaryUnit;

import java.util.ArrayList;

public class Tile {
    private final int x;
    private final int y;
    private Texture texture;

    // TODO: may need another arraylist of normal people
    private final ArrayList<MilitaryUnit> militaryUnits;
    private Building building;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.texture = Texture.GROUND;
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
}