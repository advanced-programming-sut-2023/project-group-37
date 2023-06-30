package model.graphic;

import javafx.scene.ImageCursor;
import javafx.scene.image.Image;

import java.util.Objects;

public enum CursorType {
//    DEFAULT(""), // todo
    SELECT_MOVE_DESTINATION(Objects.requireNonNull(CursorType.class.getResource(
            "/Image/Cursor/move.gif")).toExternalForm()),

    SELECT_ATTACK_DESTINATION(Objects.requireNonNull(CursorType.class.getResource(
            "/Image/Cursor/attack.gif")).toExternalForm());
    ;

    private final ImageCursor imageCursor;

    CursorType(String imageName) {
        this.imageCursor = new ImageCursor(new Image(imageName));
    }

    public ImageCursor getImageCursor() {
        return imageCursor;
    }
}
