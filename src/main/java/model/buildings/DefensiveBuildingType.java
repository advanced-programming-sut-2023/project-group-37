package model.buildings;

import javafx.scene.image.Image;

import java.util.Objects;

public enum DefensiveBuildingType {

    KEEP("", null, "keep.png", 1, 0, 0, 16),

    WALL("wall", BuildingCategory.CASTLE_BUILDINGS, "wall.png", 250, 1,
            1.1, 1),

    STAIRS("stairs", BuildingCategory.CASTLE_BUILDINGS, "stairs.jpg", 150, 1,
            0, 1),

    SMALL_GATEHOUSE("small gatehouse", BuildingCategory.GATEHOUSES, "small-gatehouse.png",
            350, 10, 1.2, 8),

    LARGE_GATEHOUSE("large gatehouse", BuildingCategory.GATEHOUSES, "large-gatehouse.png",
            450, 20, 1.4, 12),

    LOOKOUT_TOWER("lookout tower", BuildingCategory.TOWERS, "lookout-tower.png", 250,
            10, 2.2, 4),

    PERIMETER_TOWER("perimeter tower", BuildingCategory.TOWERS, "perimeter-tower.png", 350,
            10, 1.6, 6),

    DEFENCE_TOWER("defence tower", BuildingCategory.TOWERS, "defence-tower.png", 350,
            15, 1.6, 6),

    SQUARE_TOWER("square tower", BuildingCategory.TOWERS, "square-tower.png", 450,
            35, 2, 10),

    ROUND_TOWER("round tower", BuildingCategory.TOWERS, "round-tower.png", 450,
            40, 1.8, 10);

    private final String name;
    private final BuildingCategory category;
    private final Image image;
    private final int cost;
    private final int maxHitpoints;
    private final int stoneAmount;
    private final double rangeRate;
    private final int capacity;
    private final boolean isRepairable;

    DefensiveBuildingType(String name, BuildingCategory category, String imageName, int maxHitpoints, int stoneAmount,
                          double rangeRate, int capacity) {
        this.name = name;
        this.category = category;
        this.image = new Image(Objects.requireNonNull(DefensiveBuildingType.class.getResource("/Image/Buildings/" + imageName))
                .toExternalForm());
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

    public BuildingCategory getCategory() {
        return this.category;
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

    public int getStoneAmount() {
        return this.stoneAmount;
    }

    public double getRangeRate() {
        return this.rangeRate;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public boolean isRepairable() {
        return this.isRepairable;
    }
}
