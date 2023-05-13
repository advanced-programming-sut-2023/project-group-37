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
import model.user.User;
import view.enums.Message;

import java.util.ArrayList;
import java.util.Objects;
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

    public void setCurrentGovernment(Government government) {
        this.currentGovernment = government;
        this.shopMenuController.setGovernment(government);
        this.tradeMenuController.setGovernment(government);
        this.mapMenuController.setGovernment(government);
        this.unitMenuController.setGovernment(government);
        this.buildingMenuController.setCurrentGovernment(government);
    }

    public void setCurrentGame(Game game) {
        this.currentGame = game;
        tradeMenuController.setGame(game);
        mapMenuController.setGame(game);
        unitMenuController.setGame(game);
        buildingMenuController.setCurrentGame(game);
    }

    public Map getMap() {
        return currentGame.getMap();
    }

    public Tile[][] showMap(int x, int y) {
        return mapMenuController.showMap(x, y);
    }

    public String enterShopMenu() {
        if (this.currentGovernment.getUniqueBuilding(BuildingType.MARKET) != null)
            return Message.ENTERED_SHOP_MENU.toString();
        return Message.MARKET_NOT_EXISTS.toString();
    }

    public String showPopularity() {
        return Integer.toString(this.currentGovernment.getPopularity());
    }

    public String showPopularityFactors() {
        return "Popularity factors:\n" + "Food: " + (this.currentGovernment.getFoodRate() / 2 + 1) + "\n" +
                "Tax: " + (this.currentGovernment.getTaxRate() == 0 ? 0 :
                0.2 * this.currentGovernment.getTaxRate() + 0.4) + "\n" +
                "Fear: " + this.currentGovernment.getFearRate() + "\n" +
                "Religion: " + this.currentGovernment.getReligionPopularityRate();
    }

    public String showFoodList() {
        return "Apple: " + this.currentGovernment.getItemAmount(Item.APPLE) + "\n" +
                "Cheese: " + this.currentGovernment.getItemAmount(Item.CHEESE) + "\n" +
                "Bread: " + this.currentGovernment.getItemAmount(Item.BREAD) + "\n" +
                "Meat: " + this.currentGovernment.getItemAmount(Item.MEAT);
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
        if ((tile = this.currentGame.getMap().getTileByLocation(x, y)) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        Object type = BuildingType.getBuildingTypeByName(MultiMenuFunctions.deleteQuotations(matcher.group("type")));
        if (type == null)
            return Message.INVALID_BUILDING_TYPE.toString();

        // TODO: check if there is moat!
        if (!tile.getTexture().canHaveBuildingAndUnit())
            return Message.CANNOT_PLACE_BUILDING_ON_TEXTURE.toString();

        // Check texture:
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

        // Check territory for defensiveBuildings
        if (type instanceof DefensiveBuildingType &&
                this.currentGovernment.getTerritory() != tile.getTerritory())
            return Message.NOT_IN_TERRITORY.toString();

        // Check enough gold and resource:
        if (type instanceof BuildingType) {
            if (((BuildingType) type).getCost() > this.currentGovernment.getGold())
                return Message.NOT_ENOUGH_GOLD.toString();
            if (((BuildingType) type).getBuildingMaterialAmount() >
                    this.currentGovernment.getItemAmount(((BuildingType) type).getBuildingMaterial()))
                return ((BuildingType) type).getBuildingMaterial().getName() + Message.NOT_ENOUGH_RESOURCE;
        } else if (type instanceof DefensiveBuildingType) {
            if (((DefensiveBuildingType) type).getCost() > this.currentGovernment.getGold())
                return Message.NOT_ENOUGH_GOLD.toString();
            if (((DefensiveBuildingType) type).getStoneAmount() > this.currentGovernment.getItemAmount(Item.STONE))
                return "Stone" + Message.NOT_ENOUGH_RESOURCE;
        }

        // Check enough peasants:
        if (type instanceof BuildingType && this.currentGovernment.getPeasantCount() <
                ((BuildingType) type).getWorkersNeeded())
            return Message.NOT_ENOUGH_PEASANT.toString();

        // Check if unique and available!
        if ((type == BuildingType.BARRACKS || type == BuildingType.MERCENARY_POST ||
                type == BuildingType.ENGINEER_GUILD || type == BuildingType.TUNNELER_GUILD ||
                type == BuildingType.MARKET) && this.currentGovernment.getUniqueBuilding((BuildingType) type) != null)
            return Message.BUILDING_IS_UNIQUE.toString();

        // Check if non-first storage is not near the others!
        if ((type == BuildingType.STOCKPILE || type == BuildingType.GRANARY || type == BuildingType.ARMORY) &&
                this.currentGovernment.getUniqueBuilding((BuildingType) type) != null &&
                this.currentGame.getMap().getTileByLocation(tile.getX() - 1, tile.getY()).getBuilding().getType() != type &&
                this.currentGame.getMap().getTileByLocation(tile.getX() + 1, tile.getY()).getBuilding().getType() != type &&
                this.currentGame.getMap().getTileByLocation(tile.getX(), tile.getY() - 1).getBuilding().getType() != type &&
                this.currentGame.getMap().getTileByLocation(tile.getX(), tile.getY() + 1).getBuilding().getType() != type)
            return Message.STORAGE_NOT_NEIGHBOR.toString();

        // Decrease gold and resource:
        if (type instanceof BuildingType) {
            this.currentGovernment.setGold(this.currentGovernment.getGold() - ((BuildingType) type).getCost());
            this.currentGovernment.removeItem(((BuildingType) type).getBuildingMaterial(),
                    ((BuildingType) type).getBuildingMaterialAmount());
        } else if (type instanceof DefensiveBuildingType) {
            this.currentGovernment.setGold(this.currentGovernment.getGold() - ((DefensiveBuildingType) type).getCost());
            this.currentGovernment.removeItem(Item.STONE, ((DefensiveBuildingType) type).getStoneAmount());
        }

        Building building = null;
        if (type instanceof BuildingType) {
            if (type == BuildingType.STOCKPILE || type == BuildingType.GRANARY || type == BuildingType.ARMORY)
                building = new Storage(this.currentGovernment, tile, (BuildingType) type);
            else
                building = new Building(this.currentGovernment, tile, (BuildingType) type);
        } else if (type instanceof DefensiveBuildingType)
            building = new DefensiveBuilding(this.currentGovernment, tile, (DefensiveBuildingType) type);

        assert building != null;
        tile.setBuilding(building);
        this.currentGovernment.addBuilding(building);

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
        tile.setPassability(type == BuildingType.KILLING_PIT);
        return Message.DROP_BUILDING_SUCCESS.toString();
    }

    public String selectBuilding(Matcher matcher) {

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Tile tile;

        if ((tile = this.currentGame.getMap().getTileByLocation(x, y)) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        Building building;
        if ((building = tile.getBuilding()) == null)
            return Message.NO_BUILDING_IN_TILE.toString();

        if (building.getLoyalty() != this.currentGovernment)
            return Message.BUILDING_NOT_YOURS.toString();

        this.buildingMenuController.setCurrentBuilding(this.currentGame.getMap().getTileByLocation(x, y).getBuilding());
        this.buildingMenuController.setCurrentGovernment(this.currentGovernment);
        return building.getType().toString() + " selected!\n" + Message.ENTERED_BUILDING_MENU;
    }

    public String selectUnit(int x, int y) {
        Tile tile = this.currentGame.getMap().getTileByLocation(x, y);

        if (tile == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        ArrayList<MilitaryUnit> unit = tile.getMilitaryUnits();
        ArrayList<MilitaryUnit> myUnit = new ArrayList<>();
        for (MilitaryUnit militaryUnit : unit) {
            if (militaryUnit.getLoyalty() == this.currentGovernment)
                myUnit.add(militaryUnit);
        }

        if (myUnit.size() == 0)
            return Message.UNIT_NOT_EXISTS.toString();

        this.unitMenuController.setUnit(myUnit, tile);
        return Message.UNIT_SELECTED.toString();
    }

    public String dropUnit(Matcher matcher) {
        String type = matcher.group("type");
        int count = Integer.parseInt(matcher.group("count"));
        int x = Integer.parseInt(matcher.group("x")), y = Integer.parseInt(matcher.group("y"));

        Tile tile;
        if ((tile = this.currentGame.getMap().getTileByLocation(x, y)) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        TroopType troopType = TroopType.getTroopTypeByName(type);
        if (troopType == null)
            return Message.TYPE_NOT_EXISTS.toString();

        Building building;
        if (troopType.getTrainingCamp() == BuildingType.BARRACKS) {
            building = this.currentGovernment.getUniqueBuilding(BuildingType.BARRACKS);
            if (building == null)
                return Message.BARRACKS_NOT_EXISTS.toString();
        } else if (troopType.getTrainingCamp() == BuildingType.MERCENARY_POST) {
            building = this.currentGovernment.getUniqueBuilding(BuildingType.MERCENARY_POST);
            if (building == null)
                return Message.MERCENARY_POST_NOT_EXISTS.toString();
        } else if (troopType.getTrainingCamp() == BuildingType.ENGINEER_GUILD) {
            building = this.currentGovernment.getUniqueBuilding(BuildingType.ENGINEER_GUILD);
            if (building == null)
                return Message.ENGINEER_GUILD_NOT_EXISTS.toString();
        } else {
            building = this.currentGovernment.getUniqueBuilding(BuildingType.TUNNELER_GUILD);
            if (building == null)
                return Message.TUNNELER_GUILD_NOT_EXISTS.toString();
        }

        if (count < 0)
            return Message.INVALID_AMOUNT.toString();

        // check amounts
        int goldCost = troopType.getCost() * count;
        Item armor = troopType.getArmor(), weapon = troopType.getWeapon();

        if (goldCost < currentGovernment.getGold())
            return Message.NOT_ENOUGH_GOLD.toString();

        if (count < currentGovernment.getItemAmount(armor) || count < currentGovernment.getItemAmount(weapon))
            return Message.NOT_ENOUGH_RESOURCE.toString();

        currentGovernment.removeItem(armor, count);
        currentGovernment.removeItem(weapon, count);
        currentGovernment.setGold(currentGovernment.getGold() - goldCost);

        Troop troop = new Troop(this.currentGovernment, troopType, tile);

        this.currentGovernment.addTroops(troop, count);
        return Message.DROP_UNIT_SUCCESSFUL.toString();
    }

    public String setTexture(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));

        if (this.currentGame.getMap().getTileByLocation(x, y) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        if (Texture.getTextureByName(MultiMenuFunctions.deleteQuotations(matcher.group("type"))) == null)
            return Message.INVALID_TEXTURE_NAME.toString();

        if (!this.currentGame.getMap().getField()[x][y].isTotallyEmpty())
            return Message.TEXTURE_CHANGE_ERROR.toString();

        this.currentGame.getMap().getField()[x][y].changeTexture(Texture.getTextureByName(
                MultiMenuFunctions.deleteQuotations(matcher.group("type"))));
        return Message.TEXTURE_CHANGED_SUCCESSFULLY.toString();
    }

    public String setRectangleTextures(Matcher matcher) {
        int x1 = Integer.parseInt(matcher.group("x1"));
        int y1 = Integer.parseInt(matcher.group("y1"));
        int x2 = Integer.parseInt(matcher.group("x2"));
        int y2 = Integer.parseInt(matcher.group("y2"));

        if (this.currentGame.getMap().getTileByLocation(x1, y1) == null ||
                this.currentGame.getMap().getTileByLocation(x2, y2) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        for (int i = x1; i <= x2; i++)
            for (int j = y1; j <= y2; j++)
                if (!this.currentGame.getMap().getField()[i][j].isTotallyEmpty())
                    return Message.AREA_NOT_EMPTY.toString();

        if (Texture.getTextureByName(MultiMenuFunctions.deleteQuotations(matcher.group("type"))) == null)
            return Message.INVALID_TEXTURE_NAME.toString();

        for (int i = x1; i <= x2; i++)
            for (int j = y1; j <= y2; j++)
                this.currentGame.getMap().getField()[i][j].changeTexture(Texture.getTextureByName(
                        MultiMenuFunctions.deleteQuotations(matcher.group("type"))));

        return Message.TEXTURE_CHANGED_SUCCESSFULLY.toString();
    }

    public String clearTexture(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));

        if (this.currentGame.getMap().getTileByLocation(x, y) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        Tile tile = this.currentGame.getMap().getTileByLocation(x, y);

        tile.changeTexture(Texture.GROUND);
        for (Person person : this.currentGame.getMap().getTileByLocation(x, y).getPeople())
            this.currentGovernment.getPeople().remove(person);
        tile.getPeople().clear();
        for (MilitaryUnit unit : tile.getMilitaryUnits())
            this.currentGovernment.getMilitaryUnits().remove(unit);
        tile.getMilitaryUnits().clear();
        this.currentGovernment.getBuildings().remove(tile.getBuilding());
        tile.removeBuilding();

        return Message.CLEAR_SUCCESSFUL.toString();
    }

    public String dropTree(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));

        if (this.currentGame.getMap().getTileByLocation(x, y) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        if (!this.currentGame.getMap().getField()[x][y].isTotallyEmpty() ||
                !this.currentGame.getMap().getField()[x][y].getTexture().canHaveTree())
            return Message.DROP_TREE_ERROR.toString();
        this.currentGame.getMap().getField()[x][y].changeTexture(Texture.getTextureByName(
                MultiMenuFunctions.deleteQuotations(matcher.group("type"))));
        return Message.DROP_TREE.toString();
    }

    public String dropRock(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));

        if (this.currentGame.getMap().getTileByLocation(x, y) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        if (!this.currentGame.getMap().getField()[x][y].isTotallyEmpty())
            return Message.DROP_ROCK_ERROR.toString();

        this.currentGame.getMap().getField()[x][y].changeTexture(Texture.ROCK);
        return Message.DROP_ROCK.toString();
    }

    public String showInfo() {
        StringBuilder info = new StringBuilder("All Info:\n");
        info.append("Username:").append(currentGovernment.getUser().getUsername()).append("\n");
        info.append("Gold amount:").append(currentGovernment.getGold()).append("\n");
        info.append("Tax--Food--Fear rate:").append(currentGovernment.getTaxRate())
                .append("--").append(currentGovernment.getFoodRate()).append(currentGovernment.getFearRate()).append("\n");
        info.append("Popularity:").append(currentGovernment.getPopularity()).append("\n");
        info.append("Population:").append(currentGovernment.getPeople().size()).append("\n");
        info.append("Number of buildings:").append(currentGovernment.getBuildings().size()).append("\n");
        return info.toString().trim();
    }

    public String enterTradeMenu() {
        return tradeMenuController.showNewTrades();
    }

    public String goNextTurn() {
        currentGame.goToNextTurn();
        if (gameEndMessage() != null)
            return gameEndMessage();

        return Message.SUCCESS.toString();
    }

    private String gameEndMessage() {
        ArrayList<Government> liveGovernments = new ArrayList<>();
        for (Government government : currentGame.getGovernments()) {
            if (government.getLord().getHitpoints() > 0)
                liveGovernments.add(government);
        }
        if (liveGovernments.size() > 1)
            return null;

        setGovernmentsRank();
        if (liveGovernments.size() == 0)
            return "Game ended; All governments died!";
        else return "Game ended; Winner: " + liveGovernments.get(0).getUser().getUsername();
    }

    public String endGame() {
        Government winner = currentGame.getGovernments().get(0);
        int maxScore = 0;
        int score;
        for (Government government : currentGame.getGovernments()) {
            if ((score = government.modifyScore()) > maxScore) {
                maxScore = score;
                winner = government;
            }
        }
        setGovernmentsRank();
        return "Game ended, Winner: " + winner.getUser().getUsername();
    }

    private void setGovernmentsRank() {
        for (Government government : currentGame.getGovernments())
            User.setRankByHyScore(government.getUser());
    }
}
