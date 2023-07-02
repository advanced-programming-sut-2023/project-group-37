package controller.viewControllers;

import view.enums.Message;

public class ChangePasswordMenuController {
    private static final ChangePasswordMenuController changePasswordMenuController;
    private static final ProfileMenuController profileMenuController = ProfileMenuController.getInstance();


    static {
        changePasswordMenuController = new ChangePasswordMenuController();
    }

    public static ChangePasswordMenuController getInstance() {
        return changePasswordMenuController;
    }

    public Message changePassword(String oldPass, String newPass) {
        return profileMenuController.changePass(oldPass, newPass);
    }
}
