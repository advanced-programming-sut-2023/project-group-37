package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import view.enums.Result;
import view.menus.*;

public class AppController extends Application{
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        AppController.stage = stage;
        loadApp();
    }

    private static final LoginMenu loginMenu;
    private static final RegisterMenu registerMenu;
    private static final CaptchaMenu captchaMenu;
    private static final MainMenu mainMenu;
    private static final ProfileMenu profileMenu;
    private static final GameMenu gameMenu;
    private static final BuildingMenu buildingMenu;
    private static final MapMenu mapMenu;
    private static final ShopMenu shopMenu;
    private static final TradeMenu tradeMenu;
    private static final UnitMenu unitMenu;

    static {
        loginMenu = new LoginMenu();
        registerMenu = new RegisterMenu();
        captchaMenu = new CaptchaMenu();
        mainMenu = new MainMenu();
        profileMenu = new ProfileMenu();
        gameMenu = new GameMenu();
        buildingMenu = new BuildingMenu();
        mapMenu = new MapMenu();
        shopMenu = new ShopMenu();
        tradeMenu = new TradeMenu();
        unitMenu = new UnitMenu();
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
        registerMenu.start(stage);
    }

    public static void runMenu(Result result) throws Exception {
        switch (result) {
            case ENTER_LOGIN_MENU -> loginMenu.start(stage);
            case ENTER_REGISTER_MENU -> registerMenu.start(stage);
            case GO_FOR_CAPTCHA -> captchaMenu.start(stage);
            case ENTER_PROFILE_MENU -> profileMenu.start(stage);
            case ENTER_MAIN_MENU -> mainMenu.start(stage);
            case ENTER_GAME_MENU -> gameMenu.start(stage);

            // todo : how to show game parts menu ??
        }
    }
}