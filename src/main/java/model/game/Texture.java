package model.game;

import javafx.scene.image.Image;

import java.util.Objects;

public enum Texture {

    GROUND("ground", new Image(Objects.requireNonNull(Texture.class.getResource
            ("/Image/Texture/ground.jpg")).toExternalForm()), true, true),

    PEBBLE("pebble", new Image(Objects.requireNonNull(Texture.class.getResource
            ("/Image/Texture/ground.jpg")).toExternalForm()), false, true),

    // Rock is resource of stone!
    ROCK("rock", new Image(Objects.requireNonNull(Texture.class.getResource
            ("/Image/Texture/rock.jpg")).toExternalForm()), false, true),

    STONE("stone", new Image(Objects.requireNonNull(Texture.class.getResource
            ("/Image/Texture/stone1.png")).toExternalForm()), false, false),

    IRON("iron", new Image(Objects.requireNonNull(Texture.class.getResource
            ("/Image/Texture/iron.jpg")).toExternalForm()), false, true),

    GRASS("grass", new Image(Objects.requireNonNull(Texture.class.getResource
            ("/Image/Texture/grass.jpg")).toExternalForm()), true, true),

    MEADOW("meadow", new Image(Objects.requireNonNull(Texture.class.getResource
            ("/Image/Texture/grass.jpg")).toExternalForm()), true, true),

    DENSE_MEADOW("dense meadow", new Image(Objects.requireNonNull(Texture.class.getResource
            ("/Image/Texture/grass.jpg")).toExternalForm()), true, true),

    DESERT_TREE("desert tree", new Image(Objects.requireNonNull(Texture.class.getResource
            ("/Image/Texture/olive-tree.png")).toExternalForm()), false, false),

    CHERRY_PALM("cherry palm", new Image(Objects.requireNonNull(Texture.class.getResource
            ("/Image/Texture/cherry-palm.png")).toExternalForm()), false, false),

    OLIVE_TREE("olive tree", new Image(Objects.requireNonNull(Texture.class.getResource
            ("/Image/Texture/olive-tree.png")).toExternalForm()), false, false),

    COCONUT_PALM("coconut palm", new Image(Objects.requireNonNull(Texture.class.getResource
            ("/Image/Texture/coconut-palm.png")).toExternalForm()), false, false),

    DATE_PALM("date palm", new Image(Objects.requireNonNull(Texture.class.getResource
            ("/Image/Texture/palm.png")).toExternalForm()), false, false),

    OIL("oil" , new Image(Objects.requireNonNull(Texture.class.getResource
            ("/Image/Texture/oil.jpg")).toExternalForm()), false, true),

    PLAIN("plain", new Image(Objects.requireNonNull(Texture.class.getResource
            ("/Image/Texture/grass.jpg")).toExternalForm()), true, true),

    // This is a channel way through rivers!
    SHALLOW_RIVER("shallow river",  new Image(Objects.requireNonNull(Texture.class.getResource
            ("/Image/Texture/sea.jpg")).toExternalForm()), false, true),

    RIVER("river",  new Image(Objects.requireNonNull(Texture.class.getResource
            ("/Image/Texture/sea.jpg")).toExternalForm()), false, false),

    SHALLOW_POND("shallow pond",  new Image(Objects.requireNonNull(Texture.class.getResource
            ("/Image/Texture/sea.jpg")).toExternalForm()), false, false),

    DEEP_POND("deep pond",  new Image(Objects.requireNonNull(Texture.class.getResource
            ("/Image/Texture/sea.jpg")).toExternalForm()), false, false),

    BEACH("beach", new Image(Objects.requireNonNull(Texture.class.getResource
            ("/Image/Texture/ground.jpg")).toExternalForm()), true, true),

    SEA("sea", new Image(Objects.requireNonNull(Texture.class.getResource
            ("/Image/Texture/sea.jpg")).toExternalForm()), false, false),

    MOAT("moat", new Image(Objects.requireNonNull(Texture.class.getResource
            ("/Image/Texture/sea.jpg")).toExternalForm()), false, false);

    private final String name;
    private final Image image;
    private final boolean canHaveTree;
    private final boolean canHaveBuildingAndUnit;

    Texture(String name,Image image, boolean canHaveTree, boolean canHaveBuildingAndUnit) {
        this.name = name;
        this.image = image;
        this.canHaveTree = canHaveTree;
        this.canHaveBuildingAndUnit = canHaveBuildingAndUnit;
    }

    public static Texture getTextureByName(String name) {
        for (Texture texture : values())
            if (texture.name.equals(name))
                return texture;
        return null;
    }

    public Image getImage() {
        return this.image;
    }

    public boolean canHaveBuildingAndUnit() {
        return this.canHaveBuildingAndUnit;
    }

    public boolean canHaveTree() {
        return this.canHaveTree;
    }
}