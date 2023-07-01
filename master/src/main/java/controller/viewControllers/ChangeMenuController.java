package controller.viewControllers;

import view.enums.Message;

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

    public Message changeUsername(String text) {
        return profileMenuController.changeUsername(text); //errors dynamically checked no need to check here!
    }

    public Message changeSlogan(String text) {
        return profileMenuController.changeSlogan(text);
    }

    public Message changeEmail(String text) {
        return profileMenuController.changeEmail(text);
    }

    public Message changeNickname(String text) {
        return profileMenuController.changeNickName(text);
    }
}
