package model.game;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.Storage;
import model.people.MilitaryUnit;
import model.people.Troop;
import model.people.TroopType;
import model.user.User;

import java.util.ArrayList;

public class Government {
    private final User user;
    private final int territory;
    private final Color color;
    private int gold;
    private final Troop lord;
    private final ArrayList<MilitaryUnit> militaryUnits;
    private final ArrayList<Building> buildings;
    private boolean hasMarket;
    private final ArrayList<Storage> stockpile;
    private final ArrayList<Storage> granary;
    private final ArrayList<Storage> armory;
    private int popularity;
    private double foodRate;
    private double taxRate;
    private double fearRate;

    public Government(User user,  Color color, int territory) {
        this.user = user;
        this.territory = territory;
        this.color = color;
        // TODO: set default value for gold!
        this.gold = 0;
        this.lord = new Troop(this, TroopType.LORD);
        this.militaryUnits = new ArrayList<>();
        this.buildings = new ArrayList<>();
        this.hasMarket = false;
        // TODO: set default resources!
        this.stockpile = new ArrayList<>();
        this.granary = new ArrayList<>();
        this.armory = new ArrayList<>();
        this.popularity = 100;
        this.foodRate = 1;
        this.taxRate = 0;
        this.fearRate = 0;
    }

    public User getUser() {
        return this.user;
    }

    public int getTerritory() {
        return territory;
    }

    public Color getColor() {
        return this.color;
    }

    public int getGold() {
        return this.gold;
    }

    public Troop getLord() {
        return this.lord;
    }

    public ArrayList<MilitaryUnit> getMilitaryUnits() {
        return this.militaryUnits;
    }

    public ArrayList<Building> getBuildings() {
        return this.buildings;
    }

    public boolean hasMarket() {
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

        this.gold -= amount * item.getBuyCost();

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

        this.gold += amount * item.getSellCost();

        for (Storage storage : repository) {
            if (amount < 1)
                break;

            amount = storage.decreaseStock(item, amount);
        }
        return true;
    }

    public void modifyHighScore() {
        int score = 0; // todo : handle score
        if (this.user.getHighScore() < score)
            this.user.setHighScore(score);
    }
}
