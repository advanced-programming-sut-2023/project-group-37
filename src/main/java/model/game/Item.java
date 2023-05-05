package model.game;


public enum Item {

    // RESOURCES
    WOOD(),
    STONE(),
    IRON(),

    // FOODS

    // WEAPONS
    // TODO: fill sell/buy costs!
    BOW(ItemCategory.WEAPONS, Item.WOOD, 2, 0,0),
    SPEAR(ItemCategory.WEAPONS, Item.WOOD, 1, 0, 0),
    MACE(ItemCategory.WEAPONS, Item.IRON, 1,0,0),
    CROSSBOW(ItemCategory.WEAPONS, Item.WOOD, 3,0,0),
    PIKE(ItemCategory.WEAPONS, Item.WOOD, 2,0,0),
    SWORD(ItemCategory.WEAPONS, Item.IRON, 1,0,0),
    METAL_ARMOR(ItemCategory.WEAPONS, Item.IRON, 1,0,0),
    // TODO: fill leather after defining cow!
    LEATHER_ARMOR(ItemCategory.WEAPONS, null, 0,0,0);

    ;

//    private final String name;
    private final ItemCategory category;
    private final Item formingMaterial;
    private final int formingMaterialAmount;
    private final int buyCost;
    private final int sellCost;

    Item(/*String name, */ItemCategory category, int buyCost, int sellCost) {
//        this.name = name;
        this.category = category;
        this.formingMaterial = null;
        this.formingMaterialAmount = 0;
        this.buyCost = buyCost;
        this.sellCost = sellCost;
    }

    Item(/*String name, */ItemCategory category, Item formingMaterial, int formingMaterialAmount, int buyCost, int sellCost) {
//        this.name = name;
        this.category = category;
        this.formingMaterial = formingMaterial;
        this.formingMaterialAmount = formingMaterialAmount;
        this.buyCost = buyCost;
        this.sellCost = sellCost;
    }

//    public static Item getItemByName(String name) {
//        for (Item item : Item.values()) {
//            if (item.name.equals(name))
//                return item;
//        }
//        return null;
//    }

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