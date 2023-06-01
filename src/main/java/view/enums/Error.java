package view.enums;

public enum Error {
    // Multi Menu Errors :
    NECESSARY_FIELD("This field is necessary!"),
    INCOMPATIBLE_PASSWORDS("Incompatible passwords!"),

    // RegisterMenu Errors :
    INCORRECT_USERNAME_FORM("Incorrect username format!"),
    INCORRECT_EMAIL_FORM("Incorrect email format!"),
    WEAK_PASSWORD("Weak password!"),
    ;
    private final String errorMessage;

    Error(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return this.errorMessage;
    }
}
