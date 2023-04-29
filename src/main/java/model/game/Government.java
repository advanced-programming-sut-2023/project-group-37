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

    public void addItem(Item item, int amount) {
        gold -= amount * item.getBuyCost();
        // TODO : handle
    }

    public void removeItem(Item item, int amount) {
        gold += amount * item.getSellCost();
        //TODO : handle
    }

    public void checkForHighScore() {
        int score = 0; // todo : handle score
        if (user.getHighScore() < score)
            user.setHighScore(score);
    }
}
