package model.game;

import java.util.ArrayList;
import java.util.Arrays;

public enum Item {

    // ANIMALS:
    HORSE("horse", ItemCategory.ANIMALS, 0, 0, null),

    // RESOURCES
    WOOD("wood", ItemCategory.RESOURCES, 4, 1, "/Image/Item/wood.png"),
    STONE("stone", ItemCategory.RESOURCES, 14, 7, "/Image/Item/rock.png"),
    IRON("iron", ItemCategory.RESOURCES, 45, 23, "/Image/Item/iron.png"),
    HOPS("hops", ItemCategory.RESOURCES, 15, 8, "/Image/Item/hops.png"),
    WHEAT("wheat", ItemCategory.RESOURCES, 23, 8, "/Image/Item/wheat.png"),
    FLOUR("flour", ItemCategory.RESOURCES, 32, 10, "/Image/Item/flour.png"),
    ALE("ale", ItemCategory.RESOURCES, 20, 10, "/Image/Item/ale.png"),
    PITCH("pitch", ItemCategory.RESOURCES, 20, 10, "/Image/Item/pitch.png"),

    // FOODS
    APPLE("apple", ItemCategory.FOODS, 8, 4, "/Image/Item/apple.png"),
    CHEESE("cheese", ItemCategory.FOODS, 8, 4, "/Image/Item/cheese.png"),
    BREAD("bread", ItemCategory.FOODS, 8, 4, "/Image/Item/bread.png"),
    MEAT("meat", ItemCategory.FOODS, 8, 4, "/Image/Item/meat.png"),

    // WEAPONS
    BOW("bow", ItemCategory.WEAPONS, 31, 15, "/Image/Item/bow.png"),
    SPEAR("spear", ItemCategory.WEAPONS, 20, 10, "/Image/Item/spear.png"),
    MACE("mace", ItemCategory.WEAPONS, 58, 30, "/Image/Item/mace.png"),
    CROSSBOW("crossbow", ItemCategory.WEAPONS, 58, 30, "/Image/Item/crossbow.png"),
    PIKE("pike", ItemCategory.WEAPONS, 36, 18, "/Image/Item/pike.png"),
    SWORD("sword", ItemCategory.WEAPONS, 58, 30, "/Image/Item/sword.png"),
    METAL_ARMOR("metal armor", ItemCategory.WEAPONS, 58, 30, "/Image/Item/metal-amour.png"),
    // TODO: fill leather after defining cow!
    LEATHER_ARMOR("leather armor", ItemCategory.WEAPONS, 25, 12, "/Image/Item/leather-amour.png");

    private final String name;
    private final ItemCategory category;
    private final int buyCost;
    private final int sellCost;
    private static final ArrayList<Item> allItems = new ArrayList<>();
    private final String imageUrl;

    static {
        allItems.addAll(Arrays.asList(Item.values()));
    }

    Item(String name, ItemCategory category, int buyCost, int sellCost, String imageUrl) {
        this.name = name;
        this.category = category;
        this.buyCost = buyCost;
        this.sellCost = sellCost;
        this.imageUrl = imageUrl;
    }

    public static Item getItemByName(String name) {
        for (Item item : values())
            if (item.name.equals(name))
                return item;
        return null;
    }

    public static ArrayList<Item> getAllItems() {
        return allItems;
    }

    public String getImageUrl() {
        return imageUrl;
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