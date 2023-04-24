package view.enums.messages;

public enum ProfileMenuMessages {
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
    CHANGE_EMAIL_ERROR1("Invalid format for Email!"),
    CHANGE_EMAIL_ERROR2("Email already exists!"),
    CHANGE_EMAIL_ERROR3("Please enter an email!"),
    CHANGE_SLOGAN("Slogan changed successfully!"),
    CHANGE_SLOGAN_ERROR1("Please enter a slogan! (empty field)"),
    REMOVE_SLOGAN("slogan removed successfully!"),
    CANCEL("process canceled!");
    private final String message;
    ProfileMenuMessages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
