package view.enums;

import model.user.SecurityQuestion;

public enum Message {
    // MultiMenu:
    WEAK_PASSWORD("Weak password!"),
    INCOMPATIBLE_PASSWORDS("Incompatible passwords!"),
    BACK_GAME_MENU("Returned to game menu"),
    BACK_MAIN_MENU("Returned to main menu"),
    INVALID_COMMAND("Invalid command!"),

    // SignupMenu:
    CANCEL("The process canceled successfully!"),
    EMPTY_FIELD("Please fill all necessary fields!"),
    INCORRECT_USERNAME_FORM("Incorrect username format!"),
    USERNAME_ALREADY_EXISTS("Username already exists"),
    INCORRECT_EMAIL_FORM("Incorrect email format!"),
    ENTERED_LOGIN_MENU("Entered login menu!"),
    REENTER_AGAIN("Please re-enter your password again:"),
    ASK_FOR_SECURITY_QUESTION("Pick your security question: " +SecurityQuestion.getAllQuestions()),
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
    USERNAME_NOT_FOUND("Some usernames dont exist!"),
    GAME_STARTED("Game started successfully!"), // todo : is message correct ?
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
    ENTERED_MAP_MENU("Entered map menu"),
    ENTERED_BUILDING_MENU("Entered Building menu"),
    ENTERED_UNIT_MENU("Entered unit menu"),
    ENTERED_SHOP_MENU("Entered shop menu"),
    ENTERED_TRADE_MENU(""),

    // MapMenu:

    // ShopMenu:

    INVALID_ITEM_NAME("There are no item with this name!"), //TODO : is message correct ?
    NOT_ENOUGH_GOLD("Not enough gold!"),
    BOUGHT_SUCCESSFUL("Items bought successfully"),
    SOLD_SUCCESSFUL("Items sold successfully!"),

    // TradeMenu:

    // UnitMenu:

    // BuildingMenu:
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
