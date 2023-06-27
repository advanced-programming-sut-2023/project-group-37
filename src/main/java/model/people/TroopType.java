package model.people;

import javafx.scene.image.Image;
import model.buildings.BuildingType;
import model.game.Item;

import java.util.Objects;

public enum TroopType {

    //LORD:
    LORD("", "lord.png", null, 0, 100, 100, 55, 1,
            null, null, false, false),

    // Barracks:
    ARCHER("archer", "european/archer.png", BuildingType.BARRACKS, 12, 45,
            40, 90, 10, Item.BOW, null, true, true),

    SPEARMAN("spearman", "european/spearman.png", BuildingType.BARRACKS, 8, 50,
            50, 60, 1, Item.SPEAR, null, true, true),

    MaceMan("maceman", "european/maceman.png", BuildingType.BARRACKS, 20, 70,
            75, 85, 1, Item.MACE, Item.LEATHER_ARMOR, true, true),

    CROSSBOWMAN("crossbowman", "european/crossbowman.png", BuildingType.BARRACKS, 20,
            75, 70, 55, 8, Item.CROSSBOW, Item.LEATHER_ARMOR, false,
            false),

    PIKEMAN("pikeman", "european/pikeman.png", BuildingType.BARRACKS, 20, 77,
            76, 60, 1, Item.PIKE, Item.METAL_ARMOR, false, true),

    SWORDSMAN("swordsman", "european/swordsman.png", BuildingType.BARRACKS, 40, 94,
            94, 40, 1, Item.SWORD, Item.METAL_ARMOR, false, false),

    KNIGHT("knight", "european/knight.png", BuildingType.BARRACKS, 40, 90, 94,
            95, 1, Item.SWORD, Item.METAL_ARMOR, Item.HORSE, false, false),

    // Mercenary post:
    ARABIAN_ARCHER("arabian archer", "arabian/archer.png", BuildingType.MERCENARY_POST, 75,
            45, 45, 90, 12, null, null, false, true),

    SLAVE("slave", "arabian/slave.png", BuildingType.MERCENARY_POST, 5,
            20, 10, 90, 1, null, null, false, true),

    SLINGER("slinger", "arabian/slinger.png", BuildingType.MERCENARY_POST, 12,
            20, 36, 90, 6, null, null, false, false),

    HORSE_ARCHER("horse archer", "arabian/horse-archer.png", BuildingType.MERCENARY_POST, 80,
            55, 50, 95, 12, null, null, false, false),

    ARABIAN_SWORDSMAN("arabian swordsman", "arabian/swordsman.png", BuildingType.MERCENARY_POST, 80,
            88, 88, 40, 1, null, null, false, false),

    ASSASSIN("assassin", "arabian/assassin.png", BuildingType.MERCENARY_POST, 60,
            73, 76, 67, 1, null, null, false, false),

    FIRE_THROWER("fire thrower", "arabian/fire-thrower.png", BuildingType.MERCENARY_POST, 100,
            60, 84, 60, 4, null, null, false, false),

    // Engineer guild:
    ENGINEER("engineer", "european/engineer.png", BuildingType.ENGINEER_GUILD, 30,
            10, 0, 60, 1, null, null, false, false),

    LADDERMAN("ladderman", "european/ladderman.png", BuildingType.ENGINEER_GUILD, 4,
            5, 0, 60, 1, null, null, false, false),

    // Tunneler guild:
    TUNNELER("tunneler", "european/tunneler.png", BuildingType.TUNNELER_GUILD, 30,
            20, 0, 60, 20, null, null, false, false);

    private final String name;
    private final Image downPaneImage;
    private final Image image;
    private final BuildingType trainingCamp;
    private final int cost;
    private final int maxHitpoints;
    private final int damage;
    private final int range;
    private final int speed;
    private final Item weapon;
    private final Item armor;
    private final Item animal;
    private final boolean canClimbLadder;
    private final boolean canDigMoat;

    TroopType(String name, String imageURL, BuildingType trainingCamp, int cost, int maxHitpoints, int damage,
              int speed, int range, Item weapon, Item armor, boolean canClimbLadder, boolean canDigMoat) {
        this.name = name;
        this.downPaneImage = new Image(Objects.requireNonNull(TroopType.class.getResource
                ("/Image/DownPaneTroops/" + imageURL)).toExternalForm());
        this.image = new Image(Objects.requireNonNull(TroopType.class.getResource
                ("/Image/Troop/" + imageURL)).toExternalForm());
        this.trainingCamp = trainingCamp;
        this.cost = cost;
        this.maxHitpoints = maxHitpoints;
        this.damage = damage;
        this.range = range;
        this.speed = speed;
        this.weapon = weapon;
        this.armor = armor;
        this.animal = null;
        this.canClimbLadder = canClimbLadder;
        this.canDigMoat = canDigMoat;
    }

    TroopType(String name, String imageURL, BuildingType trainingCamp, int cost, int maxHitpoints, int damage, int speed, int range,
              Item weapon, Item armor, Item animal, boolean canClimbLadder, boolean canDigMoat) {
        this.name = name;
        this.downPaneImage = new Image(Objects.requireNonNull(TroopType.class.getResource
                ("/Image/DownPaneTroops/" + imageURL)).toExternalForm());
        this.image = new Image(Objects.requireNonNull(TroopType.class.getResource
                ("/Image/Troop/" + imageURL)).toExternalForm());
        this.trainingCamp = trainingCamp;
        this.cost = cost;
        this.maxHitpoints = maxHitpoints;
        this.damage = damage;
        this.range = range;
        this.speed = speed;
        this.weapon = weapon;
        this.armor = armor;
        this.animal = animal;
        this.canClimbLadder = canClimbLadder;
        this.canDigMoat = canDigMoat;
    }

    public static TroopType getTroopTypeByName(String name) {
        for (TroopType type : values())
            if (type.getName().equals(name))
                return type;
        return null;
    }

    public String getName() {
        return this.name;
    }

    public Image getDownPaneImage() {
        return this.downPaneImage;
    }

    public Image getImage() {
        return this.image;
    }

    public BuildingType getTrainingCamp() {
        return this.trainingCamp;
    }

    public int getCost() {
        return this.cost;
    }

    public int getMaxHitpoints() {
        return this.maxHitpoints;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getRange() {
        return this.range;
    }

    public int getSpeed() {
        return this.speed;
    }

    public Item getWeapon() {
        return this.weapon;
    }

    public Item getArmor() {
        return this.armor;
    }

    public Item getAnimal() {
        return this.animal;
    }

    public boolean canClimbLadder() {
        return this.canClimbLadder;
    }

    public boolean canDigMoat() {
        return this.canDigMoat;
    }
}