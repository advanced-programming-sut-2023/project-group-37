package controller;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.Storage;
import model.game.*;
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
        buildingMenuController.setCurrentGovernment(government);
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

        return mapMenuController.showMap(x, y);
    }

    public String enterShopMenu() {
        if (government.hasMarket())
            return Message.ENTERED_SHOP_MENU.toString();
        return Message.MARKET_NOT_EXISTS.toString();
    }

    public String showPopularity() {
        return Integer.toString(government.getPopularity());
    }

    public String showPopularityFactors() {
        return "There are four popularity factors as below:\n" +
                "1.Food you give!\n" +
                "2.Tax you take!\n" +
                "3.Religion you propagate!\n" +
                "4.Fear you create!";
    }

    public String showFoodList() {
        String list = "";
        int counter = 1;
        for (Storage storage : government.getGranary()) {
            list += "Foods in storage number " + counter + ":\n";
            list += storage.getFoodNames() + "\n";
            counter++;
        }
        return list.trim();
    }

    public String setFoodRate(Matcher matcher) {
        if (!(-2 <= Integer.parseInt(matcher.group("rateNumber")) &&
                Integer.parseInt(matcher.group("rateNumber")) <= 2)) {
            return Message.INVALID_FOOD_RATE.toString();
        }
        government.setFoodRate(Integer.parseInt(matcher.group("rateNumber")));
        //TODO : changes in popularity
        return Message.FOOD_RATE_CHANGED.toString();
    }

    public String showFoodRate() {
        return Integer.toString(government.getFoodRate());
    }

    public String setTaxRate(Matcher matcher) {
        if (!(-3 <= Integer.parseInt(matcher.group("rateNumber")) &&
                Integer.parseInt(matcher.group("rateNumber")) <= 8)) {
            return Message.INVALID_TAX_RATE.toString();
        }
        government.setTaxRate(Integer.parseInt(matcher.group("rateNumber")));
        //TODO : changes in popularity

        return Message.TAX_RATE_CHANGED.toString();
    }

    public String showTaxRate() {
        return Integer.toString(government.getTaxRate());
    }

    public String setFearRate(Matcher matcher) {
        return null;
    }

    public String showFearRate() {
        return null;
    }

    public String dropBuilding(Matcher matcher) {
        return null;
    }

    public String selectBuilding(Matcher matcher) {
        return null;
    }

    public String selectUnit(int x, int y) {
        Tile tile = game.getMap().getTileByLocation(x, y);

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

    public String createUnit(String type, int count) {
        TroopType troopType = TroopType.getTroopTypeByName(type);
        if (troopType == null)
            return Message.TYPE_NOT_EXISTS.toString();

        Building building;
        if (troopType.getTrainingCamp() == BuildingType.BARRACKS) {
            building = government.getUniqueBuilding(BuildingType.BARRACKS);
            if (building == null)
                return Message.BARRACKS_NOT_EXISTS.toString();
        } else {
            building = government.getUniqueBuilding(BuildingType.MERCENARY_POST);
            if (building == null)
                return Message.MERCENARY_POST_NOT_EXISTS.toString();
        }


        Troop troop = new Troop(government, troopType, MultiMenuFunctions.getNearestPassableTile(building.getLocation(), game.getMap()));

        if (count < 0)
            return Message.INVALID_AMOUNT.toString();

        government.addTroops(troop, count);
        return Message.CREATE_UNIT_SUCCESSFUL.toString();
    }

    public String setTexture(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("y"));//reverse because of the array!!
        int y = Integer.parseInt(matcher.group("x"));
        if (game.getMap().getTileByLocation(x, y) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        if (Texture.getTextureByName(MultiMenuFunctions.deleteQuotations(matcher.group("type"))) == null)
            return Message.INVALID_TEXTURE_NAME.toString();

        if (game.getMap().getField()[x][y].getBuilding() != null || game.getMap().getField()[x][y].getPeople().size() != 0)
            return Message.TEXTURE_CHANGE_ERROR.toString();

        game.getMap().getField()[x][y].changeTexture(Texture.getTextureByName
                (MultiMenuFunctions.deleteQuotations(matcher.group("type"))));
        return Message.TEXTURE_CHANGED_SUCCESSFULLY.toString();
    }

    public String setRectangleTextures(Matcher matcher) {
        int x1 = Integer.parseInt(matcher.group("y1"));
        int y1 = Integer.parseInt(matcher.group("x1"));
        int x2 = Integer.parseInt(matcher.group("y2"));
        int y2 = Integer.parseInt(matcher.group("x2"));

        if (game.getMap().getTileByLocation(x1, y1) == null || game.getMap().getTileByLocation(x2, y2) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        if (game.getMap().AreaContainsSomething(x1, y1, x2, y2))
            return Message.AREA_NOT_EMPTY.toString();

        if (Texture.getTextureByName(MultiMenuFunctions.deleteQuotations(matcher.group("type"))) == null)
            return Message.INVALID_TEXTURE_NAME.toString();

        for (int i = y1; i <= y2; i++) {
            for (int j = x1; j <= x2; j++) {
                game.getMap().getField()[i][j].changeTexture(Texture.getTextureByName
                        (MultiMenuFunctions.deleteQuotations(matcher.group("type"))));
            }
        }

        return Message.TEXTURE_CHANGED_SUCCESSFULLY.toString();
    }

    public String clearTexture(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("y"));
        int y = Integer.parseInt(matcher.group("x"));

        if (game.getMap().getTileByLocation(x, y) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        game.getMap().getTileByLocation(x, y).changeTexture(Texture.GROUND);//default
        game.getMap().getTileByLocation(x, y).getPeople().clear();
        game.getMap().getTileByLocation(x, y).removeBuilding();

        return Message.CLEAR_SUCCESSFUL.toString();
    }

    public String dropTree(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("y"));
        int y = Integer.parseInt(matcher.group("x"));

        if (game.getMap().getTileByLocation(x, y) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        if (game.getMap().getField()[x][y].getBuilding() != null || game.getMap().getField()[x][y].getPeople().size() != 0
                || !game.getMap().getField()[x][y].getTexture().canHaveTree())
            return Message.DROP_TREE_ERROR.toString();
        game.getMap().getField()[x][y].changeTexture(Texture.getTextureByName
                (MultiMenuFunctions.deleteQuotations(matcher.group("type"))));
        return Message.DROP_TREE.toString();
    }

    public String dropRock(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("y"));
        int y = Integer.parseInt(matcher.group("x"));

        if (game.getMap().getTileByLocation(x, y) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        if (game.getMap().getField()[x][y].getBuilding() != null || game.getMap().getField()[x][y].getPeople().size() != 0)
            return Message.DROP_ROCK_ERROR.toString();

        game.getMap().getField()[x][y].changeTexture(Texture.ROCK);
        return Message.DROP_ROCK.toString();
    }

}
