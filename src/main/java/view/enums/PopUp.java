package view.enums;

import javafx.scene.control.Alert;

public enum PopUp {
    // unitMenu :
    NO_UNIT_SELECTED("No unit selected!"),
    NO_ENEMY("There are no enemy in this tile!"),
    OUT_OF_RANGE("Out of range!"),
    CANT_MOVE_THERE("Can not move there !"),
    WILL_MOVE("Unit will move on next turn!"),
    ;
    private final Alert alert;
    PopUp(String message) {
        this.alert = new Alert(Alert.AlertType.ERROR, message);
    }

    public void show() {
        this.alert.show();
    }
}
