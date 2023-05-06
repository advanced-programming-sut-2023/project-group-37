package model.game;


public enum Item {

    // TODO: handle prices!

    // RESOURCES
    WOOD(ItemCategory.RESOURCES, 0, 0),
    STONE(ItemCategory.RESOURCES, 0, 0),
    IRON(ItemCategory.RESOURCES, 0, 0),
    HOPS(ItemCategory.RESOURCES, 0, 0),
    WHEAT(ItemCategory.RESOURCES, 0, 0),
    FLOUR(ItemCategory.RESOURCES, 0,0),

    // FOODS
    APPLE(ItemCategory.FOODS, 0, 0),
    CHEESE(ItemCategory.FOODS, 0, 0),
    MEAT(ItemCategory.FOODS, 0, 0),

    // WEAPONS
    BOW(ItemCategory.WEAPONS, 0, 0),
    SPEAR(ItemCategory.WEAPONS, 0, 0),
    MACE(ItemCategory.WEAPONS, 0, 0),
    CROSSBOW(ItemCategory.WEAPONS, 0, 0),
    PIKE(ItemCategory.WEAPONS, 0, 0),
    SWORD(ItemCategory.WEAPONS, 0, 0),
    METAL_ARMOR(ItemCategory.WEAPONS, 0, 0),
    // TODO: fill leather after defining cow!
    LEATHER_ARMOR(ItemCategory.WEAPONS, 0, 0);;

    private final ItemCategory category;
    private final int buyCost;
    private final int sellCost;

    Item(ItemCategory category, int buyCost, int sellCost) {
        this.category = category;
        this.buyCost = buyCost;
        this.sellCost = sellCost;
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