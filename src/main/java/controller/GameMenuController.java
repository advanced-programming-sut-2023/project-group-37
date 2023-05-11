package controller;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.DefensiveBuilding;
import model.buildings.DefensiveBuildingType;
import model.game.*;
import model.people.Person;
import view.enums.Message;

import java.util.regex.Matcher;

public class GameMenuController {
    private Game currentGame;
    private final MapMenuController mapMenuController;
    private final ShopMenuController shopMenuController;
    private final TradeMenuController tradeMenuController;
    private final BuildingMenuController buildingMenuController;
    private final UnitMenuController unitMenuController;
    private Government currentGovernment;

    public GameMenuController(MapMenuController mapMenuController, ShopMenuController shopMenuController,
                              TradeMenuController tradeMenuController, BuildingMenuController buildingMenuController,
                              UnitMenuController unitMenuController) {
        this.mapMenuController = mapMenuController;
        this.shopMenuController = shopMenuController;
        this.tradeMenuController = tradeMenuController;
        this.buildingMenuController = buildingMenuController;
        this.unitMenuController = unitMenuController;
    }

    public void setCurrentGovernment(Government currentGovernment) {
        this.currentGovernment = currentGovernment;
        shopMenuController.setGovernment(currentGovernment);
        tradeMenuController.setGovernment(currentGovernment);
        mapMenuController.setGovernment(currentGovernment);
        unitMenuController.setGovernment(currentGovernment);
        buildingMenuController.setCurrentGovernment(currentGovernment);
    }

    public void setCurrentGame(Game currentGame) {
        // TODO: it was static!
        this.currentGame = currentGame;
        TradeMenuController.setGame(currentGame);
        MapMenuController.setGame(currentGame);
        UnitMenuController.setGame(currentGame);
        // TODO: it was static!
        this.buildingMenuController.setCurrentGame(currentGame);
    }

    public String showMap(int x, int y) {
        if (x >= currentGame.getMap().getSize() || x < 0 || y >= currentGame.getMap().getSize() || y < 0)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        return mapMenuController.showMap(x, y);
    }

    public String enterShopMenu() {
        if (currentGovernment.hasMarket())
            return Message.ENTERED_SHOP_MENU.toString();
        return Message.MARKET_NOT_EXISTS.toString();
    }

    public int showPopularity() {
        return currentGovernment.getPopularity();
    }

    public String showPopularityFactors() {
        return null;
    }

    public String showFoodList() {
        StringBuilder result = new StringBuilder();

        for (Item item : Item.values())
            if (item.getCategory() == ItemCategory.FOODS)
                result.append(item.getName()).append(": ")
                        .append(this.currentGovernment.getItemAmount(item, this.currentGovernment.getGranary()))
                        .append("\n");

        return result.toString().trim();
    }

    public String setFoodRate(Matcher matcher) {
        int rate = Integer.parseInt(matcher.group("rateNumber"));

        if (rate < -2 || rate > 2)
            return Message.INVALID_FOOD_RATE.toString();

        this.currentGovernment.setFoodRate(rate);
        return Message.FOOD_RATE_SET + " " + rate;
    }

    public String showFoodRate() {
        return "Food rate: " + this.currentGovernment.getFoodRate();
    }

    public String setTaxRate(Matcher matcher) {
        int rate = Integer.parseInt(matcher.group("rateNumber"));

        if (rate < -3 || rate > 8)
            return Message.INVALID_TAX_RATE.toString();

        this.currentGovernment.setTaxRate(rate);
        return Message.TAX_RATE_SET + " " + rate;
    }

    public String showTaxRate() {
        return "Tax rate: " + this.currentGovernment.getTaxRate();
    }

    public String setFearRate(Matcher matcher) {
        int rate = Integer.parseInt(matcher.group("rateNumber"));

        if (rate < -5 || rate > 5)
            return Message.INVALID_FEAR_RATE.toString();

        this.currentGovernment.setFearRate(rate);
        return Message.FEAR_RATE_SET + " " + rate;
    }

    public String showFearRate() {
        return "Fear rate: " + this.currentGovernment.getTaxRate();
    }

    public String dropBuilding(Matcher matcher) {

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Tile tile;
        if ((tile = currentGame.getMap().getTileByLocation(x, y)) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        Object type = BuildingType.getBuildingTypeByName(matcher.group("type"));
        if (type == null)
            return Message.INVALID_BUILDING_TYPE.toString();

        // TODO: check territory if needed!
        // TODO: check if there is moat!
        if (!tile.getTexture().canHaveBuildingAndUnit())
            return Message.CANNOT_PLACE_BUILDING_ON_TEXTURE.toString();

        if (type instanceof BuildingType) {
            if ((type == BuildingType.APPLE_ORCHARD || type == BuildingType.DIARY_FARMER ||
                    type == BuildingType.HOPS_FARMER || type == BuildingType.WHEAT_FARMER) &&
                    tile.getTexture() != Texture.GRASS && tile.getTexture() != Texture.DENSE_MEADOW)
                return Message.CANNOT_PLACE_BUILDING_ON_TEXTURE.toString();

            if ((type == BuildingType.QUARRY && tile.getTexture() != Texture.ROCK) ||
                    (type == BuildingType.IRON_MINE && tile.getTexture() != Texture.IRON) ||
                    (type == BuildingType.PITCH_RIG && tile.getTexture() != Texture.OIL))
                return Message.CANNOT_PLACE_BUILDING_ON_TEXTURE.toString();
        }

        if (tile.getBuilding() != null)
            return Message.TILE_ALREADY_HAS_BUILDING.toString();

        tile.setBuilding(type instanceof BuildingType ? new Building(this.currentGovernment, tile, (BuildingType) type) :
                new DefensiveBuilding(this.currentGovernment, tile, (DefensiveBuildingType) type));

        int workersNeeded;
        if (type instanceof BuildingType && (workersNeeded = ((BuildingType) type).getWorkersNeeded()) > 0) {
            int assignedOperators = 0;
            for (Person person : this.currentGovernment.getPeople()) {
                if (person.getWorkplace() == null) {
                    person.setWorkplace(tile.getBuilding());
                    tile.getBuilding().assignOperator(person);
                    assignedOperators++;
                    if (assignedOperators == workersNeeded)
                        break;
                }
            }
        }
        // TODO: handle situation if building needs more workers!
        return Message.DROP_BUILDING_SUCCESS.toString();
    }

    public String selectBuilding(Matcher matcher) {

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Tile tile;

        if ((tile = currentGame.getMap().getTileByLocation(x, y)) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        // TODO: decide on it!
//        if (tile.getTerritory() != this.currentGovernment)
//            return Message.TILE_IS_NOT_YOURS.toString();

        Building building;

        if ((building = tile.getBuilding()) == null)
            return Message.NO_BUILDING_IN_TILE.toString();

        if (building.getLoyalty() != this.currentGovernment)
            return Message.BUILDING_NOT_YOURS.toString();

        this.buildingMenuController.setCurrentGame(this.currentGame);
        this.buildingMenuController.setCurrentBuilding(currentGame.getMap().getTileByLocation(x, y).getBuilding());
        this.buildingMenuController.setCurrentGovernment(this.currentGovernment);
        return building.getType().toString() + " selected!\n" + Message.ENTERED_BUILDING_MENU;
    }

    public String selectUnit(Matcher matcher) {
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
