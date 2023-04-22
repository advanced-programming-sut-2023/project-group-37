package view.enums.messages;

public enum MainMenuMessages {
    ENTERED_PROFILE_MENU("entered profile menu successfully!"),
    GAME_STARTED("game started successfully!"),//todo
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
