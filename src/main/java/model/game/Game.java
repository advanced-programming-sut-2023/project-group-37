package model.game;

import controller.GameMenuController;
import model.buildings.Building;
import model.buildings.BuildingType;

import java.util.ArrayList;

public class Game {
    private final GameMenuController gameMenuController;
    private final Map map;
    private final int turns;
    private final ArrayList<Government> governments;
    private Government currentTurnGovernment;
    private int index;

    public Game(GameMenuController gameMenuController, Map map, int turns, ArrayList<Government> governments) {
        this.map = map;
        this.turns = turns;
        this.governments = governments;
        this.currentTurnGovernment = governments.get(0);
        this.gameMenuController = gameMenuController;
        gameMenuController.setCurrentGovernment(currentTurnGovernment);
        index = 0;
    }

    public Government getGovernmentByUsername(String username) {
        for (Government government : governments) {
            if (government.getUser().getUsername().equals(username))
                return government;
        }
        return null;
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

    public void addGovernment(Government government) {
        this.governments.add(government);
    }

    public void goNextTurn() {
        if (index == governments.size() - 1) {
            //TODO : do changes
            for (Government government : governments) {
                government.distributeFood();
                government.receiveTax();
                government.setPopularity(government.getPopularity() + government.getFearRate());
                int innCount = 0;
                for (Building building : government.getBuildings()) {
                    if (building.getType() == BuildingType.HOVEL)
                        government.addPeasant(8);
                    else {
                        if (building.getType() == BuildingType.INN)
                            innCount++;
                        // I decrease sum of rawMaterials!
                        government.removeItem(building.getType().getRawMaterial(),
                                building.getType().getRawMaterialUses() +
                                        building.getType().getRawMaterialUsesForSecond());
                        government.addItem(building.getType().getProduct(), building.getType().getProductProvides());
                        government.addItem(building.getType().getSecondProduct(), 1);
                        // TODO: check for storage being fulled!
                    }
                }
                government.setPopularity(government.getPopularity() + Math.min(4, innCount) * 2);
                government.setPopularity(government.getPopularity() > 100 ? 100 : Math.max(government.getPopularity(), 0));
            }
        }

        index = (index + 1) % governments.size();
        currentTurnGovernment = governments.get(index);
        gameMenuController.setCurrentGovernment(currentTurnGovernment);
    }
}