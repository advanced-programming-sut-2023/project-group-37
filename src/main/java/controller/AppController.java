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

    private final RegisterMenu registerMenu;
    private final LoginMenu loginMenu;
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

        registerMenu = new RegisterMenu();
        loginMenu = new LoginMenu();
        mainMenu = new MainMenu();
        profileMenu = new ProfileMenu();
        gameMenu = new GameMenu();
        buildingMenu = new BuildingMenu();
        mapMenu = new MapMenu();
        shopMenu = new ShopMenu();
        tradeMenu = new TradeMenu();
        unitMenu = new UnitMenu();
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
            case ENTER_LOGIN_MENU -> loginMenu.start(this.stage);
            case ENTER_REGISTER_MENU -> registerMenu.start(this.stage);
            case ENTER_PROFILE_MENU -> profileMenu.start(this.stage);
            case ENTER_MAIN_MENU -> mainMenu.start(this.stage);
            case ENTER_GAME_MENU -> gameMenu.start(this.stage);

            // todo : how to show game parts menu ??
        }
    }
}