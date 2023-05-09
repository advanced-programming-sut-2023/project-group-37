package controller;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.game.Game;
import model.game.Government;
import model.game.Tile;
import model.people.MilitaryUnit;
import model.people.Troop;
import model.people.TroopType;
import view.enums.Message;

import java.util.ArrayList;
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

    public String  selectUnit(int x, int y) {
        Tile tile = game.getMap().getTileByLocation(x,y);

        if (tile == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        ArrayList<MilitaryUnit> unit = tile.getMilitaryUnits();
        ArrayList<MilitaryUnit> myUnit = new ArrayList<>();
        for (MilitaryUnit militaryUnit : unit) {
            if (militaryUnit.getLoyalty() == government)
                myUnit.add(militaryUnit);
        }

        if (myUnit.size() == 0)
            return Message.UNIT_NOT_EXISTS.toString();

        unitMenuController.setUnit(myUnit, tile);
        return Message.UNIT_SELECTED.toString();
    }

    public String  createUnit(String type, int count) {
        TroopType troopType = TroopType.getTroopTypeByName(type);
        if (troopType == null)
            return Message.TYPE_NOT_EXISTS.toString();

        Building building;
        if (troopType.getTrainingCamp() == BuildingType.BARRACKS) {
            building = government.getUnicBuilding(BuildingType.BARRACKS);
            if (building == null)
                return Message.BARRACKS_NOT_EXISTS.toString();
        }
        else {
            building = government.getUnicBuilding(BuildingType.MERCENARY_POST);
            if (building == null)
                return Message.MERCENARY_POST_NOT_EXISTS.toString();
        }


        Troop troop = new Troop(government, troopType, MultiMenuFunctions.getNearestPassableTile(building.getLocation(), game.getMap()));

        if (count < 0)
            return Message.INVALID_AMOUNT.toString();

        government.addTroops(troop, count);
        return Message.CREATE_UNIT_SUCCESSFUL.toString();
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
