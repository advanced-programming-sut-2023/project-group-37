package model.game;


public enum Item {

    // TODO: handle prices!

    // RESOURCES
    WOOD("Wood", ItemCategory.RESOURCES, 4, 1),
    STONE("Stone", ItemCategory.RESOURCES, 14, 7),
    IRON("Iron", ItemCategory.RESOURCES, 45, 23),
    HOPS("Hops", ItemCategory.RESOURCES, 15, 8),
    WHEAT("Wheat", ItemCategory.RESOURCES, 23, 8),
    FLOUR("Flour", ItemCategory.RESOURCES, 32, 10),
    ALE("Ale", ItemCategory.RESOURCES, 20, 10),
    PITCH("Pitch", ItemCategory.RESOURCES, 20, 10),

    // FOODS
    APPLE("Apple", ItemCategory.FOODS, 8, 4),
    CHEESE("Cheese", ItemCategory.FOODS, 8, 4),
    //TODO: check bread price!
    BREAD("Bread", ItemCategory.FOODS, 8, 4),
    MEAT("Meat", ItemCategory.FOODS, 8, 4),

    // WEAPONS
    BOW("Bow", ItemCategory.WEAPONS, 31, 15),
    SPEAR("Spear", ItemCategory.WEAPONS, 20, 10),
    MACE("Mace", ItemCategory.WEAPONS, 58, 30),
    CROSSBOW("Crossbow", ItemCategory.WEAPONS, 58, 30),
    PIKE("Pike", ItemCategory.WEAPONS, 36, 18),
    SWORD("Sword", ItemCategory.WEAPONS, 58, 30),
    METAL_ARMOR("Metal armor", ItemCategory.WEAPONS, 58, 30),
    // TODO: fill leather after defining cow!
    LEATHER_ARMOR("Leather armor", ItemCategory.WEAPONS, 25, 12);

    private final String name;

    private final ItemCategory category;
    private final int buyCost;
    private final int sellCost;

    Item(String name, ItemCategory category, int buyCost, int sellCost) {
        this.name = name;
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