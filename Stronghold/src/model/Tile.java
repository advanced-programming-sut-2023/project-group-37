package model;

import model.buildings.Building;
import model.people.Troop;

import java.util.ArrayList;

public class Tile {
    private int x;
    private int y;
    private final ArrayList<Building> buildings = new ArrayList<>();
    private final ArrayList<Troop> troops = new ArrayList<>();
}
