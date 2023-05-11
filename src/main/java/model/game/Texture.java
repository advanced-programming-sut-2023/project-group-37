package model.game;

public enum Texture {
    GROUND(Color.YELLOW, true, true),
    PEBBLE(Color.WHITE, false, true),
    // Rock is resource of stone!
    ROCK(Color.WHITE, false, true),
    STONE(Color.BROWN, false, false),
    IRON(Color.RED, false, true),
    GRASS(Color.GREEN, true, true),
    MEADOW(Color.GREEN, true, true),
    DENSE_MEADOW(Color.GREEN, true, true),
    DESERT_TREE(Color.GREEN, false, false),
    CHERRY_PALM(Color.GREEN, false, false),
    OLIVE_TREE(Color.GREEN, false, false),
    COCONUT_PALM(Color.GREEN, false, false),
    DATE_PALM(Color.GREEN, false, false),
    OIL(Color.BLACK, false, true),
    PLAIN(Color.GREEN, true, true),
    // This is a channel way through rivers!
    SHALLOW_RIVER(Color.CYAN, false, true),
    RIVER(Color.BLUE, false, false),
    SHALLOW_POND(Color.CYAN, false, false),
    DEEP_POND(Color.BLUE, false, false),
    BEACH(Color.YELLOW, true, true),
    SEA(Color.BLUE, false, false);

    private final Color color;
    private final boolean canHaveTree;
    private final boolean canHaveBuildingAndUnit;

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