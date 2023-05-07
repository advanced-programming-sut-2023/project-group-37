package model.game;


public enum Item {

    // TODO: handle prices!

    // RESOURCES
    WOOD(ItemCategory.RESOURCES, 4, 1),
    STONE(ItemCategory.RESOURCES, 14, 7),
    IRON(ItemCategory.RESOURCES, 45, 23),
    HOPS(ItemCategory.RESOURCES, 15, 8),
    WHEAT(ItemCategory.RESOURCES, 23, 8),
    FLOUR(ItemCategory.RESOURCES, 32, 10),
    ALE(ItemCategory.RESOURCES, 20, 10),
    PITCH(ItemCategory.RESOURCES, 20, 10),

    // FOODS
    APPLE(ItemCategory.FOODS, 8, 4),
    CHEESE(ItemCategory.FOODS, 8, 4),
    MEAT(ItemCategory.FOODS, 8, 4),

    // WEAPONS
    BOW(ItemCategory.WEAPONS, 31, 15),
    SPEAR(ItemCategory.WEAPONS, 20, 10),
    MACE(ItemCategory.WEAPONS, 58, 30),
    CROSSBOW(ItemCategory.WEAPONS, 58, 30),
    PIKE(ItemCategory.WEAPONS, 36, 18),
    SWORD(ItemCategory.WEAPONS, 58, 30),
    METAL_ARMOR(ItemCategory.WEAPONS, 58, 30),
    // TODO: fill leather after defining cow!
    LEATHER_ARMOR(ItemCategory.WEAPONS, 25, 12);;

    private final ItemCategory category;
    private final int buyCost;
    private final int sellCost;

    Item(ItemCategory category, int buyCost, int sellCost) {
        this.category = category;
        this.buyCost = buyCost;
        this.sellCost = sellCost;
    }

    public static Item getItemByName(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (Exception ignored) {
            return null;
        }
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