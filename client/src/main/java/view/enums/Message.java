package view.enums;

import model.user.RecoveryQuestion;

public enum Message {
    // MultiMenu:
    WEAK_PASSWORD("Weak password!"),
    INCOMPATIBLE_PASSWORDS("Incompatible passwords!"),
    BACK_GAME_MENU("Returned to game menu!"),
    INVALID_ITEM_NAME("There is no item with this name!"),
    INVALID_AMOUNT("Invalid amount!"),
    ADDRESS_OUT_OF_BOUNDS("Address out of bounds!"),
    CURRENT_LOCATION("Address equals current location"),
    BACK_MAIN_MENU("Returned to main menu"),
    SUCCESS("Success!"),
    INVALID_COMMAND("Invalid command!"),

    // SignupMenu:
    CANT_LOGIN("Username or password incorrect!"),
    CANCEL("The process canceled successfully!"),
    EMPTY_FIELD("Please fill all necessary fields correctly!"),
    INCORRECT_USERNAME_FORM("Incorrect username format!"),
    USERNAME_ALREADY_EXISTS("Username already exists"),
    INCORRECT_EMAIL_FORM("Incorrect email format!"),
    ENTERED_LOGIN_MENU("Entered login menu!"),
    REENTER_AGAIN("Please re-enter your password again:"),
    ASK_FOR_SECURITY_QUESTION("Pick your security question: " + RecoveryQuestion.getAllQuestions()),
    INCORRECT_QUESTION_NUMBER("Please select question-number between 1,2,3:"),
    INCOMPATIBLE_ANSWERS("Incompatible answers!"),
    GO_FOR_CAPTCHA("For the last thing; Enter captcha :"),
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
    INVALID_TERRITORY_NUMBER("Invalid territory format!"),
    USER_NUMBER_LIMIT("You can only choose 7 users to start the game!"),
    USERNAME_NOT_FOUND("Some usernames dont exist!"),
    INVALID_MAP_NAME("Invalid map name!"),
    GAME_STARTED("Game started successfully!"),
    LOGOUT_SUCCESSFUL("User logged out successfully!"),

    // ProfileMenu:
    CHANGE_USERNAME("Username changed successfully!"),
    CHANGE_SUCCESSFUL("change was successful!"),
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
    EMAIL_ALREADY_EXISTS("Email already exists!"),
    CHANGE_EMAIL_ERROR3("Please enter an email!"),
    CHANGE_SLOGAN("Slogan changed successfully!"),
    CHANGE_SLOGAN_ERROR1("Please enter a slogan! (empty field)"),
    REMOVE_SLOGAN("Slogan removed successfully!"),
//    CANCEL("process canceled!"),

    // GameMenu:
    ENTERED_MAP_MENU("Entered map menu!"),
    ENTERED_BUILDING_MENU("Entered Building menu!"),
    MARKET_NOT_EXISTS("Market not exists!"),
    ENGINEER_GUILD_NOT_EXISTS("Engineer Guild not exists!"),
    TUNNELER_GUILD_NOT_EXISTS("Tunneler Guild not exists!"),
    ENTERED_SHOP_MENU("Entered shop menu!"),
    ENTERED_TRADE_MENU("Entered trade menu!"),
    INVALID_TEXTURE_NAME("There is no texture with this name!"),
    TEXTURE_CHANGE_ERROR("You can't change the texture : there is something in this place!"),
    TEXTURE_CHANGED_SUCCESSFULLY("Texture changed successfully!"),
    GAME_END_ALL_DESTROYED("Game ended; All governments are destroyed!"),
    GAME_END_WITH_WINNER("Game ended; winner: "),
    NOT_ENOUGH_HORSE("Not enough horse!"),

    // MapMenu:

