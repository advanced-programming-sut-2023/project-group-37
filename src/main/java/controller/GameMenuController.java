package controller;

import model.game.Game;
import model.game.Government;
import view.enums.Message;

import java.util.regex.Matcher;

public class GameMenuController {
    private static Game game;
    private final MapMenuController mapMenuController;
    private final ShopMenuController shopMenuController;
    private final TradeMenuController tradeMenuController;
    private final BuildingMenuController buildingMenuController;
    private final UnitMenuController unitMenuController;
    private Government government;

    public GameMenuController(MapMenuController mapMenuController, ShopMenuController shopMenuController,
                              TradeMenuController tradeMenuController, BuildingMenuController buildingMenuController,
                              UnitMenuController unitMenuController) {
        this.mapMenuController = mapMenuController;
        this.shopMenuController = shopMenuController;
        this.tradeMenuController = tradeMenuController;
        this.buildingMenuController = buildingMenuController;
        this.unitMenuController = unitMenuController;
    }
    public void setGovernment(Government government) {
        this.government = government;
        shopMenuController.setGovernment(government);
        tradeMenuController.setGovernment(government);
        mapMenuController.setGovernment(government);
        unitMenuController.setGovernment(government);
        buildingMenuController.setGovernment(government);
    }

    public static void setGame(Game game) {
        GameMenuController.game = game;
        TradeMenuController.setGame(game);
        MapMenuController.setGame(game);
        UnitMenuController.setGame(game);
        BuildingMenuController.setGame(game);
    }

    public String showMap(int x, int y) {
        if (x >= game.getMap().getSize() || x < 0 || y >= game.getMap().getSize() || y < 0)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        return mapMenuController.showMap(x,y);
    }

    public String enterShopMenu() {
        if (government.hasShop())
            return Message.ENTERED_SHOP_MENU.toString();
        return Message.MARKET_NOT_EXISTS.toString();
    }

    public String showPopularity() {
        return null;
    }

    public String showPopularityFactors() {
        return null;
    }

    public String showFoodList() {
        return null;
    }

    public String setFoodRate(Matcher matcher) {
        return null;
    }

    public String showFoodRate() {
        return null;
    }

    public String setTaxRate(Matcher matcher) {
        return null;
    }

    public String showTaxRate() {
        return null;
    }

    public String setFearRate(Matcher matcher) {
        return null;
    }

    public String  showFearRate() {
        return null;
    }

    public String dropBuilding(Matcher matcher) {
        return null;
    }

    public String selectBuilding(Matcher matcher) {
        return null;
    }

    public String  selectUnit(Matcher matcher) {
        return null;
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
