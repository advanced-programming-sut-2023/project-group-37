package controller;

import model.game.Game;
import model.game.Map;
import model.user.User;
import view.enums.messages.GameMenuMessages;

import java.util.regex.Matcher;

public class GameMenuController {
    private static Game game;
    private static User currentUser;

//    public static void setGame(Map map) {
//        game = new Game(map);
//    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public GameMenuMessages showMap(Matcher matcher) {
        return null;
    }

    public GameMenuMessages showPopularity() {
        return null;
    }

    public GameMenuMessages showPopularityFactors() {
        return null;
    }

    public GameMenuMessages showFoodList() {
        return null;
    }

    public GameMenuMessages setFoodRate(Matcher matcher) {
        return null;
    }

    public GameMenuMessages showFoodRate() {
        return null;
    }

    public GameMenuMessages setTaxRate(Matcher matcher) {
        return null;
    }

    public GameMenuMessages showTaxRate() {
        return null;
    }

    public GameMenuMessages setFearRate(Matcher matcher) {
        return null;
    }

    public GameMenuMessages showFearRate() {
        return null;
    }

    public GameMenuMessages dropBuilding(Matcher matcher) {
        return null;
    }

    public GameMenuMessages selectBuilding(Matcher matcher) {
        return null;
    }

    public GameMenuMessages selectUnit(Matcher matcher) {
        return null;
    }

    public GameMenuMessages enterShopMenu() {
        ShopMenuController.setCurrentUser(currentUser);
        return GameMenuMessages.ENTERED_SHOP_MENU;
    }

    public GameMenuMessages enterTradeMenu() {
        TradeMenuController.setCurrentUser(currentUser);
        return GameMenuMessages.ENTERED_TRADE_MENU;
    }

    public GameMenuMessages createUnit(Matcher matcher) {
        return null;
    }

    public GameMenuMessages setTexture(Matcher matcher) {
        return null;
    }

    public GameMenuMessages setRectangleTextures(Matcher matcher) {
        return null;
    }

    public GameMenuMessages clearTexture(Matcher matcher) {
        return null;
    }

    public GameMenuMessages dropTree(Matcher matcher) {
        return null;
    }

    public GameMenuMessages dropRock(Matcher matcher) {
        return null;
    }

}
