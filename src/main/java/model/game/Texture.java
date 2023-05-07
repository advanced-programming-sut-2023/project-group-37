package model.game;

public enum Texture {
    GROUND(),
    PEBBLE(),
    ROCK(),
    STONE(),
    IRON(),
    GRASS(),
    MEADOW(),
    DENSE_MEADOW(),
    DESERT_TREE(),
    CHERRY_PALM(),
    OLIVE_TREE(),
    COCONUT_PALM(),
    DATE_PALM(),
    OIL(),
    PLAIN(),
    RIVER(),
    SHALLOW_POND(),
    DEEP_POND(),
    BEACH(),
    SEA();

    private Color color;
    private boolean canHaveTree;
    private boolean canHaveBuildingAndUnit;

    // TODO: remove empty ctor later!
    Texture() {

    }

    Texture(Color color, boolean canHaveTree, boolean canHaveBuildingAndUnit) {
        this.color = color;
        this.canHaveTree = canHaveTree;
        this.canHaveBuildingAndUnit = canHaveBuildingAndUnit;
    }

    public static Texture getTextureByName(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (Exception ignored) {
            return null;
        }
    }

    public Color getColor() {
        return this.color;
    }

    public boolean canHaveBuildingAndUnit() {
        return this.canHaveBuildingAndUnit;
    }

    public boolean canHaveTree() {
        return this.canHaveTree;
    }
}