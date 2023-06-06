package controller.viewControllers;

public class ChangeMenuController {
    private static final ChangeMenuController changeMenuController;
    private static final ProfileMenuController profileMenuController = ProfileMenuController.getInstance();
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
        profileMenuController.changeUsername(text); //errors dynamically checked no need to check here!
    }

    public void changePassword(String text) {
        //profileMenuController.changePassword(text);
    }

    public void changeSlogan(String text) {
        profileMenuController.changeSlogan(text);
    }

    public void changeEmail(String text) {
        profileMenuController.changeEmail(text);
    }

    public void changeNickname(String text) {
        profileMenuController.changeNickName(text);
    }
}
