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
import java.util.regex.Matcher;

public class GameMenuController {
    private static final GameMenuController gameMenuController;
    private Game currentGame;
    private final MapMenuController mapMenuController;
    private final ShopMenuController shopMenuController;
    private final TradeMenuController tradeMenuController;
    private final BuildingMenuController buildingMenuController;
    private final UnitMenuController unitMenuController;
    private Government currentGovernment;
    static {
        gameMenuController = new GameMenuController();
    }

    private GameMenuController() {
        mapMenuController = MapMenuController.getInstance();
        shopMenuController = ShopMenuController.getInstance();
        tradeMenuController = TradeMenuController.getInstance();
        buildingMenuController = BuildingMenuController.getInstance();
        unitMenuController = UnitMenuController.getInstance();

    }

    public static GameMenuController getInstance() {
        return gameMenuController;
    }

    public void setCurrentGovernment(Government government) {
        this.currentGovernment = government;
        this.shopMenuController.setGovernment(government);
        this.tradeMenuController.setGovernment(government);
        this.mapMenuController.setCurrentGovernment(government);
        this.unitMenuController.setCurrentGovernment(government);
        this.buildingMenuController.setCurrentGovernment(government);
    }

    public void setCurrentGame(Game game) {
        this.currentGame = game;
        tradeMenuController.setGame(game);
        mapMenuController.setGame(game);
        unitMenuController.setCurrentGame(game);
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
        return "Popularity factors:\n" + "Food: " + (this.currentGovernment.getFoodRate() / 2 + 1) +
                "\nTax: " + (this.currentGovernment.getTaxRate() == 0 ? 0 :
                0.2 * this.currentGovernment.getTaxRate() + 0.4) +
                "\nFear: " + this.currentGovernment.getFearRate() +
                "\nReligion: " + this.currentGovernment.getReligionPopularityRate();
    }

    public String showItemList(ItemCategory category) {
        StringBuilder result = new StringBuilder();
        for (Item item : Item.values())
            if (item.getCategory() == category)
                result.append(item.getName()).append(":\t").append(this.currentGovernment.getItemAmount(item)).append("\n");
        return result.toString().trim();
    }

    public String setFoodRate(Matcher matcher) {
        if (matcher.group("rateNumber") == null)
            return Message.EMPTY_FIELD.toString();

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
        return "Fear rate: " + this.currentGovernment.getFearRate();
    }

