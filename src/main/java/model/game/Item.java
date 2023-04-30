package model.game;


public enum Item {

    // RESOURCES

    // FOODS

    // WEAPONS
    ;

    private final String name;
    private final ItemCategory category;

    private final Item formingMaterial;
    private final int formingMaterialAmount;

    private final int buyCost;
    private final int sellCost;

    Item( String name, ItemCategory category, int buyCost, int sellCost) {
        this.name = name;
        this.category = category;
        this.formingMaterial = null;
        this.formingMaterialAmount = 0;
        this.buyCost = buyCost;
        this.sellCost = sellCost;
    }

    Item(String name, ItemCategory category, Item formingMaterial, int formingMaterialAmount, int buyCost, int sellCost) {
        this.name = name;
        this.category = category;
        this.formingMaterial = formingMaterial;
        this.formingMaterialAmount = formingMaterialAmount;
        this.buyCost = buyCost;
        this.sellCost = sellCost;
    }

    public static Item getItemByName(String name) {
        for (Item item : Item.values()) {
            if (item.name.equals(name))
                return item;
        }
        return null;
    }

    public String getName() {
        return name;
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