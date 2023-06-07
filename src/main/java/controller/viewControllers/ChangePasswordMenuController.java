package controller.viewControllers;

import view.menus.ChangePasswordMenu;

public class ChangePasswordMenuController {
    private static final ChangePasswordMenuController changePasswordMenuController;
    private static final ProfileMenuController profileMenuController = ProfileMenuController.getInstance();


    static {
        changePasswordMenuController = new ChangePasswordMenuController();
    }

    public static ChangePasswordMenuController getInstance() {
        return changePasswordMenuController;
    }
}
