package model.game;

import controller.GameController;
import controller.MultiMenuFunctions;
import model.buildings.*;
import model.people.*;
import view.animation.FaceAnimation;

import java.util.ArrayList;

public class Game {
    private final GameController gameController;
    private final Map map;
    //private final int turns;
    private final ArrayList<Government> governments;
    private Government currentTurnGovernment;
    private int index;
    private int turnNumber;

    public Game(Map map, ArrayList<Government> governments) {
        this.map = map;
        this.governments = governments;
        this.currentTurnGovernment = governments.get(0);
        this.gameController = GameController.getInstance();
        this.gameController.setCurrentGovernment(this.currentTurnGovernment);
        this.index = 0;
    }

    public GameController getGameMenuController() {
        return gameController;
    }

    public Government getGovernmentByUsername(String username) {
        for (Government government : this.governments) {
            if (government.getUser().getUsername().equals(username))
                return government;
        }
        return null;
    }

    public int getIndex() {
        return this.index;
    }

    public int getTurnNumber() {
        return this.turnNumber;
    }

    public Map getMap() {
        return this.map;
    }

//    public int getTurns() {
//        return this.turns;
//    }

    public ArrayList<Government> getGovernments() {
        return this.governments;
    }

    public Government getCurrentTurnGovernment() {
        return this.currentTurnGovernment;
    }

