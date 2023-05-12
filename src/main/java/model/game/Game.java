package model.game;

import controller.GameMenuController;
import model.people.MilitaryUnit;
import model.people.MilitaryUnitStance;

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
        this.turnNumber = 1;
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

    public void goNextTurn() {
        if (index == governments.size() - 1) {

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

        index = (index + 1) % governments.size();
        currentTurnGovernment = governments.get(index);
        gameMenuController.setCurrentGovernment(currentTurnGovernment);
        turnNumber++;
    }
}
