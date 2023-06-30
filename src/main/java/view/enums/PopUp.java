package view.enums;

import javafx.scene.control.Alert;

public enum PopUp {
    // unitMenu :
    NO_UNIT_SELECTED("No unit selected!"),
    NO_ENEMY("There are no enemy in this tile!"),
    OUT_OF_RANGE("Out of range!"),
    CANT_MOVE_THERE("Can not move there !"),
    WILL_MOVE("Unit will move on next turn!", true),
    WILL_ATTACK("Unit will attack on next turn!", true),

    // building menu:
    NOT_ENOUGH_GOLD("Not enough gold !"),
    NOT_ENOUGH_RESOURCE("Not enough resources!"),
    // Shop Menu :
    MARKET_NOT_EXISTS("Market not exists!"),

    //Trade menu
    NOT_ENOUGH("You don't have more!"),
    ;
    private final Alert alert;

    PopUp(String message) {
        this.alert = new Alert(Alert.AlertType.ERROR, message);
    }

    PopUp(String message, boolean information) {
        this.alert = new Alert(Alert.AlertType.INFORMATION, message);
    }

    public void show() {
        this.alert.show();
    }
}
