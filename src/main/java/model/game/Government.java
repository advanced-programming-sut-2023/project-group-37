package model.game;

import javafx.scene.paint.Color;
import model.buildings.*;
import model.people.MilitaryUnit;
import model.people.Person;
import model.people.Troop;

import model.people.TroopType;
import model.user.User;

import java.util.ArrayList;

import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.GREEN;

public class Government {
    private final User user;
    private final Territory territory;
    private final GameColor color;
    private int gold;
    private final Troop lord;
    private final ArrayList<Person> people;
    private final ArrayList<MilitaryUnit> militaryUnits;
    private final ArrayList<Building> buildings;
    private final ArrayList<Storage> stockpile;
    private final ArrayList<Storage> granary;
    private final ArrayList<Storage> armory;
    private int horseCount;
    private int popularity;
    private int foodRate;
    private int taxRate;
    private int fearRate;
    private int religionPopularityRate;
    private int score;
    private boolean isDead;

    public Government(User user, GameColor color, Map map, int territoryNumber) {
        this.user = user;
        this.territory = map.getCopyKeepByNumber(this, territoryNumber);
        this.color = color;
        this.gold = 2000;
        this.territory.getKeep().setBuilding(new DefensiveBuilding(this, this.territory.getKeep(),
                DefensiveBuildingType.KEEP, 'v'));

        this.lord = new Troop(this, TroopType.LORD, this.territory.getMap().getTileByLocation(
                this.territory.getKeep().getLocationX(), this.territory.getKeep().getLocationY() - 1));

        this.people = new ArrayList<>();
        this.addPeasant(10);
        this.militaryUnits = new ArrayList<>();
        this.buildings = new ArrayList<>();
        this.stockpile = new ArrayList<>();
        Storage firstStockPile = new Storage(this, territory.getFirstStockpileLocation(), BuildingType.STOCKPILE);
        this.stockpile.add(firstStockPile);
        firstStockPile.getLocation().setBuilding(firstStockPile);
        this.buildings.add(firstStockPile);
        this.addItem(Item.WOOD, 100);
        this.addItem(Item.STONE, 50);
        this.granary = new ArrayList<>();
        this.armory = new ArrayList<>();
        this.horseCount = 0;
        this.popularity = 100;
        this.foodRate = 0;
        this.taxRate = 0;
        this.fearRate = 0;
        this.score = 0;
        this.isDead = false;
    }

    public User getUser() {
        return this.user;
    }

    public Territory getTerritory() {
        return this.territory;
    }

    public GameColor getColor() {
        return this.color;
    }

    public Building getUniqueBuilding(BuildingType type) {
        for (Building building : this.buildings) {
            if (building.getType() == type)
                return building;
        }
        return null;
    }

    public boolean isOnAttack() {
        boolean isOnAttack = false;
        for (MilitaryUnit militaryUnit : this.militaryUnits) {
            if (militaryUnit.isOnAttack()) {
                isOnAttack = true;
                break;
            }
        }
        return isOnAttack;
    }

    public int getGold() {
        return this.gold;
    }

