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
        switch (this.type){
            case STOCKPILE -> category = ItemCategory.RESOURCES;
            case GRANARY -> category = ItemCategory.FOODS;
            case ARMORY -> category = ItemCategory.WEAPONS;
        }
        for (Item item : Item.values())
            if (item.getCategory() == category)
                this.stock.put(item, 0);
            //TODO: set defaults!
    }

    public int addStock(Item item, int amount){
        int totalStock = 0;
        for (Integer count : this.stock.values())
            totalStock += count;

        int freeSpace = capacity - totalStock, finalAmount = amount;

        if (freeSpace < amount)
            finalAmount = freeSpace;

        this.stock.put(item,this.stock.get(item) + finalAmount);

        if (amount == finalAmount)
            return 0;

        return amount - finalAmount;
    }

}
