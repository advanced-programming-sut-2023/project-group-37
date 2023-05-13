package model.game;

import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.Storage;
import model.people.MilitaryUnit;
import model.people.Person;
import model.people.Troop;

import model.people.TroopType;
import model.user.User;

import java.util.ArrayList;
import java.util.Map;

public class Government {
    private final User user;
    private final Territory territory;
    private final Color color;
    private int gold;
    private final Troop lord;
    private final ArrayList<Person> people;
    private final ArrayList<Troop> troops;
    private final ArrayList<MilitaryUnit> militaryUnits;
    private final ArrayList<Building> buildings;
    private final ArrayList<Storage> stockpile;
    private final ArrayList<Storage> granary;
    private final ArrayList<Storage> armory;
    private int popularity;
    private int foodRate;
    private int taxRate;
    private int fearRate;
    private int religionPopularityRate;

    public Government(User user, Color color, model.game.Map map, int territoryNumber) {
        this.user = user;
        this.territory = map.getCopyKeepByNumber(this, territoryNumber);
        this.color = color;
        // TODO: set default value for gold!
        this.gold = 2000;
        this.lord = new Troop(this, TroopType.LORD, territory.getKeep());
        this.people = new ArrayList<>();
        this.militaryUnits = new ArrayList<>();
        this.buildings = new ArrayList<>();
        // TODO: set default resources!
        this.stockpile = new ArrayList<>();
        this.stockpile.add(new Storage(this, territory.getFirstStockpileLocation(), BuildingType.STOCKPILE));
        this.addItem(Item.WOOD, 100);
        this.addItem(Item.STONE, 50);
        this.granary = new ArrayList<>();
        this.armory = new ArrayList<>();
        // TODO: set default popularity!
        this.popularity = 100;
        this.foodRate = 1;
        this.taxRate = 0;
        this.fearRate = 0;
        this.troops = new ArrayList<>();
    }

    public User getUser() {
        return this.user;
    }


    public void addTroops(Troop troop, int count) {
        for (int i=0; i < count; i++)
            troops.add(troop);
        troop.getLocation().addMilitaryUnit(troop, count);
    }

    public Territory getTerritory() {
        return this.territory;
    }

    public Color getColor() {
        return this.color;
    }

    public Building getUniqueBuilding(BuildingType type) {
        for (Building building : this.buildings) {
            if (building.getType() == type)
                return building;
        }
        return null;
    }

    public int getGold() {
        return this.gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public Troop getLord() {
        return this.lord;
    }

    public ArrayList<Person> getPeople() {
        return this.people;
    }

    public int getPeasantCount() {
        int count = 0;
        for (Person person : this.people)
            if (person.getWorkplace() == null)
                count++;
        return count;
    }

    public ArrayList<MilitaryUnit> getMilitaryUnits() {
        return this.militaryUnits;
    }

    public ArrayList<Building> getBuildings() {
        return this.buildings;
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

    public void setPopularity(int amount) {
        this.popularity = amount;
    }

    public int getFoodRate() {
        return this.foodRate;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
    }

    public int getTaxRate() {
        return this.taxRate;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public int getFearRate() {
        return this.fearRate;
    }

    public int getReligionPopularityRate() {
        return this.religionPopularityRate;
    }

    public void setReligionPopularityRate(int religionPopularityRate) {
        this.religionPopularityRate = religionPopularityRate;
    }

    public void addPeasant(int count) {
        for (int i = 0; i < count; i++)
            this.people.add(new Person(this));
    }

    public void addBuilding(Building building) {
        if (building.getType() == BuildingType.STOCKPILE)
            this.stockpile.add((Storage) building);
        else if (building.getType() == BuildingType.GRANARY)
            this.granary.add((Storage) building);
        else if (building.getType() == BuildingType.ARMORY)
            this.armory.add((Storage) building);
        else
            this.buildings.add(building);
    }

    public void distributeFood() {
        double foodPerPerson = 0;
        if (this.foodRate > -2)
            foodPerPerson = (double) (this.foodRate / 2) + 1;

        int totalFoodNeeded = (int) (this.people.size() * foodPerPerson), foodToDistribute;


        OUTER:
        for (int i = this.granary.size() - 1; i >= 0; i--) {
            for (Map.Entry<Item, Integer> entry : granary.get(i).getStock().entrySet()) {
                if (totalFoodNeeded == 0)
                    break OUTER;

                foodToDistribute = Math.min(entry.getValue(), totalFoodNeeded);
                entry.setValue(entry.getValue() - foodToDistribute);
                totalFoodNeeded -= foodToDistribute;
            }
        }

        if (totalFoodNeeded != 0)
            this.foodRate = -2;

        this.popularity += 4 * this.foodRate;
    }

    public void receiveTax() {
        double taxPerPerson = 0;
        if (this.taxRate != 0)
            taxPerPerson = (this.taxRate > 0 ? 1 : -1) * (0.2 * taxRate + 0.4);

        if (this.people.size() * taxPerPerson > this.gold)
            this.taxRate = 0;
        this.gold = Math.min(0, this.gold + (int) (this.people.size() * taxPerPerson));

        if (Math.abs(this.popularity) <= 4)
            this.popularity += -2 * this.taxRate + (this.taxRate > 0 ? 0 : 1);
        else
            this.popularity += -4 * this.taxRate + 8;
    }

    private ArrayList<Storage> getTargetRepository(Item item) {
        return switch (item.getCategory()) {
            case RESOURCES -> this.stockpile;
            case FOODS -> this.granary;
            case WEAPONS -> this.armory;
        };
    }

    // Shop Menu Methods :

    private int getFreeSpace(ArrayList<Storage> repository) {
        int freeSpace = 0;
        for (Storage storage : repository) {
            freeSpace += storage.getFreeSpace();
        }

        return freeSpace;
    }

    public int getItemAmount(Item item) {
        int itemAmount = 0;
        for (Storage storage : getTargetRepository(item)) {
            itemAmount += storage.getItemAmount(item);
        }

        return itemAmount;
    }

    public boolean addItem(Item item, int amount) {
        ArrayList<Storage> repository = getTargetRepository(item);
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

    public boolean removeItem(Item item, int amount) {
        ArrayList<Storage> repository = getTargetRepository(item);
        if (getItemAmount(item) < amount)
            return false;

        for (Storage storage : repository) {
            if (amount < 1)
                break;

            amount = storage.decreaseStock(item, amount);
        }
        return true;
    }

    public boolean sellItem(Item item, int amount) {
        if (removeItem(item, amount))
            return false;

        this.gold += amount * item.getSellCost();
        return true;
    }

    public void removeDeadUnits() {
        int index = 0;
        MilitaryUnit militaryUnit;
        while (index < this.militaryUnits.size()) {
            militaryUnit = this.militaryUnits.get(index);
            if (militaryUnit.getHitpoints() < 1) {
                this.militaryUnits.remove(index);
                militaryUnit.getLocation().getMilitaryUnits().remove(militaryUnit);
            } else index++;
        }
    }

    public void removeDestroyedBuildings() {
        int index = 0;
        Building building;
        while (index < this.buildings.size()) {
            building = this.buildings.get(index);
            if (building.getHitpoints() < 1) {
                building.destroy();
                this.buildings.remove(index);
            }
            else index++;
        }
    }

    public int modifyScore() {
        int score = 0;

        if (this.user.getHighScore() < score)
            this.user.setHighScore(score);

        return score;
    }

    public void destroy() {
        // todo
    }
}