    public void setGold(int gold) {
        this.score += Math.abs(this.gold - gold);
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

    public int getHorseCount() {
        return this.horseCount;
    }

    public void setHorseCount(int horseCount) {
        this.horseCount = horseCount;
    }

    public void addHorse(int amount) {
        this.horseCount += amount;
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

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public int getFearRate() {
        return this.fearRate;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
    }

    public int getTaxRate() {
        return this.taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
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
        this.buildings.add(building);
        score += 10;
    }

    public void distributeFood() {
        double foodPerPerson = 0;
        if (this.foodRate > -2)
            foodPerPerson = (double) (this.foodRate / 2) + 1;

        int totalFoodNeeded = (int) (this.people.size() * foodPerPerson), foodToDistribute;

        ArrayList<Item> typesOfFoods = new ArrayList<>();

        OUTER:
        for (int i = this.granary.size() - 1; i >= 0; i--) {
            for (java.util.Map.Entry<Item, Integer> entry : granary.get(i).getStock().entrySet()) {
                if (totalFoodNeeded == 0)
                    break OUTER;
                if (!typesOfFoods.contains(entry.getKey()) && entry.getValue() > 0)
                    typesOfFoods.add(entry.getKey());
                foodToDistribute = Math.min(entry.getValue(), totalFoodNeeded);
                entry.setValue(entry.getValue() - foodToDistribute);
                totalFoodNeeded -= foodToDistribute;
            }
        }

        if (totalFoodNeeded != 0)
            this.foodRate = -2;
        else
            this.popularity += typesOfFoods.size() - 1;

        this.popularity += 4 * this.foodRate;
    }

    public void receiveTax() {
        double taxPerPerson = 0;
        if (this.taxRate != 0)
            taxPerPerson = (this.taxRate > 0 ? 1 : -1) * (0.2 * taxRate + 0.4);

        if (this.people.size() * taxPerPerson > this.gold)
            this.taxRate = 0;
        this.gold = Math.max(0, this.gold + (int) (this.people.size() * taxPerPerson));

        if (Math.abs(this.taxRate) <= 4)
            this.popularity += -2 * this.taxRate + (this.taxRate > 0 ? 0 : 1);
        else
            this.popularity += -4 * this.taxRate + 8;
    }

    public ArrayList<Storage> getTargetRepository(Item item) {
        if (item == null)
            return null;

        return switch (item.getCategory()) {
            case RESOURCES -> this.stockpile;
            case FOODS -> this.granary;
            case WEAPONS -> this.armory;
            default -> null;
        };
    }

    // Shop Menu Methods :

    public int getFreeSpace(ArrayList<Storage> repository) {
        if (repository == null)
            return 0;

        int freeSpace = 0;
        for (Storage storage : repository)
            freeSpace += storage.getFreeSpace();
        return freeSpace;
    }

    public int getItemAmount(Item item) {
        if (item == null)
            return 0;

        int itemAmount = 0;
        for (Storage storage : getTargetRepository(item))
            itemAmount += storage.getItemAmount(item);

        return itemAmount;
    }

    public boolean addItem(Item item, int amount) {
        if (item == null)
            return false;

        ArrayList<Storage> repository = getTargetRepository(item);
        if (getFreeSpace(repository) < amount)
            return false;

        for (Storage storage : repository) {
            if (amount < 1)
                break;

            amount = storage.increaseStock(item, amount);
        }
        return true;
    }

    public boolean buyItem(Item item, int amount) {
        if (item == null)
            return false;

        if (!addItem(item, amount))
            return false;

        this.gold -= amount * item.getBuyCost();
        return true;
    }

    public boolean removeItem(Item item, int amount) {
        if (item == null)
            return false;

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
        if (item == null)
            return false;

        if (!removeItem(item, amount))
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
                continue;
            }
            else if (militaryUnit instanceof Troop troop) {
                if (troop.getType() == TroopType.LADDERMAN || troop.getType() == TroopType.TUNNELER) {
                    this.militaryUnits.remove(index);
                    militaryUnit.getLocation().getMilitaryUnits().remove(militaryUnit);
                    continue;
                }
            }

            index++;
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
            } else
                index++;
        }
    }

    public int modifyScore() {
        this.score += this.popularity * 5;
        this.score += this.religionPopularityRate * 3;

        if (this.user.getHighScore() < this.score)
            this.user.setHighScore(this.score);

        return this.score;
    }

    public void destroy() {
        for (Building building : buildings) {
            building.destroy();
        }
        for (MilitaryUnit militaryUnit : militaryUnits) {
            militaryUnit.getLocation().getMilitaryUnits().remove(militaryUnit);
            militaryUnit.getLoyalty().getMilitaryUnits().remove(militaryUnit);
        }
        this.score = 0;
        this.isDead = true;
    }

    public boolean isDead() {
        return isDead;
    }
}
