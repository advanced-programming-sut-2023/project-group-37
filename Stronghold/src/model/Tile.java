package model;

import model.buildings.Building;
import model.people.Troop;

import java.util.ArrayList;

public class Tile {
    private final int x;
    private final int y;
    private final ArrayList<Troop> troops = new ArrayList<>();
    private Building building;
    private Textures texture;


    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void addTroop(Troop troop) {
        troops.add(troop);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Building getBuilding() {
        return building;
    }

    public Textures getTexture() {
        return texture;
    }

    public void setTexture(Textures texture) {
        this.texture = texture;
    }
}
