package model.game;

import model.buildings.Storage;
import model.user.User;

import java.util.ArrayList;

public class Government {
    private final User user;
    private final Color color;
    private int gold;
    private final ArrayList<Storage> stockpile;
    private final ArrayList<Storage> granary;
    private final ArrayList<Storage> armory;
    private int popularity;

    public Government(User user, Color color) {
        this.user = user;
        this.color = color;
        // TODO: set default value for gold!
        this.gold = 0;
        // TODO: set default resources!
        this.stockpile = new ArrayList<>();
        this.granary = new ArrayList<>();
        this.armory = new ArrayList<>();
        // TODO: set default popularity!
        this.popularity = 100;
    }

    public Color getColor() {
        return this.color;
    }

    public int getGold() {
        return this.gold;
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

    private boolean addToTargetRepository(ArrayList<Storage> repository, Item item, int amount) {
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
    public boolean addItem(Item item, int amount) {
        switch (item.getCategory()) {
            case FOODS -> {return addToTargetRepository(granary, item, amount);}
            case WEAPONS -> {return addToTargetRepository(armory, item, amount);}
            case RESOURCES -> {return addToTargetRepository(stockpile, item, amount);}
        }
        return false;
    }

    private boolean removeFromTargetRepository(ArrayList<Storage> repository, Item item, int amount) {
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

    public boolean removeItem(Item item, int amount) {
        switch (item.getCategory()) {
            case FOODS -> {return removeFromTargetRepository(granary, item, amount);}
            case WEAPONS -> {return removeFromTargetRepository(armory, item, amount);}
            case RESOURCES -> {return removeFromTargetRepository(stockpile, item, amount);}
        }
        return false;
    }

    public void checkForHighScore() {
        int score = 0; // todo : handle score
        if (user.getHighScore() < score)
            user.setHighScore(score);
    }
}
