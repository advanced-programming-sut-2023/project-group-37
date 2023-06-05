package model.game;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Objects;

public enum Texture {
    GROUND("ground", Color.YELLOW, new Image(Objects.requireNonNull(Texture.class.getResource("/Image/Texture/desert.jpg")).toExternalForm()), true, true),
    PEBBLE("pebble", Color.WHITE, new Image(Objects.requireNonNull(Texture.class.getResource("/Image/Texture/desert.jpg")).toExternalForm()), false, true),
    // Rock is resource of stone!
    ROCK("rock", Color.WHITE, new Image(Objects.requireNonNull(Texture.class.getResource("/Image/Texture/desert.jpg")).toExternalForm()), false, true),
    STONE("stone", Color.BROWN, new Image(Objects.requireNonNull(Texture.class.getResource("/Image/Texture/desert.jpg")).toExternalForm()), false, false),
    IRON("iron", Color.RED, new Image(Objects.requireNonNull(Texture.class.getResource("/Image/Texture/desert.jpg")).toExternalForm()), false, true),
    GRASS("grass", Color.GREEN, new Image(Objects.requireNonNull(Texture.class.getResource("/Image/Texture/grass.jpg")).toExternalForm()), true, true),
    MEADOW("meadow", Color.GREEN, new Image(Objects.requireNonNull(Texture.class.getResource("/Image/Texture/desert.jpg")).toExternalForm()), true, true),
    DENSE_MEADOW("dense meadow", Color.GREEN, new Image(Objects.requireNonNull(Texture.class.getResource("/Image/Texture/grass.jpg")).toExternalForm()), true, true),
    DESERT_TREE("desert tree", Color.GREEN, new Image(Objects.requireNonNull(Texture.class.getResource("/Image/Texture/desert.jpg")).toExternalForm()), false, false),
    CHERRY_PALM("cherry palm", Color.GREEN, new Image(Objects.requireNonNull(Texture.class.getResource("/Image/Texture/desert.jpg")).toExternalForm()), false, false),
    OLIVE_TREE("olive tree", Color.GREEN, new Image(Objects.requireNonNull(Texture.class.getResource("/Image/Texture/desert.jpg")).toExternalForm()), false, false),
    COCONUT_PALM("coconut palm", Color.GREEN, new Image(Objects.requireNonNull(Texture.class.getResource("/Image/Texture/desert.jpg")).toExternalForm()), false, false),
    DATE_PALM("date palm", Color.GREEN, new Image(Objects.requireNonNull(Texture.class.getResource("/Image/Texture/desert.jpg")).toExternalForm()), false, false),
    OIL("oil", Color.BLACK, new Image(Objects.requireNonNull(Texture.class.getResource("/Image/Texture/desert.jpg")).toExternalForm()), false, true),
    PLAIN("plain", Color.GREEN, new Image(Objects.requireNonNull(Texture.class.getResource("/Image/Texture/desert.jpg")).toExternalForm()), true, true),
    // This is a channel way through rivers!
    SHALLOW_RIVER("shallow river", Color.CYAN, new Image(Objects.requireNonNull(Texture.class.getResource("/Image/Texture/desert.jpg")).toExternalForm()), false, true),
    RIVER("river", Color.BLUE, new Image(Objects.requireNonNull(Texture.class.getResource("/Image/Texture/sea.jpg")).toExternalForm()), false, false),
    SHALLOW_POND("shallow pond", Color.CYAN, new Image(Objects.requireNonNull(Texture.class.getResource("/Image/Texture/sea.jpg")).toExternalForm()), false, false),
    DEEP_POND("deep pond", Color.BLUE, new Image(Objects.requireNonNull(Texture.class.getResource("/Image/Texture/desert.jpg")).toExternalForm()), false, false),
    BEACH("beach", Color.YELLOW, new Image(Objects.requireNonNull(Texture.class.getResource("/Image/Texture/desert.jpg")).toExternalForm()), true, true),
    SEA("sea", Color.BLUE, new Image(Objects.requireNonNull(Texture.class.getResource("/Image/Texture/sea.jpg")).toExternalForm()), false, false),
    MOAT("moat", Color.BLACK, new Image(Objects.requireNonNull(Texture.class.getResource("/Image/Texture/desert.jpg")).toExternalForm()), false, false);

    private final String name;
    private final Color color;
    private final Image image;
    private Image inputImage;
    private final boolean canHaveTree;
    private final boolean canHaveBuildingAndUnit;

    Texture(String name, Color color, Image image, boolean canHaveTree, boolean canHaveBuildingAndUnit) {
        this.name = name;
        this.color = color;
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


    public Color getColor() {
        return this.color;
    }

    public void setInputImage(Image inputImage) {
        this.inputImage = inputImage;
    }

    public Image getImage() {
        if (this.inputImage == null)
            return this.image;
        return this.inputImage;
    }

    public boolean canHaveBuildingAndUnit() {
        return this.canHaveBuildingAndUnit;
    }

    public boolean canHaveTree() {
        return this.canHaveTree;
    }
}