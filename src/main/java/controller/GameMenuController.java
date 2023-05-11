package controller;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.Storage;
import model.game.*;
import model.people.MilitaryUnit;
import model.people.Troop;
import model.people.TroopType;
import model.buildings.DefensiveBuilding;
import model.buildings.DefensiveBuildingType;
import model.people.Person;
import view.enums.Message;

import java.util.ArrayList;
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

    public String showPopularity() {
        return Integer.toString(currentGovernment.getPopularity());
    }

    public String showPopularityFactors() {
        return """
                There are four popularity factors as below:
                1.Food you give!
                2.Tax you take!
                3.Religion you propagate!
                4.Fear you create!""";
    }

    public String showFoodList() {
        StringBuilder list = new StringBuilder();
        int counter = 1;
        for (Storage storage : currentGovernment.getGranary()) {
            list.append("Foods in storage number ").append(counter).append(":\n");
            list.append(storage.getFoodNames()).append("\n");
            counter++;
        }
        return list.toString().trim();
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

    public String dropBuilding(Matcher matcher) { //TODO : decrease

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
        tile.setPassability(type == BuildingType.KILLING_PIT);
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

        this.buildingMenuController.setCurrentBuilding(currentGame.getMap().getTileByLocation(x, y).getBuilding());
        this.buildingMenuController.setCurrentGovernment(this.currentGovernment);
        return building.getType().toString() + " selected!\n" + Message.ENTERED_BUILDING_MENU;
    }

    public String selectUnit(int x, int y) {
        Tile tile = currentGame.getMap().getTileByLocation(x, y);

        if (tile == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        ArrayList<MilitaryUnit> unit = tile.getMilitaryUnits();
        ArrayList<MilitaryUnit> myUnit = new ArrayList<>();
        for (MilitaryUnit militaryUnit : unit) {
            if (militaryUnit.getLoyalty() == currentGovernment)
                myUnit.add(militaryUnit);
        }

        if (myUnit.size() == 0)
            return Message.UNIT_NOT_EXISTS.toString();

        unitMenuController.setUnit(myUnit, tile);
        return Message.UNIT_SELECTED.toString();
    }

    public String dropUnit(Matcher matcher) { // TODO : decrease
        String type = matcher.group("type");
        int count = Integer.parseInt(matcher.group("count"));
        int x = Integer.parseInt(matcher.group("x")), y = Integer.parseInt(matcher.group("y"));

        Tile tile;
        if ((tile = currentGame.getMap().getTileByLocation(x,y)) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        TroopType troopType = TroopType.getTroopTypeByName(type);
        if (troopType == null)
            return Message.TYPE_NOT_EXISTS.toString();

        Building building;
        if (troopType.getTrainingCamp() == BuildingType.BARRACKS) {
            building = currentGovernment.getUniqueBuilding(BuildingType.BARRACKS);
            if (building == null)
                return Message.BARRACKS_NOT_EXISTS.toString();
        } else if (troopType.getTrainingCamp() == BuildingType.MERCENARY_POST) {
            building = currentGovernment.getUniqueBuilding(BuildingType.MERCENARY_POST);
            if (building == null)
                return Message.MERCENARY_POST_NOT_EXISTS.toString();
        } else if (troopType.getTrainingCamp() == BuildingType.ENGINEER_GUILD) {
            building = currentGovernment.getUniqueBuilding(BuildingType.ENGINEER_GUILD);
            if (building == null)
                return Message.ENGINEER_GUILD_NOT_EXISTS.toString();
        }
        else {
            building = currentGovernment.getUniqueBuilding(BuildingType.TUNNELER_GUILD);
            if (building == null)
                return Message.TUNNELER_GUILD_NOT_EXISTS.toString();
        }

        Troop troop = new Troop(currentGovernment, troopType, tile);

        if (count < 0)
            return Message.INVALID_AMOUNT.toString();

        currentGovernment.addTroops(troop, count);
        return Message.DROP_UNIT_SUCCESSFUL.toString();
    }

    public String setTexture(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("y"));//reverse because of the array!!
        int y = Integer.parseInt(matcher.group("x"));
        if (currentGame.getMap().getTileByLocation(x, y) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        if (Texture.getTextureByName(MultiMenuFunctions.deleteQuotations(matcher.group("type"))) == null)
            return Message.INVALID_TEXTURE_NAME.toString();

        if (currentGame.getMap().getField()[x][y].getBuilding() != null || currentGame.getMap().getField()[x][y].getPeople().size() != 0)
            return Message.TEXTURE_CHANGE_ERROR.toString();

        currentGame.getMap().getField()[x][y].changeTexture(Texture.getTextureByName
                (MultiMenuFunctions.deleteQuotations(matcher.group("type"))));
        return Message.TEXTURE_CHANGED_SUCCESSFULLY.toString();
    }

    public String setRectangleTextures(Matcher matcher) {
        int x1 = Integer.parseInt(matcher.group("y1"));
        int y1 = Integer.parseInt(matcher.group("x1"));
        int x2 = Integer.parseInt(matcher.group("y2"));
        int y2 = Integer.parseInt(matcher.group("x2"));

        if (currentGame.getMap().getTileByLocation(x1, y1) == null || currentGame.getMap().getTileByLocation(x2, y2) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        if (currentGame.getMap().AreaContainsSomething(x1, y1, x2, y2))
            return Message.AREA_NOT_EMPTY.toString();

        if (Texture.getTextureByName(MultiMenuFunctions.deleteQuotations(matcher.group("type"))) == null)
            return Message.INVALID_TEXTURE_NAME.toString();

        for (int i = y1; i <= y2; i++) {
            for (int j = x1; j <= x2; j++) {
                currentGame.getMap().getField()[i][j].changeTexture(Texture.getTextureByName
                        (MultiMenuFunctions.deleteQuotations(matcher.group("type"))));
            }
        }

        return Message.TEXTURE_CHANGED_SUCCESSFULLY.toString();
    }

    public String clearTexture(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("y"));
        int y = Integer.parseInt(matcher.group("x"));

        if (currentGame.getMap().getTileByLocation(x, y) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        currentGame.getMap().getTileByLocation(x, y).changeTexture(Texture.GROUND);//default
        currentGame.getMap().getTileByLocation(x, y).getPeople().clear();
        currentGame.getMap().getTileByLocation(x, y).removeBuilding();

        return Message.CLEAR_SUCCESSFUL.toString();
    }

    public String dropTree(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("y"));
        int y = Integer.parseInt(matcher.group("x"));

        if (currentGame.getMap().getTileByLocation(x, y) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        if (currentGame.getMap().getField()[x][y].getBuilding() != null || currentGame.getMap().getField()[x][y].getPeople().size() != 0
                || !currentGame.getMap().getField()[x][y].getTexture().canHaveTree())
            return Message.DROP_TREE_ERROR.toString();
        currentGame.getMap().getField()[x][y].changeTexture(Texture.getTextureByName
                (MultiMenuFunctions.deleteQuotations(matcher.group("type"))));
        return Message.DROP_TREE.toString();
    }

    public String dropRock(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("y"));
        int y = Integer.parseInt(matcher.group("x"));

        if (currentGame.getMap().getTileByLocation(x, y) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        if (currentGame.getMap().getField()[x][y].getBuilding() != null || currentGame.getMap().getField()[x][y].getPeople().size() != 0)
            return Message.DROP_ROCK_ERROR.toString();

        currentGame.getMap().getField()[x][y].changeTexture(Texture.ROCK);
        return Message.DROP_ROCK.toString();
    }

}
