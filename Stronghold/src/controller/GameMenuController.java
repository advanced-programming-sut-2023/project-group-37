package controller;

import model.game.Game;
import model.user.User;
import view.enums.Message;

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

    public Message showMap(Matcher matcher) {
        return null;
    }

    public Message showPopularity() {
        return null;
    }

    public Message showPopularityFactors() {
        return null;
    }

    public Message showFoodList() {
        return null;
    }

    public Message setFoodRate(Matcher matcher) {
        return null;
    }

    public Message showFoodRate() {
        return null;
    }

    public Message setTaxRate(Matcher matcher) {
        return null;
    }

    public Message showTaxRate() {
        return null;
    }

    public Message setFearRate(Matcher matcher) {
        return null;
    }

    public Message showFearRate() {
        return null;
    }

    public Message dropBuilding(Matcher matcher) {
        return null;
    }

    public Message selectBuilding(Matcher matcher) {
        return null;
    }

    public Message selectUnit(Matcher matcher) {
        return null;
    }

    public Message enterShopMenu() {
        ShopMenuController.setCurrentUser(currentUser);
        return Message.ENTERED_SHOP_MENU;
    }

    public Message enterTradeMenu() {
        TradeMenuController.setCurrentUser(currentUser);
        return Message.ENTERED_TRADE_MENU;
    }

    public Message createUnit(Matcher matcher) {
        return null;
    }

    public Message setTexture(Matcher matcher) {
        return null;
    }

    public Message setRectangleTextures(Matcher matcher) {
        return null;
    }

    public Message clearTexture(Matcher matcher) {
        return null;
    }

    public Message dropTree(Matcher matcher) {
        return null;
    }

    public Message dropRock(Matcher matcher) {
        return null;
    }

}
