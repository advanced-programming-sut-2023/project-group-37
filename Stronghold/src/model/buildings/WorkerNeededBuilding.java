package model.buildings;

import model.game.Government;
import model.game.Tile;
import model.people.Person;

import java.util.ArrayList;

public class WorkerNeededBuilding extends Building{

    private final int workersNeeded;

    private final ArrayList<Person> workers;

    public WorkerNeededBuilding(Government loyalty, Tile location, BuildingType type) {
        super(loyalty, location, type);
        this.workersNeeded = type.getWorkersNeeded();
        this.workers = new ArrayList<>();
    }
}
