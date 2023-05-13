package model.game;

import controller.GameMenuController;
import model.people.MilitaryUnit;
import model.people.MilitaryUnitStance;
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
    private int turnNumber;

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

    public int getIndex() {
        return index;
    }

    public int getTurnNumber() {
        return turnNumber;
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

            // MOVE AND STANCE:
            int range;
            Tile target;

            for (Government government : governments) {
                for (MilitaryUnit militaryUnit : government.getMilitaryUnits()) {
                    if (militaryUnit.isOnMove())
                        militaryUnit.move();

                    else if (militaryUnit.isOnPatrol())
                        militaryUnit.patrol();

                    else {
                        if (militaryUnit.getStance() == MilitaryUnitStance.STANDING)
                            range = militaryUnit.getRange();
                        else range = 2 * militaryUnit.getRange();

                        firstFor :for (int i = 0; i < range + 1; i++) {
                            for (int j = 0; j < range + 1; j++) {

                                if (Math.sqrt(i*i + j*j) < range + 0.2) {
                                    // i : + AND j : +
                                    target = map.getTileByLocation(militaryUnit.getLocation().getX() + i,
                                            militaryUnit.getLocation().getY() + j);

                                    if (target.hasEnemy(government)) {
                                        militaryUnit.setTarget(target);
                                        break firstFor;
                                    }
                                    // i : - AND j : +
                                    target = map.getTileByLocation(militaryUnit.getLocation().getX() - i,
                                            militaryUnit.getLocation().getY() + j);

                                    if (target.hasEnemy(government)) {
                                        militaryUnit.setTarget(target);
                                        break firstFor;
                                    }
                                    // i : + AND j : -
                                    target = map.getTileByLocation(militaryUnit.getLocation().getX() + i,
                                            militaryUnit.getLocation().getY() - j);

                                    if (target.hasEnemy(government)) {
                                        militaryUnit.setTarget(target);
                                        break firstFor;
                                    }
                                    // i : - AND j : -
                                    target = map.getTileByLocation(militaryUnit.getLocation().getX() - i,
                                            militaryUnit.getLocation().getY() - j);

                                    if (target.hasEnemy(government)) {
                                        militaryUnit.setTarget(target);
                                        break firstFor;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // FIGHT :
            for (Government government : governments) {
                for (MilitaryUnit militaryUnit : government.getMilitaryUnits()) {
                    militaryUnit.attack();
                }
            }
            for (Government government : governments) {
                government.removeDiedUnits();
                government.removeDestroyedBuildings();
            }
        }
        this.index = (this.index + 1) % this.governments.size();
        this.currentTurnGovernment = this.governments.get(this.index);
        this.gameMenuController.setCurrentGovernment(this.currentTurnGovernment);
        turnNumber++;
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