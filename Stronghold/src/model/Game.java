package model;

import java.util.ArrayList;

public class Game {
    private ArrayList<Government> governments;
    int turns;

    public Game() {
        this.governments = new ArrayList<>();
    }

    public ArrayList<Government> getGovernments() {
        return governments;
    }
}
