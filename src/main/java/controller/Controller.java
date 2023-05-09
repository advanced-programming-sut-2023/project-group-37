package controller;

import model.game.Map;
import model.user.User;
import view.enums.Result;
import view.menus.*;

import java.util.Scanner;

public class Controller {

    // controllers :
    private final LoginMenuController loginMenuController = new LoginMenuController();
    private final RegisterMenuController registerMenuController = new RegisterMenuController();
    private final ProfileMenuController profileMenuController = new ProfileMenuController();
    private final MapMenuController mapMenuController = new MapMenuController();
    private final ShopMenuController shopMenuController = new ShopMenuController();
    private final TradeMenuController tradeMenuController = new TradeMenuController();
    private final BuildingMenuController buildingMenuController = new BuildingMenuController();
    private final UnitMenuController unitMenuController = new UnitMenuController();
    private final GameMenuController gameMenuController = new GameMenuController(mapMenuController, shopMenuController,
            tradeMenuController, buildingMenuController, unitMenuController);
    private final MainMenuController mainMenuController = new MainMenuController(gameMenuController);


    // menus :
    private final Scanner scanner;
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
        scanner = new Scanner(System.in);
        registerMenu = new RegisterMenu(scanner, registerMenuController);
        loginMenu = new LoginMenu(scanner, loginMenuController);
        mainMenu = new MainMenu(scanner, mainMenuController);
        profileMenu = new ProfileMenu(scanner, profileMenuController);
        gameMenu = new GameMenu(scanner, gameMenuController);
        buildingMenu = new BuildingMenu(scanner, buildingMenuController);
        mapMenu = new MapMenu(scanner, mapMenuController);
        shopMenu = new ShopMenu(scanner, shopMenuController);
        tradeMenu = new TradeMenu(scanner, tradeMenuController);
        unitMenu = new UnitMenu(scanner, unitMenuController);
    }

    public void run() {
        User.loadUsersFromFile();
        User loggedInUser = User.loadStayLoggedIn();
        Map.loadMaps();

        if (loggedInUser != null) {
            MainMenuController.setCurrentUser(loggedInUser);

            while (true) {
                if (runMainMenu()) return;
            }
        }

        Result result;

        if ((result = this.loginMenu.run()) == Result.EXIT)
            return;

        while (true) {
            switch (result) {
                case LOGGED_IN -> {
                    if (runMainMenu()) return;
                }

                case ENTER_REGISTER_MENU -> {
                    if (this.registerMenu.run() == Result.EXIT)
                        return;
                    this.run();
                    return;
                }
            }
        }
    }

    private boolean runMainMenu() {
        switch (this.mainMenu.run()) {
            case ENTER_PROFILE_MENU -> this.profileMenu.run();
            case ENTER_GAME_MENU -> runGameMenu();
            // TODO: case map edition menu!
            case ENTER_LOGIN_MENU -> {
                this.run();
                return true;
            }
            case EXIT -> {
                return true;
            }
        }
        return false;
    }

    private void runGameMenu() {
        Result result;

        while (true) {
            result = this.gameMenu.run();
            switch (result) {
                case ENTER_BUILDING_MENU -> this.buildingMenu.run(scanner);
                case ENTER_MAP_MENU -> this.mapMenu.run(scanner);
                case ENTER_SHOP_MENU -> this.shopMenu.run(scanner);
                case ENTER_TRADE_MENU -> this.tradeMenu.run(scanner);
                case ENTER_UNIT_MENU -> this.unitMenu.run(scanner);
                case END_GAME -> {
                    return;
                }
            }
        }
    }
}