package model.buildings;

import model.game.Government;
import model.game.Item;
import model.game.ItemCategory;
import model.game.Tile;

import java.util.HashMap;

public class Storage extends Building {
    private BuildingType type;
    private final int capacity;
    private final HashMap<Item, Integer> stock;

    public Storage(Government loyalty, Tile location, BuildingType type, int capacity) {
        super(loyalty, location, type);
        this.type = type;
        this.capacity = capacity;

        //TODO: add items to stock based on type!
        this.stock = new HashMap<>();
        ItemCategory category = null;
        switch (this.type) {
            case STOCKPILE -> category = ItemCategory.RESOURCES;
            case GRANARY -> category = ItemCategory.FOODS;
            case ARMORY -> category = ItemCategory.WEAPONS;
        }
        for (Item item : Item.values())
            if (item.getCategory() == category)
                this.stock.put(item, 0);
        //TODO: set defaults!
    }

    public int getFreeSpace() {
        int totalStock = 0;
        for (Integer count : this.stock.values())
            totalStock += count;

        return capacity - totalStock;
    }

    public int getItemAmount(Item item) {
        return stock.get(item);
    }

    public int increaseStock(Item item, int amount) {
        int freeSpace = getFreeSpace(), finalAmount = amount;

        if (freeSpace < amount)
            finalAmount = freeSpace;

        this.stock.put(item, this.stock.get(item) + finalAmount);

        return amount - finalAmount;
    }

    public int decreaseStock(Item item, int amount) {
        int itemAmount = getItemAmount(item), finalAmount = amount;

        if (itemAmount < amount)
            finalAmount = itemAmount;

        this.stock.put(item, this.stock.get(item) - finalAmount);
        return amount - finalAmount;
    }

}
