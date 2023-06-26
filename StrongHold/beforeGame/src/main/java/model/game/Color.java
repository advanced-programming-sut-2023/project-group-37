package model.game;

public enum Color {
    RED(javafx.scene.paint.Color.RED),
    CYAN(javafx.scene.paint.Color.CYAN),
    BLUE(javafx.scene.paint.Color.BLUE),
    YELLOW(javafx.scene.paint.Color.YELLOW),
    GREEN(javafx.scene.paint.Color.GREEN),
    WHITE(javafx.scene.paint.Color.WHITE),
    BROWN(javafx.scene.paint.Color.BROWN),
    BLACK(javafx.scene.paint.Color.BLACK);

    private final javafx.scene.paint.Color color;

    Color(javafx.scene.paint.Color color) {
        this.color = color;
    }

    public javafx.scene.paint.Color getColor() {
        return this.color;
    }

    @Override
    public String toString() {
        return "";
    }
}
