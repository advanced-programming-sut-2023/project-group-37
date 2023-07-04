package view.enums;

import javafx.scene.control.Alert;

public enum PopUp {
    // Register & Login Menu :
    CANT_LOGIN("Username and password didn't match!"),

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
    NOT_ENOUGH("You don't have this amount to donate!"),
    INVALID_ID("Invalid id!"),
    TRADE_FAILED("This trade can't be done!"),
    TRADE_SUCCESS("The trade was successfully completed!"),
    LOGIN_SUCCESSFUL("LoggedIn successfully!", true),
    EMPTY_FIELD("Please enter something!"),
    LOBBY_NOT_FOUND("No lobby with this Id!"),
    SEARCH_LOBBY("PLease search a lobby first!");
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
