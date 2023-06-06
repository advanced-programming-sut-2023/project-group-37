package controller.viewControllers;

public class ChangeMenuController {
    private static final ChangeMenuController changeMenuController;
    private String promptText;


    static {
        changeMenuController = new ChangeMenuController();
    }

    public static ChangeMenuController getInstance() {
        return changeMenuController;
    }

    public String getPromptText() {
        return promptText;
    }

    public void setPromptText(String promptText) {
        this.promptText = promptText;
    }

    public void changeUsername(String text) {
    }

    public void changePassword(String text) {
    }

    public void changeSlogan(String text) {
    }

    public void changeEmial(String text) {
    }

    public void changeNickname(String text) {
    }
}