    public void goToNextTurn() {
        if (this.index == this.governments.size() - 1) {
            System.out.println(0);
            for (Government government : this.governments) {
                int innCount = 0, churchCount = 0, cathedralCount = 0, stableCount = 0;
                for (Building building : government.getBuildings()) {
                    if (building instanceof DefensiveBuilding)
                        continue;

                    BuildingType type = building.getType();

                    if (type == BuildingType.HOVEL)
                        government.addPeasant(8);
                    else if (type == BuildingType.CHURCH)
                        churchCount++;
                    else if (type == BuildingType.CATHEDRAL)
                        cathedralCount++;
                    else if (type == BuildingType.STABLE)
                        stableCount++;
                    else if (building instanceof SiegeTent) {
                        MilitaryMachine machine = new MilitaryMachine(government,
                                ((SiegeTent) building).getFormingMachine(), building.getLocation());
                        government.getBuildings().remove(building);
                        building.getLocation().removeBuilding();
                        int operatorsNeeded = ((SiegeTent) building).getFormingMachine().getOperatorsNeeded();
                        for (int i = building.getLocation().getMilitaryUnits().size() - 1; i >= 0; i--) {
                            government.getMilitaryUnits().remove(building.getLocation().getMilitaryUnits().get(i));
                            building.getLocation().getMilitaryUnits().remove(building.getLocation().getMilitaryUnits().get(i));
                            machine.assignOperator((Troop) building.getLocation().getMilitaryUnits().get(i));
                            operatorsNeeded--;
                            if (operatorsNeeded == 0)
                                break;
                        }
                        government.getMilitaryUnits().add(machine);
                    } else {
                        if (type == BuildingType.INN)
                            innCount++;

                        if ((type == BuildingType.TANNER &&
                                government.getUniqueBuilding(BuildingType.TANNER) != null) || type.getRawMaterial() == null) {
                            if (government.getFreeSpace(government.getTargetRepository(type.getProduct())) >=
                                    (int) Math.ceil(type.getProductProvides() * (1 - (double) government.getFearRate() / 6)))
                                government.addItem(type.getProduct(), (int) Math.ceil(type.getProductProvides() *
                                        (1 - (double) government.getFearRate() / 6)));
                        } else if (government.getItemAmount(type.getRawMaterial()) >=
                                (int) Math.ceil(type.getRawMaterialUses() * (1 - (double) government.getFearRate() / 6))
                                        + (int) Math.ceil(type.getRawMaterialUsesForSecond() *
                                        (1 - (double) government.getFearRate() / 6))) {

                            government.removeItem(type.getRawMaterial(), type.getRawMaterialUses() +
                                    (int) Math.ceil(type.getRawMaterialUsesForSecond() *
                                            (1 - (double) government.getFearRate() / 6)));
                            if ((type.getSecondProduct() == null && government.getFreeSpace
                                    (government.getTargetRepository(type.getProduct())) >=
                                    (int) Math.ceil(type.getProductProvides() * (1 - (double) government.getFearRate() / 6))) ||
                                    (type.getSecondProduct() != null && government.getFreeSpace
                                            (government.getTargetRepository(type.getProduct())) >=
                                            (int) Math.ceil((double) government.getFearRate() / 6) + (int) Math.ceil(type.getProductProvides() *
                                                    (1 - (double) government.getFearRate() / 6)))) {
                                government.addItem(type.getProduct(), (int) Math.ceil(type.getProductProvides() *
                                        (1 - (double) government.getFearRate() / 6)));
                                government.addItem(type.getSecondProduct(), (int) Math.ceil(
                                        ((double) government.getFearRate() / 6)));
                            } else {
                                government.addItem(type.getRawMaterial(), (type.getRawMaterialUses() +
                                        type.getRawMaterialUsesForSecond()) *
                                        (int) Math.ceil(1 - (double) government.getFearRate() / 6));
                            }
                        }
                    }
                }
                government.distributeFood();
                government.receiveTax();
                government.setPopularity(government.getPopularity() + government.getFearRate());
                government.setHorseCount(4 * stableCount);
                government.setReligionPopularityRate(Math.min(4, (8 * churchCount + 16 * cathedralCount) / 24));
                government.setPopularity(government.getPopularity() + Math.min(4, innCount) * 2);
                government.setPopularity(government.getPopularity() + government.getReligionPopularityRate());
                government.setPopularity(government.getPopularity() > 100 ? 100 : Math.max(government.getPopularity(), 0));
            }

            // MOVE AND STANCE:
            double range;
            Tile target;

            for (Government government : this.governments) {
                for (MilitaryUnit militaryUnit : government.getMilitaryUnits()) {
                    if (militaryUnit.isOnMove())
                        militaryUnit.move();

                    else if (militaryUnit.isOnPatrol())
                        militaryUnit.patrol();

                    else if (militaryUnit.hasMoatTarget())
                        militaryUnit.getMoatTarget().changeMoat();

                    if (militaryUnit instanceof Troop troop) {
                        if (troop.getType() == TroopType.TUNNELER)
                            continue;
                    }

                    else {
                        if (militaryUnit.getStance() == MilitaryUnitStance.STANDING)
                            range = militaryUnit.getRange();
                        else range = 2 * militaryUnit.getRange();

                        firstFor:
                        for (int i = 0; i < range + 1; i++) {
                            for (int j = 0; j < range + 1; j++) {

                                if (Math.sqrt(i * i + j * j) < range + 0.2) {
                                    // i : + AND j : +
                                    target = this.map.getTileByLocation(militaryUnit.getLocation().getLocationX() + i,
                                            militaryUnit.getLocation().getLocationY() + j);

                                    if (setEnemyTargetForDefence(target, government, militaryUnit)) break firstFor;
                                    // i : - AND j : +
                                    target = this.map.getTileByLocation(militaryUnit.getLocation().getLocationX() - i,
                                            militaryUnit.getLocation().getLocationY() + j);

                                    if (setEnemyTargetForDefence(target, government, militaryUnit)) break;
                                    // i : + AND j : -
                                    target = this.map.getTileByLocation(militaryUnit.getLocation().getLocationX() + i,
                                            militaryUnit.getLocation().getLocationY() - j);

                                    if (setEnemyTargetForDefence(target, government, militaryUnit)) break;
                                    // i : - AND j : -
                                    target = this.map.getTileByLocation(militaryUnit.getLocation().getLocationX() - i,
                                            militaryUnit.getLocation().getLocationY() - j);

                                    if (setEnemyTargetForDefence(target, government, militaryUnit)) break;
                                }
                            }
                        }
                    }
                    if (militaryUnit.isOnMove())
                        militaryUnit.move();

                    else if (militaryUnit.isOnPatrol())
                        militaryUnit.patrol();
                }
            }

            // FIGHT :
            for (Government government : this.governments) {
                for (MilitaryUnit militaryUnit : government.getMilitaryUnits())
                    militaryUnit.attack();
            }

            for (Government government : this.governments) {
                government.removeDeadUnits();
                government.removeDestroyedBuildings();
            }
            for (Government government : this.governments)
                for (Building building : government.getBuildings())
                    if (building instanceof DefensiveBuilding && (
                            ((DefensiveBuilding) building).getDefensiveType() == DefensiveBuildingType.SMALL_GATEHOUSE ||
                                    ((DefensiveBuilding) building).getDefensiveType() == DefensiveBuildingType.LARGE_GATEHOUSE))
                        ((DefensiveBuilding) building).setOwner();

            map.updateImages();
        }
        do {
            this.index = (this.index + 1) % this.governments.size();
        } while (this.governments.get(index).isDead());
        this.currentTurnGovernment = this.governments.get(this.index);
        this.gameController.setCurrentGovernment(this.currentTurnGovernment);
        this.turnNumber++;
    }

    private boolean setEnemyTargetForDefence(Tile target, Government government, MilitaryUnit militaryUnit) {
        if (target.hasEnemy(government)) {
            if (MultiMenuFunctions.distance(militaryUnit.getLocation(), target) > militaryUnit.getRange() + 0.2) {
                militaryUnit.setRoute(MultiMenuFunctions.routeFinder(militaryUnit.getLocation(),
                        MultiMenuFunctions.getMiddle(militaryUnit.getLocation(), target, this.map), this.map));
            }
            militaryUnit.setTarget(target);
            return true;
        }
        return false;
    }

    public Government getNextTurnGovernment() {
        return this.governments.get((index + 1) % this.governments.size());
    }
}