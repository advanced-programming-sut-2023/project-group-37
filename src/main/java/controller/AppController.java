package controller;

import javafx.stage.Stage;
import model.game.Map;
import model.user.User;
import view.enums.Result;
import view.menus.*;

public class AppController {
    private final static AppController appController;
    private final Stage stage;

    static {
        appController = new AppController();
    }

    public static AppController getInstance() {
        return appController;
    }

    private AppController() {

    }

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

    {
        this.stage = new Stage();

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

    public void runLoginMenu() throws Exception {
        User.loadUsersFromFile();
        User loggedInUser = User.loadStayLoggedIn();
        Map.loadMaps();

        if (loggedInUser != null) {
            MainMenuController.setCurrentUser(loggedInUser);

            mainMenu.start(this.stage);
        }

        loginMenu.start(this.stage);
    }

    public void runMenu(Result result) throws Exception {
        switch (result) {
            case ENTER_LOGIN_MENU -> this.loginMenu.start(this.stage);
            case ENTER_REGISTER_MENU -> this.registerMenu.start(this.stage);
            case GO_FOR_CAPTCHA -> this.captchaMenu.start(this.stage);
            case ENTER_PROFILE_MENU -> this.profileMenu.start(this.stage);
            case ENTER_MAIN_MENU -> this.mainMenu.start(this.stage);
            case ENTER_GAME_MENU -> this.gameMenu.start(this.stage);

            // todo : how to show game parts menu ??
        }
    }
}