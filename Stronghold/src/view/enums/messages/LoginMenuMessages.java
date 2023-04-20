package view.enums.messages;

public enum LoginMenuMessages {
    CANCEL("The process canceled successfully!"),
    USER_NOT_EXISTS("Username and password didn’t match!"),
    INCORRECT_PASSWORD("Username and password didn’t match!"),
    WEAK_PASSWORD("Weak password!"),
    INCOMPATIBLE_PASSWORDS("Incompatible passwords!"),
    ASK_QUESTION("Please answer following question to change your password"),
    LOGIN_SUCCESSFUL("user logged in successfully!"),
    INCORRECT_ANSWER("Incorrect answer!"),
    ENTER_NEW_PASSWORD("Please Enter your new password"),
    ENTER_NEW_PASSWORD_AGAIN("Please enter your new password again"),
    CHANGE_PASSWORD_SUCCESSFUL("password changed successfully!");

    private final String output;
    private String secondOutput = "";

    LoginMenuMessages(String output) {
        this.output = output;
    }

    LoginMenuMessages(String output, String secondOutput) {
        this.output = output;
        this.secondOutput = secondOutput;
    }

    @Override
    public String toString() {
        return output;
    }

    public String continueOutput(String input) {
        return output + input + secondOutput;
    }
}
