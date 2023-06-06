package controller.viewControllers;

public class ChangeMenuController {
    private static final ChangeMenuController changeMenuController;

    static {
        changeMenuController = new ChangeMenuController();
    }

    public static ChangeMenuController getInstance() {
        return changeMenuController;
    }

}
