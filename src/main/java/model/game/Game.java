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
        this.gameMenuController.setCurrentGovernment(this.currentTurnGovernment);
        this.index = 0;
    }

    public Government getGovernmentByUsername(String username) {
        for (Government government : this.governments) {
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

    public void goToNextTurn() {
        if (this.index == this.governments.size() - 1) {
            //TODO : do changes
            for (Government government : this.governments) {
                government.distributeFood();
                government.receiveTax();
                government.setPopularity(government.getPopularity() + government.getFearRate());
                int innCount = 0;
                for (Building building : government.getBuildings()) {

                    BuildingType type = building.getType();

                    if (type == BuildingType.HOVEL)
                        government.addPeasant(8);
                    else {
                        if (type == BuildingType.INN)
                            innCount++;
                        if (type.getRawMaterial() == null)
                            government.addItem(type.getProduct(), (int) (type.getProductProvides() *
                                    (1 - (double) this.currentTurnGovernment.getFearRate() / 6)) + 1);
                        else {
                            government.removeItem(type.getRawMaterial(), type.getRawMaterialUses() +
                                    type.getRawMaterialUsesForSecond());
                            government.addItem(type.getProduct(), type.getProductProvides());
                            government.addItem(type.getSecondProduct(), 1);
                        }

                        // TODO: check for storage being fulled!
                    }
                }
                government.setPopularity(government.getPopularity() + Math.min(4, innCount) * 2);
                government.setPopularity(government.getPopularity() > 100 ? 100 : Math.max(government.getPopularity(), 0));
            }
        }
        this.index = (this.index + 1) % this.governments.size();
        this.currentTurnGovernment = this.governments.get(this.index);
        this.gameMenuController.setCurrentGovernment(this.currentTurnGovernment);
    }
}

// Some bullshit:
//if (type.getProductProvides() >= 3) {
//                            government.removeItem(type.getRawMaterial(),
//                                    (int) (((type.getRawMaterialUses() + type.getRawMaterialUsesForSecond())) *
//                                            (1 + (double) this.currentTurnGovernment.getFearRate() / 6)) + 1);
//                            government.addItem(type.getProduct(), (int) ((type.getProductProvides()) *
//                                    (1 + (double) this.currentTurnGovernment.getFearRate() / 6)) + 1);
//                            government.addItem(type.getSecondProduct(),
//                                    (int) ((double) (this.currentTurnGovernment.getFearRate() / 6) + 1));
//                        } else {
//                        }