package view.enums;

public enum Error {
    // Multi Menu Errors :
    NECESSARY_FIELD("Require field!"),
    INCOMPATIBLE_PASSWORDS("Incompatible passwords!"),
    EMAIL_ALREADY_EXISTS("Email already exists!"),

    // RegisterMenu Errors :
    USERNAME_ALREADY_EXISTS("Username already exists"),
    INCORRECT_USERNAME_FORM("Incorrect username format!"),
    INCORRECT_EMAIL_FORM("Incorrect email format!"),
    WEAK_PASSWORD("Weak password!"),
    INCORRECT_CAPTCHA("Incorrect captcha entered!");
    private final String errorMessage;

    Error(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return this.errorMessage;
    }
}