    public String dropBuilding(Matcher matcher) {
        if (matcher.group("x") == null || matcher.group("y") == null)
            return Message.EMPTY_FIELD.toString();

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Tile tile;
        if ((tile = this.currentGame.getMap().getTileByLocation(x, y)) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        Object type = BuildingType.getBuildingTypeByName(MultiMenuFunctions.deleteQuotations(matcher.group("type")));
        if (type == null)
            return Message.INVALID_BUILDING_TYPE.toString();

        char direction;
        try {
            direction = matcher.group("direction").charAt(0);
        } catch (Exception ignored) {
            direction = 'v';
        }

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
                this.currentGovernment.getTerritory().getTerritoryNumber() != tile.getTerritory().getTerritoryNumber())
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
                type == BuildingType.MARKET || type == BuildingType.OIL_SMELTER) &&
                this.currentGovernment.getUniqueBuilding((BuildingType) type) != null)
            return Message.BUILDING_IS_UNIQUE.toString();

        // Check if non-first storage is not near the others!
        if ((type == BuildingType.STOCKPILE || type == BuildingType.GRANARY || type == BuildingType.ARMORY) &&
                this.currentGovernment.getUniqueBuilding((BuildingType) type) != null &&
                (this.currentGame.getMap().getTileByLocation(tile.getX() - 1, tile.getY()).getBuilding() == null ||
                        this.currentGame.getMap().getTileByLocation(tile.getX() - 1, tile.getY()).getBuilding().getType() != type) &&
                (this.currentGame.getMap().getTileByLocation(tile.getX() + 1, tile.getY()).getBuilding() == null ||
                        this.currentGame.getMap().getTileByLocation(tile.getX() + 1, tile.getY()).getBuilding().getType() != type) &&
                (this.currentGame.getMap().getTileByLocation(tile.getX(), tile.getY() - 1).getBuilding() == null ||
                        this.currentGame.getMap().getTileByLocation(tile.getX(), tile.getY() - 1).getBuilding().getType() != type) &&
                (this.currentGame.getMap().getTileByLocation(tile.getX(), tile.getY() + 1).getBuilding() == null ||
                        this.currentGame.getMap().getTileByLocation(tile.getX(), tile.getY() + 1).getBuilding().getType() != type))
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

        Building[] neighborBuildings = {
                this.currentGame.getMap().getTileByLocation(tile.getX() - 1, tile.getY()).getBuilding(),
                this.currentGame.getMap().getTileByLocation(tile.getX() + 1, tile.getY()).getBuilding(),
                this.currentGame.getMap().getTileByLocation(tile.getX(), tile.getY() - 1).getBuilding(),
                this.currentGame.getMap().getTileByLocation(tile.getX(), tile.getY() + 1).getBuilding(),
        };

        Building building = null;
        if (type instanceof BuildingType) {
            if (type == BuildingType.STOCKPILE || type == BuildingType.GRANARY || type == BuildingType.ARMORY)
                building = new Storage(this.currentGovernment, tile, (BuildingType) type);
            else
                building = new Building(this.currentGovernment, tile, (BuildingType) type);
        } else if (type instanceof DefensiveBuildingType) {
            building = new DefensiveBuilding(this.currentGovernment, tile, (DefensiveBuildingType) type, direction);

            for (Building neighborBuilding : neighborBuildings)
                if (neighborBuilding instanceof DefensiveBuilding) {
                    ((DefensiveBuilding) building).addDefensiveNeighbor((DefensiveBuilding) neighborBuilding);
                    for (DefensiveBuilding neighbor : ((DefensiveBuilding) neighborBuilding).getDefensiveNeighbors())
                        ((DefensiveBuilding) building).addDefensiveNeighbor(neighbor);
                    ((DefensiveBuilding) neighborBuilding).addDefensiveNeighbor((DefensiveBuilding) building);
                    for (DefensiveBuilding neighbor : ((DefensiveBuilding) building).getDefensiveNeighbors())
                        if (!neighbor.getDefensiveNeighbors().contains(neighborBuilding))
                            neighbor.addDefensiveNeighbor((DefensiveBuilding) neighborBuilding);
                }
        }
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
        if (type == DefensiveBuildingType.STAIRS) {
            for (Building neighbor : neighborBuildings) {
                if (neighbor instanceof DefensiveBuilding) {
                    ((DefensiveBuilding) neighbor).setHasLadderAttached(true);
                    ((DefensiveBuilding) neighbor).setCanBeReached(true);
                    for (DefensiveBuilding defensiveNeighbor : ((DefensiveBuilding) neighbor).getDefensiveNeighbors())
                        defensiveNeighbor.setCanBeReached(true);
                }
            }
        } else if (type == BuildingType.STABLE)
            this.currentGovernment.addHorse(4);

        tile.setPassability(type == BuildingType.KILLING_PIT || type == DefensiveBuildingType.STAIRS ||
                type == BuildingType.BARRACKS || type == BuildingType.MERCENARY_POST ||
                type == BuildingType.ENGINEER_GUILD || type == BuildingType.TUNNELER_GUILD ||
                type == DefensiveBuildingType.SMALL_GATEHOUSE || type == DefensiveBuildingType.LARGE_GATEHOUSE);
        return Message.DROP_BUILDING_SUCCESS.toString();
    }

    public String selectBuilding(Matcher matcher) {
        if (matcher.group("x") == null || matcher.group("y") == null)
            return Message.EMPTY_FIELD.toString();

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
        String type = MultiMenuFunctions.deleteQuotations(matcher.group("type"));
        int x, y, count;
        try {
            count = Integer.parseInt(matcher.group("count"));
            x = Integer.parseInt(matcher.group("x"));
            y = Integer.parseInt(matcher.group("y"));
        } catch (Exception ex) {
            return Message.EMPTY_FIELD.toString();
        }

        Tile tile;
        if ((tile = this.currentGame.getMap().getTileByLocation(x, y)) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        if (tile.getTerritory().getTerritoryNumber() != this.currentGovernment.getTerritory().getTerritoryNumber())
            return Message.TILE_IS_NOT_YOURS.toString();

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

        if (goldCost > currentGovernment.getGold())
            return Message.NOT_ENOUGH_GOLD.toString();

        if (armor != null) {
            if (count > currentGovernment.getItemAmount(armor))
                return Message.NOT_ENOUGH_RESOURCE.toString();
        }

        if (weapon != null) {
            if (count > currentGovernment.getItemAmount(weapon))
                return Message.NOT_ENOUGH_RESOURCE.toString();
        }

        if (troopType.getAnimal() != null && count < currentGovernment.getHorseCount())
            return Message.NOT_ENOUGH_HORSE.toString();

        currentGovernment.removeItem(armor, count);
        currentGovernment.removeItem(weapon, count);
        currentGovernment.setGold(currentGovernment.getGold() - goldCost);

        Troop troop;
        for (int i = 0; i < count; i++) {
            troop = new Troop(this.currentGovernment, troopType, tile);
            this.currentGovernment.getMilitaryUnits().add(troop);
            tile.addMilitaryUnit(troop);
        }
        return Message.DROP_UNIT_SUCCESSFUL.toString();
    }

    public String setTexture(Matcher matcher) {
        if (matcher.group("x") == null || matcher.group("y") == null || matcher.group("type") == null)
            return Message.EMPTY_FIELD.toString();

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
        int x1, y1, x2, y2;
        try {
            x1 = Integer.parseInt(matcher.group("x1"));
            y1 = Integer.parseInt(matcher.group("y1"));
            x2 = Integer.parseInt(matcher.group("x2"));
            y2 = Integer.parseInt(matcher.group("y2"));
        } catch (Exception ex) {
            return Message.EMPTY_FIELD.toString();
        }

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
        if (matcher.group("x") == null || matcher.group("y") == null)
            return Message.EMPTY_FIELD.toString();

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
        if (matcher.group("x") == null || matcher.group("y") == null || matcher.group("type") == null)
            return Message.EMPTY_FIELD.toString();

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));

        if (this.currentGame.getMap().getTileByLocation(x, y) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        if (!this.currentGame.getMap().getField()[x][y].isTotallyEmpty() ||
                !this.currentGame.getMap().getField()[x][y].getTexture().canHaveTree())
            return Message.DROP_TREE_ERROR.toString();

        Texture texture = Texture.getTextureByName(MultiMenuFunctions.deleteQuotations(matcher.group("type")));
        if (texture == null)
            return Message.INVALID_TEXTURE_NAME.toString();

        this.currentGame.getMap().getField()[x][y].changeTexture(texture);
        return Message.DROP_TREE.toString();
    }

    public String dropRock(Matcher matcher) {
        if (matcher.group("x") == null || matcher.group("y") == null)
            return Message.EMPTY_FIELD.toString();

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
        return "All Info:\n" + "Username: " + this.currentGovernment.getUser().getUsername() +
                "\nKeep location: " + currentGovernment.getTerritory().getKeep().getX() +
                ", " + currentGovernment.getTerritory().getKeep().getY() +
                "\nGold amount: " + this.currentGovernment.getGold() +
                "\nFood rate: " + this.currentGovernment.getFoodRate() +
                "\nTax rate: " + this.currentGovernment.getTaxRate() +
                "\nFear rate: " + this.currentGovernment.getFearRate() +
                "\nPopularity: " + this.currentGovernment.getPopularity() +
                "\nPopulation: " + this.currentGovernment.getPeople().size() +
                "\nNumber of buildings: " + this.currentGovernment.getBuildings().size() +
                "\nNumber of MilitaryUnits: " + this.currentGovernment.getMilitaryUnits().size();
    }

    public String enterTradeMenu() {
        return this.tradeMenuController.showNewTrades();
    }

    public String goNextTurn() {
        this.currentGame.goToNextTurn();
        if (gameEndMessage() != null)
            return gameEndMessage();

        return "Now its " + currentGame.getCurrentTurnGovernment().getUser().getUsername() + " turn";
    }

    private String gameEndMessage() {
        ArrayList<Government> remainingGovernments = new ArrayList<>();
        for (Government government : this.currentGame.getGovernments()) {
            if (government.getLord().getHitpoints() > 0)
                remainingGovernments.add(government);
            else government.destroy();
        }

        if (remainingGovernments.size() > 1 && this.currentGame.getTurnNumber() < this.currentGame.getTurns() * this.currentGame.getGovernments().size())
            return null;

        this.setGovernmentsRank();
        if (remainingGovernments.size() == 0)
            return Message.GAME_END_ALL_DESTROYED.toString();
        else
            return Message.GAME_END_WITH_WINNER + remainingGovernments.get(0).getUser().getUsername();
    }

    public String endGame() {
        Government winner = this.currentGame.getGovernments().get(0);
        int maxScore = 0;
        int score;
        boolean hasNoWinner = false;
        for (Government government : this.currentGame.getGovernments()) {
            if ((score = government.modifyScore()) >= maxScore) {
                hasNoWinner = score == maxScore;
                maxScore = score;
                winner = government;
            }
        }
        setGovernmentsRank();
        if (!hasNoWinner)
            return Message.GAME_END_WITH_WINNER + winner.getUser().getUsername();

        return Message.GAME_END_ALL_DESTROYED.toString();
    }

    private void setGovernmentsRank() {
        for (Government government : this.currentGame.getGovernments())
            User.setRankByHyScore(government.getUser());
    }
}
