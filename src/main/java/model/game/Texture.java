package model.game;

public enum Texture {
    GROUND("ground", Color.YELLOW, true, true),
    PEBBLE("pebble",Color.WHITE, false, true),
    // Rock is resource of stone!
    ROCK("rock", Color.WHITE, false, true),
    STONE("stone",Color.BROWN, false, false),
    IRON("iron",Color.RED, false, true),
    GRASS("grass", Color.GREEN, true, true),
    MEADOW("meadow", Color.GREEN, true, true),
    DENSE_MEADOW("dense meadow", Color.GREEN, true, true),
    DESERT_TREE("desert tree", Color.GREEN, false, false),
    CHERRY_PALM("cherry palm", Color.GREEN, false, false),
    OLIVE_TREE("olive tree", Color.GREEN, false, false),
    COCONUT_PALM("coconut palm", Color.GREEN, false, false),
    DATE_PALM("date palm", Color.GREEN, false, false),
    OIL("oil", Color.BLACK, false, true),
    PLAIN("plain", Color.GREEN, true, true),
    // This is a channel way through rivers!
    SHALLOW_RIVER("shallow river", Color.CYAN, false, true),
    RIVER("river", Color.BLUE, false, false),
    SHALLOW_POND("shallow pond", Color.CYAN, false, false),
    DEEP_POND("deep pond", Color.BLUE, false, false),
    BEACH("beach", Color.YELLOW, true, true),
    SEA("sea", Color.BLUE, false, false),
    MOAT("moat", Color.BLACK, false, false);

    private final String name;
    private final Color color;
    private final boolean canHaveTree;
    private final boolean canHaveBuildingAndUnit;

    Texture(String name, Color color, boolean canHaveTree, boolean canHaveBuildingAndUnit) {
        this.name = name;
        this.color = color;
        this.canHaveTree = canHaveTree;
        this.canHaveBuildingAndUnit = canHaveBuildingAndUnit;
    }

    public static Texture getTextureByName(String name) {
        for (Texture texture : values())
            if (texture.name.equals(name))
                return texture;
        return null;
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