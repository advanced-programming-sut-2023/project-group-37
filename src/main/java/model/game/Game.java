package model.game;

import java.util.ArrayList;

public class Game {
    private final Map map;
    private final int turns;
    private final ArrayList<Government> governments;
    private Government currentTurnGovernment;

    public Game(Map map, int turns, Government starter) {
        this.map = map;
        this.turns = turns;
        this.governments = new ArrayList<>();
        this.governments.add(starter);
        this.currentTurnGovernment = starter;
    }

    public Map getMap() {
        return this.map;
    }

    public int getTurns() {
        return this.turns;
    }

    public ArrayList<Government> getGovernments() {
        return this.governments;
    }

    public Government getCurrentTurnGovernment() {
        return this.currentTurnGovernment;
    }

    public void addGovernment(Government government){
        this.governments.add(government);
    }
}
