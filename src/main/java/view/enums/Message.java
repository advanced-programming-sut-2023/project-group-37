package view.enums;

import model.user.SecurityQuestion;

public enum Message {
    // MultiMenu:
    WEAK_PASSWORD("Weak password!"),
    INCOMPATIBLE_PASSWORDS("Incompatible passwords!"),
    BACK_GAME_MENU("Returned to game menu!"),
    INVALID_ITEM_NAME("There are no item with this name!"),
    INVALID_AMOUNT("Invalid amount!"),
    ADDRESS_OUT_OF_BOUNDS("Address out of bounds!"),
    CURRENT_LOCATION("Address equals current location"),
    BACK_MAIN_MENU("Returned to main menu"),
    SUCCESS("Success!"),
    INVALID_COMMAND("Invalid command!"),

    // SignupMenu:
    CANCEL("The process canceled successfully!"),
    EMPTY_FIELD("Please fill all necessary fields!"),
    INCORRECT_USERNAME_FORM("Incorrect username format!"),
    USERNAME_ALREADY_EXISTS("Username already exists"),
    INCORRECT_EMAIL_FORM("Incorrect email format!"),
    ENTERED_LOGIN_MENU("Entered login menu!"),
    REENTER_AGAIN("Please re-enter your password again:"),
    ASK_FOR_SECURITY_QUESTION("Pick your security question: " + SecurityQuestion.getAllQuestions()),
    INCORRECT_QUESTION_NUMBER("Please select question-number between 1,2,3:"),
    INCOMPATIBLE_ANSWERS("Incompatible answers!"),
    REGISTER_SUCCESSFUL("User created successfully!"),

    // LoginMenu:
    USER_NOT_EXISTS("Username doesn't exist!"),
    INCORRECT_PASSWORD("Username and password didn't match!"),
    ASK_QUESTION("Please answer following question to change your password:"),
    LOGIN_SUCCESSFUL("User logged in successfully!"),
    INCORRECT_ANSWER("Incorrect answer!"),
    ENTER_NEW_PASSWORD("Please Enter your new password:"),
    ENTER_NEW_PASSWORD_AGAIN("Please enter your new password again:"),
    CHANGE_PASSWORD_SUCCESSFUL("Password changed successfully!"),
    ENTERED_REGISTER_MENU("Entered register menu!"),

    // MainMenu:
    ENTERED_PROFILE_MENU("Entered profile menu successfully!"),
    TERRITORY_NOT_ASSIGNED("Please assign territory for each user!"),
    USER_NUMBER_LIMIT("You can only choose 7 users to start the game!"),
    USERNAME_NOT_FOUND("Some usernames dont exist!"),
    INVALID_MAP_NAME("Invalid map name!"),
    GAME_STARTED("Game started successfully!"),
    LOGOUT_SUCCESSFUL("User logged out successfully!"),

    // ProfileMenu:
    CHANGE_USERNAME("Username changed successfully!"),
    CHANGE_USERNAME_ERROR1("Please enter a username (empty field)!"),
    CHANGE_USERNAME_ERROR2("Invalid format for username!"),
    CHANGE_USERNAME_ERROR3("Please enter a new username!"),
    CHANGE_NICKNAME("Nickname changed successfully!"),
    CHANGE_NICKNAME_ERROR1("Please enter a nickname (empty field)!"),
    CHANGE_NICKNAME_ERROR2("Please enter a new nickname!"),
    CHANGE_PASSWORD("Password changed successfully!"),
    CHANGE_PASSWORD_ERROR1("Please enter a password (empty field)!"),
    CHANGE_PASSWORD_ERROR2("Current password is incorrect!"),
    CHANGE_PASSWORD_ERROR3("Please enter a new password!"),
    CHANGE_PASSWORD_ERROR4("Password is weak!"),
    CHANGE_PASSWORD_ERROR5("Incompatible Passwords!"),
    ENTER_PASSWORD_AGAIN("Please enter new password again!"),
    CHANGE_EMAIL("Email changed successfully!"),
    CHANGE_EMAIL_ERROR1("Invalid format for email!"),
    CHANGE_EMAIL_ERROR2("Email already exists!"),
    CHANGE_EMAIL_ERROR3("Please enter an email!"),
    CHANGE_SLOGAN("Slogan changed successfully!"),
    CHANGE_SLOGAN_ERROR1("Please enter a slogan! (empty field)"),
    REMOVE_SLOGAN("Slogan removed successfully!"),
//    CANCEL("process canceled!"),

    // GameMenu:
    ENTERED_MAP_MENU("Entered map menu!"),
    ENTERED_BUILDING_MENU("Entered Building menu!"),
    ENTERED_UNIT_MENU("Entered unit menu!"),
    MARKET_NOT_EXISTS("Market not exists!"),
    ENTERED_SHOP_MENU("Entered shop menu!"),
    ENTERED_TRADE_MENU("Entered trade menu!"),
    INVALID_TEXTURE_NAME("There is no texture with this name!"),
    TEXTURE_CHANGE_ERROR("You can't change the texture : there is something in this place!"),
    TEXTURE_CHANGED_SUCCESSFULLY("Texture changed successfully!"),

    // MapMenu:

    // ShopMenu:

    //TODO : is message correct ?
    NOT_ENOUGH_GOLD("Not enough gold!"),
    NOT_ENOUGH_SPACE("Not enough space!"),
    NOT_ENOUGH_AMOUNT("Not enough amount!"),
    BOUGHT_SUCCESSFUL("Items bought successfully"),
    SOLD_SUCCESSFUL("Items sold successfully!"),

    // TradeMenu:
    INVALID_PRICE("Invalid price!"),
    REQUEST_SENT("Request sent successful!"),
    INVALID_ID("Invalid id!"),
    TRADE_FAILED("This trade can't be done!"),
    TRADE_SUCCESS("The trade was successfully completed!"),

    // UnitMenu:

    NO_ROUTS_FOUND("Cant move to the address!"),
    UNIT_NOT_EXISTS("There are no units on address!"),
    UNIT_SELECTED("Unit selected and entered unitMenu!"),
    TYPE_NOT_EXISTS("There are no troop with this type!"),
    BARRACKS_NOT_EXISTS("Barracks not exists!"),
    MERCENARY_POST_NOT_EXISTS("Mercenary Post not exists!"),
    CREATE_UNIT_SUCCESSFUL("Create unit successful!"),
    STATE_IS_SET("State set Successfully!"),
    NO_ENEMY("You can just attack to an address that exist enemy!"),
    NO_ARCHER("You dont have any archers in this place"),
    OUT_OF_RANGE("Out of range!"),
    NO_MEALY_UNIT("You dont have any mealy unit in this place!"),

    // BuildingMenu:,
    TILE_IS_NOT_YOURS("The tile is not in your territory!"),
    BUILDING_NOT_YOURS("Building is not yours!"),
    NO_BUILDING_IN_TILE("No building in the tile!"),
    DO_CAPTCHA("For the last thing enter captcha!"),
    WRONG_CAPTCHA("Captcha is wrong try again!"),
    AREA_NOT_EMPTY("You can't change texture : Area is not empty!"),
    CLEAR_SUCCESSFUL("Block cleared successfully!"),
    ;

    private final String message;

    Message(String message){
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }

    public boolean equals(String message) {
        return message.equals(this.message);
    }
}
