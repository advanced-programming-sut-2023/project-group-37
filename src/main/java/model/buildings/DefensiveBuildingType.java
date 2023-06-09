package model.buildings;

import javafx.scene.image.Image;

import java.util.Objects;

public enum DefensiveBuildingType {

    KEEP("", new Image(Objects.requireNonNull(DefensiveBuildingType.class.getResource
            ("/Image/building/keep.png")).toExternalForm()), 1, 0, 0, 16),
    WALL("wall", new Image(Objects.requireNonNull(DefensiveBuildingType.class.getResource
            ("/Image/building/wall.png")).toExternalForm()), 250, 1, 1.1, 1),
    STAIRS("stairs", new Image(Objects.requireNonNull(DefensiveBuildingType.class.getResource
            ("/Image/building/stairs.png")).toExternalForm()), 150, 1, 0, 1),
    SMALL_GATEHOUSE("small gatehouse", new Image(Objects.requireNonNull(DefensiveBuildingType.class.getResource
            ("/Image/building/small-gatehouse.png")).toExternalForm()), 350, 10, 1.2,
            8),
    LARGE_GATEHOUSE("large gatehouse", new Image(Objects.requireNonNull(DefensiveBuildingType.class.getResource
            ("/Image/building/large-gatehouse.png")).toExternalForm()), 450, 20, 1.4,
            12),
    LOOKOUT_TOWER("lookout tower", new Image(Objects.requireNonNull(DefensiveBuildingType.class.getResource
            ("/Image/building/lookout-tower.png")).toExternalForm()), 250, 10, 2.2,
            4),
    PERIMETER_TOWER("perimeter tower", new Image(Objects.requireNonNull(DefensiveBuildingType.class.getResource
            ("/Image/building/perimeter-tower.png")).toExternalForm()), 350, 10, 1.6,
            6),
    DEFENCE_TOWER("defence tower", new Image(Objects.requireNonNull(DefensiveBuildingType.class.getResource
            ("/Image/building/defence=tower.png")).toExternalForm()), 350, 15, 1.6,
            6),
    SQUARE_TOWER("square tower", new Image(Objects.requireNonNull(DefensiveBuildingType.class.getResource
            ("/Image/building/square-tower.png")).toExternalForm()), 450, 35, 2,
            10),
    ROUND_TOWER("round tower", new Image(Objects.requireNonNull(DefensiveBuildingType.class.getResource
            ("/Image/building/round-tower.png")).toExternalForm()), 450, 40, 1.8,
            10),
    ;
    private final String name;
    private final int cost;
    private final int maxHitpoints;
    private final int stoneAmount;
    private final double rangeRate;
    private final int capacity;
    private final boolean isRepairable;
    private final Image image;

    DefensiveBuildingType(String name, Image image, int maxHitpoints, int stoneAmount, double rangeRate, int capacity) {
        this.name = name;
        this.image = image;
        this.cost = 0;
        this.maxHitpoints = maxHitpoints;
        this.stoneAmount = stoneAmount;
        this.rangeRate = rangeRate;
        this.capacity = capacity;
        this.isRepairable = true;
    }

    public static DefensiveBuildingType getDefensiveBuildingTypeByName(String name) {
        for (DefensiveBuildingType type : values())
            if (type.getName().equals(name))
                return type;
        return null;
    }

    public String getName() {
        return this.name;
    }

    public int getCost() {
        return this.cost;
    }

    public int getMaxHitpoints() {
        return this.maxHitpoints;
    }

    public int getStoneAmount() {
        return this.stoneAmount;
    }

    public double getRangeRate() {
        return this.rangeRate;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public Image getImage() {
        return image;
    }

    public boolean isRepairable() {
        return this.isRepairable;
    }
}
