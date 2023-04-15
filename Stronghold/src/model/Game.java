package model;

import java.util.ArrayList;

public class Game {
    private final ArrayList<Government> governments;
    private Map map;
    int turns;

    public Game(int x,int y) {
        this.map = new Map(x,y);
        this.governments = new ArrayList<>();
    }

    public ArrayList<Government> getGovernments() {
        return governments;
    }
}
