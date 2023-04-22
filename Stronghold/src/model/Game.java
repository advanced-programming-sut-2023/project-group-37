package model;

import java.util.ArrayList;

public class Game {
    private final ArrayList<Government> governments = new ArrayList<>();
    private final Map map;
    int turns;

    public Game(Map map) {
        this.map = map;
    }

    public ArrayList<Government> getGovernments() {
        return governments;
    }
}