package model.people;

import javafx.scene.image.Image;

import java.util.Objects;

public enum MilitaryMachineType {

    // TODO: image for machines...

    PORTABLE_SHIELD("portable shield", "shield.png", 5, 300, 0, 0,
            80, 1),
    BATTERING_RAM("battering ram", "battering-ram.png", 150, 250, 200,
            1, 45, 4),
    SIEGE_TOWER("siege tower", "siege-tower.png", 150, 250, 0, 1,
            50, 4),
    CATAPULT("catapult", "catapult.png", 150, 80, 100, 35, 45,
            2),
    TREBUCHET("trebuchet", "trebuchet.png", 150, 80, 125, 45, 0,
            3),
    FIRE_BALLISTA("fire ballista", "fire-ballista.png", 150, 75, 40, 30,
            30, 2);

    private final String name;
    private final Image downPaneImage;
    private final Image image;
    private final int cost;
    private final int maxHitpoints;
    private final int damage;
    private final int range;
    private final int speed;
    private final int operatorsNeeded;

    MilitaryMachineType(String name, String imageName, int cost, int maxHitpoints, int damage,
                        int range, int speed, int engineersNeeded) {
        this.name = name;
        this.cost = cost;
        this.maxHitpoints = maxHitpoints;
        this.damage = damage;
        this.range = range;
        this.speed = speed;
        this.operatorsNeeded = engineersNeeded;
        this.downPaneImage = new Image(Objects.requireNonNull(MilitaryMachineType.class.getResource
                ("/Image/DownPaneTroops/MilitaryMachine/" + imageName)).toExternalForm());
//        this.image = new Image(Objects.requireNonNull(MilitaryMachineType.class.getResource
//                ("/Image/Troop/MilitaryMachine/" + imageName)).toExternalForm());
        this.image = null;
    }

    public static MilitaryMachineType getMilitaryMachineTypeByName(String name) {
        for (MilitaryMachineType type : values())
            if (type.name.equals(name))
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

    public int getOperatorsNeeded() {
        return this.operatorsNeeded;
    }
}