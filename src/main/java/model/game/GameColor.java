package model.game;

import javafx.scene.paint.Color;

public enum GameColor {

    BLUE("blue", Color.BLUE),
    GREEN("green", Color.GREEN),
    GREY("grey", Color.GREY),
    ORANGE("orange", Color.ORANGE),
    PURPLE("purple", Color.PURPLE),
    RED("red", Color.RED),
    SKY_BLUE("sky-blue", Color.SKYBLUE),
    YELLOW("yellow", Color.YELLOW);

    private final String name;
    private final Color color;

    GameColor(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
