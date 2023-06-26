package model.game;

import java.util.ArrayList;
import java.util.Arrays;

public enum Item {

    // ANIMALS:
    HORSE("horse", ItemCategory.ANIMALS, 0,0),

    // RESOURCES
    WOOD("wood", ItemCategory.RESOURCES, 4, 1),
    STONE("stone", ItemCategory.RESOURCES, 14, 7),
    IRON("iron", ItemCategory.RESOURCES, 45, 23),
    HOPS("hops", ItemCategory.RESOURCES, 15, 8),
    WHEAT("wheat", ItemCategory.RESOURCES, 23, 8),
    FLOUR("flour", ItemCategory.RESOURCES, 32, 10),
    ALE("ale", ItemCategory.RESOURCES, 20, 10),
    PITCH("pitch", ItemCategory.RESOURCES, 20, 10),

    // FOODS
    APPLE("apple", ItemCategory.FOODS, 8, 4),
    CHEESE("cheese", ItemCategory.FOODS, 8, 4),
    BREAD("bread", ItemCategory.FOODS, 8, 4),
    MEAT("meat", ItemCategory.FOODS, 8, 4),

    // WEAPONS
    BOW("bow", ItemCategory.WEAPONS, 31, 15),
    SPEAR("spear", ItemCategory.WEAPONS, 20, 10),
    MACE("mace", ItemCategory.WEAPONS, 58, 30),
    CROSSBOW("crossbow", ItemCategory.WEAPONS, 58, 30),
    PIKE("pike", ItemCategory.WEAPONS, 36, 18),
    SWORD("sword", ItemCategory.WEAPONS, 58, 30),
    METAL_ARMOR("metal armor", ItemCategory.WEAPONS, 58, 30),
    // TODO: fill leather after defining cow!
    LEATHER_ARMOR("leather armor", ItemCategory.WEAPONS, 25, 12);

    private final String name;
    private final ItemCategory category;
    private final int buyCost;
    private final int sellCost;
    private static final ArrayList<Item> allItems = new ArrayList<>();

    static {
        allItems.addAll(Arrays.asList(Item.values()));
    }

    Item(String name, ItemCategory category, int buyCost, int sellCost) {
        this.name = name;
        this.category = category;
        this.buyCost = buyCost;
        this.sellCost = sellCost;
    }

    public static Item getItemByName(String name) {
        for (Item item : values())
            if (item.name.equals(name))
                return item;
        return null;
    }
    public static ArrayList<Item> getAllItems(){
        return allItems;
    }

    public String getName() {
        return this.name;
    }

    public ItemCategory getCategory() {
        return this.category;
    }

    public int getBuyCost() {
        return this.buyCost;
    }

    public int getSellCost() {
        return this.sellCost;
    }
}