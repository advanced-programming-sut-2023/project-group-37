package controller;

import view.enums.Results;
import view.menus.*;

import java.util.Scanner;

public class Controller {
    private static final Scanner scanner = new Scanner(System.in);
    private final RegisterMenu registerMenu = new RegisterMenu(scanner);
    private final LoginMenu loginMenu = new LoginMenu(scanner);
    private final MainMenu mainMenu = new MainMenu(scanner);
    private final ProfileMenu profileMenu = new ProfileMenu(scanner);
    private final GameMenu gameMenu = new GameMenu(scanner);
    private final BuildingMenu buildingMenu = new BuildingMenu(scanner);
    private final MapMenu mapMenu = new MapMenu(scanner);
    private final ShopMenu shopMenu = new ShopMenu(scanner);
    private final TradeMenu tradeMenu = new TradeMenu(scanner);
    private final UnitMenu unitMenu = new UnitMenu(scanner);
    private Results result;

    public void run() {
        runLoginMenu();
    }

    private void runLoginMenu() {
        result = loginMenu.run();

        switch (result) {
            case ENTER_REGISTER_MENU -> runRegisterMenu();
            case LOGGED_IN -> runMainMenu();
        }
    }

    private void runRegisterMenu() {
        result = registerMenu.run();

        switch (result) {
            case ENTER_LOGIN_MENU, USER_CREATED -> runLoginMenu();
        }
    }

    private void runMainMenu() {
        result = mainMenu.run();

        switch (result) {
            case ENTER_LOGIN_MENU -> runLoginMenu();
            case ENTER_PROFILE_MENU -> runProfileMenu();
            case ENTER_GAME_MENU -> runGameMenu();
        }
    }

    private void runProfileMenu() {
        result = profileMenu.run();

        switch (result) {
            case ENTER_LOGIN_MENU -> runLoginMenu();
            case ENTER_MAIN_MENU -> runMainMenu();
        }
    }

    private void runGameMenu() {
        loop:
        while (true) {
            result = gameMenu.run();

            switch (result) {
                case ENTER_BUILDING_MENU -> runBuildingMenu();
                case ENTER_MAP_MENU -> runMapMenu();
                case ENTER_SHOP_MENU -> runSHopMenu();
                case ENTER_TRADE_MENU -> runTradeMenu();
                case ENTER_UNIT_MENU -> runUnitMenu();
                case END_GAME -> {
                    runMainMenu();
                    break loop;
                }
            }
        }
    }

    private void runBuildingMenu() {
        result = buildingMenu.run();
    }

    private void runMapMenu() {
        result = mapMenu.run();
    }

    private void runSHopMenu() {
        result = shopMenu.run();
    }

    private void runTradeMenu() {
        result = tradeMenu.run();
    }

    private void runUnitMenu() {
        result = unitMenu.run();
    }

}