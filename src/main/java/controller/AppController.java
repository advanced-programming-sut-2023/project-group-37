package controller;

import javafx.stage.Stage;
import view.enums.Result;
import view.menus.*;

public class AppController {
    private static AppController appController;
    private final Stage stage;
    private final LoginMenu loginMenu;
    private final RegisterMenu registerMenu;
    private final CaptchaMenu captchaMenu;
    private final MainMenu mainMenu;
    private final ProfileMenu profileMenu;
    private final GameMenu gameMenu;
    private final BuildingMenu buildingMenu;
    private final MapMenu mapMenu;
    private final ShopMenu shopMenu;
    private final TradeMenu tradeMenu;
    private final UnitMenu unitMenu;

    public AppController(Stage stage) {
        this.stage = stage;

        if (appController == null)
            appController = this;

        this.loginMenu = new LoginMenu();
        this.registerMenu = new RegisterMenu();
        this.captchaMenu = new CaptchaMenu();
        this.mainMenu = new MainMenu();
        this.profileMenu = new ProfileMenu();
        this.gameMenu = new GameMenu();
        this.buildingMenu = new BuildingMenu();
        this.mapMenu = new MapMenu();
        this.shopMenu = new ShopMenu();
        this.tradeMenu = new TradeMenu();
        this.unitMenu = new UnitMenu();
    }

    public static AppController getInstance() {
        return appController;
    }

    public void loadApp() throws Exception {
//        User.loadUsersFromFile();
//        User loggedInUser = User.loadStayLoggedIn();
//        Map.loadMaps();
//
//        if (loggedInUser != null) {
//            MainMenuController.setCurrentUser(loggedInUser);
//
//            mainMenu.start(stage);
//        }
//
//        loginMenu.start(stage);
        runMenu(Result.ENTER_REGISTER_MENU);
    }

    public void runMenu(Result result) throws Exception {
        switch (result) {
            case ENTER_LOGIN_MENU -> this.loginMenu.start(stage);
            case ENTER_REGISTER_MENU -> this.registerMenu.start(stage);
            case GO_FOR_CAPTCHA -> this.captchaMenu.start(stage);
            case ENTER_PROFILE_MENU -> this.profileMenu.start(stage);
            case ENTER_MAIN_MENU -> this.mainMenu.start(stage);
            case ENTER_GAME_MENU -> this.gameMenu.start(stage);

            // todo : how to show game parts menu ??
        }
    }
}