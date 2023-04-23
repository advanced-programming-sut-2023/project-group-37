package model.buildings;

import model.game.Government;
import model.game.Item;
import model.game.Tile;

import java.util.HashMap;

public class Storage extends Building {
    private final BuildingType type;
    private final int capacity;
    private final HashMap<Item, Integer> stock;

    public Storage(Government loyalty, Tile location, BuildingType type, int capacity) {
        super(loyalty, location, type);
        this.type = type;
        this.capacity = capacity;

        //TODO: add items to stock based on type!
        this.stock = new HashMap<>();
    }

    public boolean isFull() {
        int totalStock = 0;
        for (Integer count : this.stock.values())
            totalStock += count;
        return totalStock == capacity;
    }
}
