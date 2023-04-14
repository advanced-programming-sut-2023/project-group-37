package model;

import model.buildings.Granary;
import model.buildings.Stockpile;
import model.enums.Foods;
import model.enums.Resources;

import java.util.ArrayList;
import java.util.HashMap;

public class Government {

    private static Government currentGovernment;
    private Colors color;

    private int gold;

    private ArrayList<Stockpile> stockpile;

    private ArrayList<Granary> granary;

    private int popularity;

    private int foodRate;

    private int taxRate;

    private int fearRate;

    private HashMap<String, Integer> popularityFactors;

    public Government(Colors color) {
        // TODO: fill here
    }

    public ArrayList<Stockpile> getStockpile() {
        return this.stockpile;
    }

    public static Government getCurrentGovernment() {
        return currentGovernment;
    }

    public static void setCurrentGovernment(Government currentGovernment) {
        Government.currentGovernment = currentGovernment;
    }
}
