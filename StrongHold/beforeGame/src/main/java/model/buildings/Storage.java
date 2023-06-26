package model.buildings;

import model.game.Government;
import model.game.Item;
import model.game.ItemCategory;
import model.game.Tile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Storage extends Building {
    private final BuildingType type;
    private final HashMap<Item, Integer> stock;

    public Storage(Government loyalty, Tile location, BuildingType type) {
        super(loyalty, location, type);
        this.type = type;

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
    }

    public HashMap<Item, Integer> getStock() {
        return this.stock;
    }

    public int getFreeSpace() {
        int totalStock = 0;
        for (Integer count : this.stock.values())
            totalStock += count;

        return this.type.getCapacity() - totalStock;
    }

    public String getFoodNames() {
        StringBuilder foodList = new StringBuilder();
        Set<Item> foods = stock.keySet();
        for (Item food : foods) {
            foodList.append(food.getName()).append("\n");
        }

        return foodList.toString().trim();
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
