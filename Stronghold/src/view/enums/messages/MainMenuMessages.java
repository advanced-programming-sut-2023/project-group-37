package view.enums.messages;

public enum MainMenuMessages {
    LOGOUT_SUCCESSFUL("user logged out successfully!");

    private final String output;
    private String secondOutput = "";

    MainMenuMessages(String output) {
        this.output = output;
    }

    MainMenuMessages(String output, String secondOutput) {
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
