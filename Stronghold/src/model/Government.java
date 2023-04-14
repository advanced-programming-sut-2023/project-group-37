package model;

import model.enums.Foods;
import model.enums.Resources;

import java.util.ArrayList;
import java.util.HashMap;

public class Government {

    private Colors color;
    private HashMap<Resources, Integer> stockpile;

    private HashMap<Foods, Integer> granary;

    private int popularity;

    private int foodRate;

    private int taxRate;

    private int fearRate;

    private int gold;

    private final ArrayList<Trade> trades = new ArrayList<>();

    private HashMap<String, Integer> popularityFactors;

    public Government(Colors color) {
        // TODO: fill here
    }

    public HashMap<Resources, Integer> getResources() {
        return this.stockpile;
    }
}
