package model.game;

enum ItemCategory {
    RESOURCES, FOODS, WEAPONS
}

public enum Item {

    // RESOURCES

    // FOODS

    // WEAPONS
    ;

    private final ItemCategory category;

    private final Item formingMaterial;
    private final int formingMaterialAmount;

    private final int buyCost;
    private final int sellCost;

    Item(ItemCategory category, int buyCost, int sellCost) {
        this.category = category;
        this.formingMaterial = null;
        this.formingMaterialAmount = 0;
        this.buyCost = buyCost;
        this.sellCost = sellCost;
    }

    Item(ItemCategory category, Item formingMaterial, int formingMaterialAmount, int buyCost, int sellCost) {
        this.category = category;
        this.formingMaterial = formingMaterial;
        this.formingMaterialAmount = formingMaterialAmount;
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