    // ShopMenu:
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
    UNIT_STOPPED("Unit stopped!"),
    NO_ROUTS_FOUND("Cant move to the address!"),
    UNIT_NOT_EXISTS("There are no units on address!"),
    UNIT_SELECTED("Unit selected and entered unitMenu!"),
    TYPE_NOT_EXISTS("There are no troop with this type!"),
    NO_UNIT_WITH_THIS_TYPE("There are no unit with this type in the place!"),
    NO_MOVER("There are no mover in this place"),
    NO_PATROL("There are no patroller in this place"),
    BARRACKS_NOT_EXISTS("Barracks not exists!"),
    MERCENARY_POST_NOT_EXISTS("Mercenary Post not exists!"),
    DROP_UNIT_SUCCESSFUL("Drop unit successful!"),
    INVALID_STANCE("Invalid stance!"),
    STANCE_IS_SET("Stance set Successfully!"),
    NO_ENEMY("You can just attack to an address that exist enemy!"),
    NO_ARCHER("You dont have any archers in this place"),
    OUT_OF_RANGE("Out of range!"),
    NO_MEALY_UNIT("You dont have any mealy unit in this place!"),
    DISBAND_SUCCESSFUL("Unit disbanded successfully!"),
    UNIT_NOT_ENGINEER("You can build siege equipments only by engineer units!"),
    INVALID_MACHINE_TYPE("Invalid military machine type!"),
    CANNOT_BUILD_MACHINE_HERE("Cannot build this military machine here!"),
    CONSTRUCTING_SIEGE_EQUIPMENT("Constructing siege equipment"),
    UNIT_NOT_TUNNELER("You can dig tunnel only by tunneler units!"),
    CANNOT_DIG_TUNNEL_THERE("Cannot dig tunnel on this location!"),
    TUNNEL_TOO_FAR_FROM_ENEMY("Tunnel cannot be too far from enemy!"),
    TUNNEL_DIG_SUCCESSFUL("Tunnel dug successfully!"),
    UNIT_CANNOT_DIG_MOAT("At least one member of the unit cannot dig moat!"),
    UNIT_CANNOT_FILL_MOAT("At least one member of the unit cannot fill moat!"),
    CANCEL_DIG_MOAT_SUCCESS("Dig moat canceled successfully!"),
    NO_OIL_SMELTER("You have to build an oil smelter first!"),

    // BuildingMenu:
    INCORRECT_BUILDING("Incorrect building type for this action!"),
    CREATE_UNIT_SUCCESSFUL("Unit created successfully!"),
    TILE_IS_NOT_YOURS("The tile is not in your territory!"),
    BUILDING_NOT_YOURS("Building is not yours!"),
    NO_BUILDING_IN_TILE("No building on the tile!"),
    DO_CAPTCHA("For the last thing enter captcha!"),
    WRONG_CAPTCHA("Captcha is wrong try again!"),
    AREA_NOT_EMPTY("You can't change texture : Area is not empty!"),
    CLEAR_SUCCESSFUL("Block cleared successfully!"),
    DROP_ROCK("You dropped rock here!"),
    DROP_ROCK_ERROR("You can't drop rock here!"),
    DROP_TREE("You dropped tree here!"),
    DROP_TREE_ERROR("You can't drop tree here!"),
    NO_BUILDING_SELECTED("No building has been selected!"),
    INVALID_BUILDING_TYPE("Invalid building type!"),
    CANNOT_PLACE_BUILDING_ON_TEXTURE("Cannot place the building on this texture!"),
    TILE_ALREADY_HAS_BUILDING("Tile already has a building!"),
    NOT_IN_TERRITORY("You can place defensive buildings only in your own territory!"),
    NOT_ENOUGH_PEASANT("Peasant not enough!"),
    NOT_ENOUGH_RESOURCE("Not enough resources!"),
    NOT_ENOUGH_STONE("Not enough stone!"),
    BUILDING_IS_UNIQUE("This building can be placed once!"),
    STORAGE_NOT_NEIGHBOR("You have to place this storage near the other ones!"),
    DROP_BUILDING_SUCCESS("Building has been dropped successfully!"),
    BUILDING_NOT_REPAIRABLE("Building is not repairable!"),
    REPAIR_SUCCESS("Repaired successfully!"),
    INVALID_FOOD_RATE("You have to enter a rate between -2 to 2!"),
    FOOD_RATE_SET("Food rate has been set to"),
    INVALID_TAX_RATE("You have to enter a rate between -3 to 8"),
    TAX_RATE_SET("Tax rate has been set to"),
    INVALID_FEAR_RATE("You have to enter a rate between -5 to 5"),
    FEAR_RATE_SET("Fear rate has been set to"),
    TERRITORY_EXISTS("Territory number already exists !"),
    START_GAME_ERROR("Start Error"),
    USER_IS_LOGIN("Another client loggedIn with this user!"),
    LOBBY_IS_FULL("The lobby is full"),
    CANT_START("No other player to start game!");

    private final String message;

    Message(String message) {
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
