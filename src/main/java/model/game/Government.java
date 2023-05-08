package model.game;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.Storage;
import model.people.Troop;
import model.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Government {
    private final User user;
    private final String username;
    private final int territory;
    private final Color color;
    private int gold;
    private ArrayList<Building> buildings;
    private HashMap<Troop, Integer> troops;
    private boolean hasMarket;
    private final ArrayList<Storage> stockpile;
    private final ArrayList<Storage> granary;
    private final ArrayList<Storage> armory;
    private int popularity;

    public Government(User user, Color color, int territory) {
        this.user = user;
        this.username = user.getUsername();
        this.territory = territory;
        this.color = color;
        // TODO: set default value for gold!
        this.gold = 0;
        // TODO: set default resources!
        this.stockpile = new ArrayList<>();
        this.granary = new ArrayList<>();
        this.armory = new ArrayList<>();
        // TODO: set default popularity!
        this.popularity = 100;
        this.buildings = new ArrayList<>();
        this.troops = new HashMap<>();
        this.hasMarket = false;
    }

    public void addTroops(Troop troop, int count) {
        troops.put(troop, count + troops.getOrDefault(troop, 0));
        troop.getLocation().addMilitaryUnit(troop, count);
    }

    public int getTerritory() {
        return territory;
    }
    public Color getColor() {
        return this.color;
    }
    public Building getUnicBuilding(BuildingType type) {
        for (Building building : buildings) {
            if (building.getType() == type)
                return building;
        }
        return null;
    }
    public int getGold() {
        return this.gold;
    }

    public boolean hasShop() {
        return hasMarket;
    }
    public ArrayList<Storage> getStockpile() {
        return this.stockpile;
    }

    public ArrayList<Storage> getGranary() {
        return this.granary;
    }

    public ArrayList<Storage> getArmory() {
        return this.armory;
    }

    public int getPopularity() {
        return this.popularity;
    }

    public void addBuilding(Building building) {
        if(building.getType() == BuildingType.STOCKPILE)
            this.stockpile.add((Storage) building);
        else if (building.getType() == BuildingType.GRANARY)
            this.granary.add((Storage) building);
        else if (building.getType() == BuildingType.ARMORY)
            this.armory.add((Storage) building);
        // todo : unComment these :
//        else if (building.getType() == BuildingType.MARKET) {
//            this.hasMarket = true;
//            this.buildings.add(building);
//        }
        else
            this.buildings.add(building);
    }

    // Shop Menu Methods :

    private int getFreeSpace(ArrayList<Storage> repository) {
        int freeSpace = 0;
        for (Storage storage : repository) {
            freeSpace += storage.getFreeSpace();
        }

        return freeSpace;
    }

    private int getItemAmount(Item item, ArrayList<Storage> repository) {
        int itemAmount = 0;
        for (Storage storage : repository) {
            itemAmount += storage.getItemAmount(item);
        }

        return itemAmount;
    }

    public boolean addToTargetRepository(ArrayList<Storage> repository, Item item, int amount) {
        if (getFreeSpace(repository) < amount)
            return false;

        gold -= amount * item.getBuyCost();

        for (Storage storage : repository) {
            if (amount < 1)
                break;

            amount = storage.increaseStock(item, amount);
        }
        return true;
    }

    public boolean removeFromTargetRepository(ArrayList<Storage> repository, Item item, int amount) {
        if (getItemAmount(item, repository) < amount)
            return false;

        gold += amount * item.getSellCost();

        for (Storage storage : repository) {
            if (amount < 1)
                break;

            amount = storage.decreaseStock(item, amount);
        }
        return true;
    }

    public void checkForHighScore() {
        int score = 0; // todo : handle score
        if (user.getHighScore() < score)
            user.setHighScore(score);
    }

    public String getUsername() {
        return username;
    }
}
