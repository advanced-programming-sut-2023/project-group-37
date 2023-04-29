package model.game;

import controller.GameMenuController;

import java.util.ArrayList;

public class Game {
    private final Map map;
    private final int turns;
    private final ArrayList<Government> governments;
    private Government currentTurnGovernment;
    private int index;

    public Game(Map map, int turns, ArrayList<Government> governments) {
        this.map = map;
        this.turns = turns;
        this.governments = governments;
        this.currentTurnGovernment = governments.get(0);
        GameMenuController.setGovernment(currentTurnGovernment);
        index = 0;
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

    public void goNextTurn() {
        if (index == governments.size()-1)
        {
            //todo : do all changes
        }

        index = (index+1) % governments.size();
        currentTurnGovernment = governments.get(index);
        GameMenuController.setGovernment(currentTurnGovernment);
    }
}
