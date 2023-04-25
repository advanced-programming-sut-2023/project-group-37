package view.enums;

import model.user.SecurityQuestion;

public enum Message {
    // MultiMenu:
    WEAK_PASSWORD("Weak password!"),
    INCOMPATIBLE_PASSWORDS("Incompatible passwords!"),

    // SignupMenu:
    CANCEL("The process canceled successfully!"),
    EMPTY_FIELD("Please fill all necessary fields!"),
    INCORRECT_USERNAME_FORM("Incorrect username format!"),
    INCORRECT_EMAIL_FORM("Incorrect email format!"),
//    RANDOM_SLOGAN("Your slogan is \"", "\""),
//    RANDOM_PASSWORD("Your random password is: " , "\n" + "Please re-enter your password here:"),
    REENTER_AGAIN("Please re-enter your password again:"),
    ASK_FOR_SECURITY_QUESTION(SecurityQuestion.getAllQuestions()),
    INCORRECT_QUESTION_NUMBER("Please select question-number between 1,2,3:"),
    INCOMPATIBLE_ANSWERS("Incompatible answers!"),
    REGISTER_SUCCESSFUL("user created successfully!"),
    NOT_LOGGED_IN("No loggedIn user!"),

    // LoginMenu:
//    CANCEL("The process canceled successfully!"),
    USER_NOT_EXISTS("Username and password didn't match!"),
    INCORRECT_PASSWORD("Username and password didn't match!"),
    ASK_QUESTION("Please answer following question to change your password:"),
    LOGIN_SUCCESSFUL("user logged in successfully!"),
    INCORRECT_ANSWER("Incorrect answer!"),
    ENTER_NEW_PASSWORD("Please Enter your new password:"),
    ENTER_NEW_PASSWORD_AGAIN("Please enter your new password again:"),
    CHANGE_PASSWORD_SUCCESSFUL("password changed successfully!"),

    // MainMenu:
    ENTERED_PROFILE_MENU("entered profile menu successfully!"),
    GAME_STARTED("game started successfully!"),
    LOGOUT_SUCCESSFUL("user logged out successfully!"),

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
    REMOVE_SLOGAN("slogan removed successfully!"),
//    CANCEL("process canceled!"),

    // GameMenu:
    ENTERED_MAP_MENU(""),
    ENTERED_BUILDING_MENU(""),
    ENTERED_UNIT_MENU(""),
    ENTERED_SHOP_MENU(""),
    ENTERED_TRADE_MENU(""),

    // MapMenu:
    BACK_GAME_MENU(""),

    // ShopMenu:

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
}
