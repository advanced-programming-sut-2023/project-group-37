package view.enums.messages;

public enum RegisterMenuMessages {
    CANCEL("The process canceled successfully!"),
    EMPTY_FIELD("Please fill all necessary fields"),
    INCORRECT_USERNAME_FORM("Incorrect username format!"),
    WEAK_PASSWORD("Weak password!"),
    INCOMPATIBLE_PASSWORDS("Incompatible passwords!"),
    INCORRECT_EMAIL_FORM("Incorrect email format!"),
    RANDOM_PASSWORD("Your random password is: " , "\n" + "Please re-enter your password here:"),
    REENTER_AGAIN("Please re-enter your password again:"),
    ASK_FOR_SECURITY_QUESTION("Pick your security question: 1. What is my father’s name? 2. What\n" +
            "was my first pet’s name? 3. What is my mother’s last name?"),
    INCORRECT_QUESTION_NUMBER("Please select question-number between 1,2,3"),
    INCOMPATIBLE_ANSWERS("Incompatible answers!"),
    REGISTER_SUCCESSFUL("user created successfully!"),
    NOT_LOGGED_IN("No loggedIn user!"),
    LOGOUT_SUCCESSFUL("user logged out successfully!");

    private final String output;
    private String secondOutput = "";

    RegisterMenuMessages(String output) {
        this.output = output;
    }

    RegisterMenuMessages(String output, String secondOutput) {
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