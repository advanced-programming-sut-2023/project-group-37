package controller.viewControllers;

import controller.MultiMenuFunctions;
import model.buildings.*;
import model.game.Game;
import model.game.Government;
import model.game.Tile;
import model.people.*;
import view.enums.Message;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;

public class UnitMenuController {
    private static final UnitMenuController unitMenuController;
    private Game currentGame;
    private Government currentGovernment;
    private ArrayList<MilitaryUnit> currentUnit;
    private Tile currentLocation;

    static {
        unitMenuController = new UnitMenuController();
    }

    private UnitMenuController() {

    }

    public static UnitMenuController getInstance() {
        return unitMenuController;
    }

    public void setCurrentGovernment(Government currentGovernment) {
        this.currentGovernment = currentGovernment;
    }

    public void setUnit(ArrayList<MilitaryUnit> unit, Tile location) {
        this.currentUnit = unit;
        this.currentLocation = location;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public String selectUnitWithType(String type) {
        TroopType troopType = TroopType.getTroopTypeByName(type);
        if (troopType != null) {
            ArrayList<Troop> troops = new ArrayList<>();
            for (MilitaryUnit militaryUnit : this.currentUnit)
                if (militaryUnit instanceof Troop troop)
                    if (troop.getType() == troopType)
                        troops.add(troop);

            if (troops.size() == 0)
                return Message.NO_UNIT_WITH_THIS_TYPE.toString();

            this.currentUnit = new ArrayList<>();
            this.currentUnit.addAll(troops);
        } else {
            MilitaryMachineType militaryMachineType = MilitaryMachineType.getMilitaryMachineTypeByName(type);
            if (militaryMachineType == null)
                return Message.TYPE_NOT_EXISTS.toString();

            ArrayList<MilitaryMachine> militaryMachines = new ArrayList<>();
            for (MilitaryUnit militaryUnit : this.currentUnit)
                if (militaryUnit instanceof MilitaryMachine militaryMachine)
                    if (militaryMachine.getType() == militaryMachineType)
                        militaryMachines.add(militaryMachine);
            if (militaryMachines.size() == 0)
                return Message.NO_UNIT_WITH_THIS_TYPE.toString();

            this.currentUnit = new ArrayList<>();
            this.currentUnit.addAll(militaryMachines);
        }
        return Message.SUCCESS.toString();
    }

    private boolean isUnitOfType(TroopType troopType) {
        for (MilitaryUnit militaryUnit : this.currentUnit) {
            if (militaryUnit instanceof Troop troop) {
                if (troop.getType() != troopType)
                    return false;
            } else
                return false;
        }
        return true;
    }

    private boolean isUnitOfType(MilitaryMachineType militaryMachineType) {
        for (MilitaryUnit militaryUnit : this.currentUnit) {
            if (militaryUnit instanceof MilitaryMachine militaryMachine) {
                if (militaryMachine.getType() != militaryMachineType)
                    return false;
            } else
                return false;
        }
        return true;
    }

    public String moveUnit(int x, int y) {
        Tile destination = this.currentGame.getMap().getTileByLocation(x, y);

        if (destination == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        if (destination.equals(this.currentLocation))
            return Message.CURRENT_LOCATION.toString();

        LinkedList<Tile> route = MultiMenuFunctions.routeFinder(this.currentLocation, destination,
                this.currentGame.getMap());

        if (route == null)
            return Message.NO_ROUTS_FOUND.toString();

        if (destination.getBuilding() != null) {
            if (destination.getBuilding() instanceof DefensiveBuilding defensiveBuilding) {
                if (defensiveBuilding.getType().getCapacity() < defensiveBuilding.getLocation().getMilitaryUnits().size()
                        + currentUnit.size())
                    return Message.NOT_ENOUGH_SPACE.toString();
            }
        }
        LinkedList<Tile> routeCopy;

        for (MilitaryUnit militaryUnit : this.currentUnit) {
            routeCopy = new LinkedList<>(route);
            militaryUnit.setRoute(routeCopy);
        }

        return Message.SUCCESS.toString();
    }

    public String patrolUnit(int x1, int y1, int x2, int y2) {
        Tile tile1 = this.currentGame.getMap().getTileByLocation(x1, y1);
        Tile tile2 = this.currentGame.getMap().getTileByLocation(x2, y2);

        if (tile1 == null || tile2 == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        if (tile1 != this.currentLocation) {
            LinkedList<Tile> route = MultiMenuFunctions.routeFinder(this.currentLocation, tile1, this.currentGame.getMap());
            for (MilitaryUnit militaryUnit : this.currentUnit)
                militaryUnit.setRoute(route);
        }

        LinkedList<Tile> patrolRoute = MultiMenuFunctions.routeFinder(tile1, tile2, this.currentGame.getMap());
        LinkedList<Tile> patrolRouteCopy ;

        for (MilitaryUnit militaryUnit : this.currentUnit) {
            patrolRouteCopy = new LinkedList<>(patrolRoute);
            militaryUnit.setPatrol(patrolRouteCopy);
        }

        return "Patrol target set to :" + x1 + ", " + y1 + "and " + x2 + ", " +y2;
    }

    public String cancelMove() {
        boolean isOnMove = false;
        for (MilitaryUnit militaryUnit : this.currentUnit) {
            if (militaryUnit.isOnMove()) {
                isOnMove = true;
                militaryUnit.cancelMove();
            }
        }
        if (isOnMove)
            return Message.CANCEL.toString();
        return Message.NO_MOVER.toString();
    }

    public String cancelPatrol() {
        boolean isOnPatrol = false;
        for (MilitaryUnit militaryUnit : this.currentUnit) {
            if (militaryUnit.isOnPatrol()) {
                isOnPatrol = true;
                militaryUnit.cancelPatrol();
            }
        }
        if (isOnPatrol)
            return Message.CANCEL.toString();
        return Message.NO_PATROL.toString();
    }

    public String setUnitState(String state) {
        MilitaryUnitStance stance = MilitaryUnitStance.getByState(state);
        if (stance == null)
            return Message.INVALID_STANCE.toString();

        for (MilitaryUnit militaryUnit : this.currentUnit) {
            militaryUnit.setStance(stance);
        }
        return Message.STANCE_IS_SET.toString();
    }

    public String attack(int x, int y, boolean isEarth) {
        Tile target = this.currentGame.getMap().getTileByLocation(x, y);
        if (target == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        boolean isEnemy = false;
        if (target.getBuilding() != null)
            if (target.getBuilding().getLoyalty() != this.currentGovernment)
                isEnemy = true;

        if (!isEnemy)
            if (target.getMilitaryUnits().size() != 0)
                for (MilitaryUnit militaryUnit : target.getMilitaryUnits())
                    if (militaryUnit.getLoyalty() != this.currentGovernment) {
                        isEnemy = true;
                        break;
                    }

        if (!isEnemy)
            return Message.NO_ENEMY.toString();

        if (!isEarth) {
            ArrayList<MilitaryUnit> rangedUnits = new ArrayList<>();
            boolean hasRangedUnit = false;

            double distance = MultiMenuFunctions.distance(this.currentLocation, target);

            for (MilitaryUnit militaryUnit : this.currentUnit)
                if (militaryUnit.getRange() != 1) {
                    hasRangedUnit = true;
                    break;
                }

            if (!hasRangedUnit)
                return Message.NO_ARCHER.toString();

            for (MilitaryUnit militaryUnit : this.currentUnit)
                if (militaryUnit.getRange() + 0.3 > distance)
                    rangedUnits.add(militaryUnit);

            if (rangedUnits.size() == 0)
                return Message.OUT_OF_RANGE.toString();

            for (MilitaryUnit militaryUnit : rangedUnits)
                militaryUnit.setTarget(target);

            return Message.SUCCESS.toString();
        } else {
            ArrayList<MilitaryUnit> mealyUnits = new ArrayList<>();

            for (MilitaryUnit militaryUnit : this.currentUnit)
                if (militaryUnit.getRange() == 1)
                    mealyUnits.add(militaryUnit);

            if (mealyUnits.size() == 0)
                return Message.NO_MEALY_UNIT.toString();

            for (MilitaryUnit militaryUnit : mealyUnits)
                militaryUnit.setTarget(target);

            return Message.SUCCESS.toString();
        }
    }

    public Message fillContainer(Matcher matcher) {
        if (!this.isUnitOfType(TroopType.ENGINEER))
            return Message.UNIT_NOT_ENGINEER;

        Building building;
        if ((building = this.currentGovernment.getUniqueBuilding(BuildingType.OIL_SMELTER)) == null)
            return Message.NO_OIL_SMELTER;

        // TODO:

        return null;
    }

    public String pourOil(Matcher matcher) {

        return null;
    }

    public Message digTunnel(Matcher matcher) {
        if (matcher.group("x") == null || matcher.group("y") == null)
            return Message.EMPTY_FIELD;

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));

        if (this.currentGame.getMap().getTileByLocation(x, y) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS;

        if (!this.isUnitOfType(TroopType.TUNNELER))
            return Message.UNIT_NOT_TUNNELER;

        Tile location = this.currentGame.getMap().getTileByLocation(x, y);
        if (location.getBuilding() == null)
            return Message.CANNOT_DIG_TUNNEL_THERE;

        else if (((DefensiveBuilding) location.getBuilding()).getDefensiveType() != DefensiveBuildingType.WALL &&
                ((DefensiveBuilding) location.getBuilding()).getDefensiveType() != DefensiveBuildingType.LOOKOUT_TOWER &&
                ((DefensiveBuilding) location.getBuilding()).getDefensiveType() != DefensiveBuildingType.PERIMETER_TOWER &&
                ((DefensiveBuilding) location.getBuilding()).getDefensiveType() != DefensiveBuildingType.DEFENCE_TOWER)
            return Message.CANNOT_DIG_TUNNEL_THERE;

        if (MultiMenuFunctions.distance(this.currentLocation, location) > TroopType.TUNNELER.getRange() + 0.2)
            return Message.TUNNEL_TOO_FAR_FROM_ENEMY;

        this.currentLocation.setBuilding(new Building(this.currentGovernment, this.currentLocation,
                BuildingType.TUNNEL_ENTRANCE));

        for (MilitaryUnit unit : this.currentUnit)
            unit.setTarget(location);

        return Message.TUNNEL_DIG_SUCCESSFUL;
    }

    public Message buildEquipment(Matcher matcher) {
        if (!this.isUnitOfType(TroopType.ENGINEER))
            return Message.UNIT_NOT_ENGINEER;

        MilitaryMachineType type;
        if ((type = MilitaryMachineType.getMilitaryMachineTypeByName(MultiMenuFunctions.deleteQuotations(matcher.group("type")))) == null)
            return Message.INVALID_MACHINE_TYPE;

        if (this.currentLocation.getBuilding() != null &&
                !(this.currentLocation.getBuilding() instanceof DefensiveBuilding &&
                        type == MilitaryMachineType.FIRE_BALLISTA && (
                        ((DefensiveBuilding) this.currentLocation.getBuilding()).getDefensiveType() ==
                                DefensiveBuildingType.SQUARE_TOWER ||
                                ((DefensiveBuilding) this.currentLocation.getBuilding()).getDefensiveType() ==
                                        DefensiveBuildingType.ROUND_TOWER)))
            return Message.CANNOT_BUILD_MACHINE_HERE;

        if (this.currentLocation.getBuilding() == null)
            this.currentLocation.setBuilding(new SiegeTent(this.currentGovernment, this.currentLocation, type));
        else {
            MilitaryMachine machine = new MilitaryMachine(this.currentGovernment, type, this.currentLocation);
            this.currentLocation.getMilitaryUnits().add(machine);
            this.currentGovernment.getMilitaryUnits().add(machine);
            int counter = 0;
            for (int i = this.currentUnit.size() - 1; i >= 0; i--) {
                this.currentGovernment.getMilitaryUnits().remove(this.currentUnit.get(i));
                this.currentLocation.getMilitaryUnits().remove(this.currentUnit.get(i));
                machine.assignOperator((Troop) this.currentUnit.get(i));
                counter++;
                if (counter == 2)
                    break;
            }
        }
        return Message.CONSTRUCTING_SIEGE_EQUIPMENT;
    }

    public Message disbandUnit() {
        for (int i = this.currentLocation.getMilitaryUnits().size() - 1; i >= 0; i--) {
            MilitaryUnit unit = this.currentLocation.getMilitaryUnits().get(i);
            this.currentGovernment.getMilitaryUnits().remove(unit);
            this.currentLocation.getMilitaryUnits().remove(i);
            this.currentGovernment.addPeasant(unit instanceof MilitaryMachine ?
                    ((MilitaryMachine) unit).getType().getOperatorsNeeded() : 1);
        }
        return Message.DISBAND_SUCCESSFUL;
    }

    public Message digMoat(Matcher matcher) {
        if (matcher.group("x") == null || matcher.group("y") == null)
            return Message.EMPTY_FIELD;

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));

        Tile destination = this.currentGame.getMap().getTileByLocation(x, y);
        if (destination == null)
            return Message.ADDRESS_OUT_OF_BOUNDS;

        for (MilitaryUnit unit : this.currentUnit)
            if (unit instanceof MilitaryMachine || !((Troop) unit).getType().canDigMoat())
                return Message.UNIT_CANNOT_DIG_MOAT;

        Tile target = MultiMenuFunctions.getNearestPassableTileByLocation(destination, this.currentGame.getMap());
        LinkedList<Tile> route;
        if ((route = MultiMenuFunctions.routeFinder(this.currentLocation, target, this.currentGame.getMap())) == null)
            return Message.NO_ROUTS_FOUND;
        for (MilitaryUnit unit : this.currentUnit) {
            unit.setRoute(route);
            unit.setMoatTarget(destination);
        }

        return Message.SUCCESS;
    }

    public Message cancelDigMoat(Matcher matcher) {
        if (matcher.group("x") == null || matcher.group("y") == null)
            return Message.EMPTY_FIELD;

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));

        Tile destination = this.currentGame.getMap().getTileByLocation(x, y);
        if (destination == null)
            return Message.ADDRESS_OUT_OF_BOUNDS;

        for (MilitaryUnit unit : this.currentUnit)
            if (unit.hasMoatTarget()) {
                unit.cancelMoatTarget();
                unit.setRoute(null);
            }

        return Message.CANCEL_DIG_MOAT_SUCCESS;
    }

    public Message fillMoat(Matcher matcher) {
        if (matcher.group("x") == null || matcher.group("y") == null)
            return Message.EMPTY_FIELD;

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));

        Tile destination = this.currentGame.getMap().getTileByLocation(x, y);
        if (destination == null)
            return Message.ADDRESS_OUT_OF_BOUNDS;

        for (MilitaryUnit unit : this.currentUnit)
            if (unit instanceof MilitaryMachine || !((Troop) unit).getType().canDigMoat())
                return Message.UNIT_CANNOT_DIG_MOAT;

        Tile target = MultiMenuFunctions.getNearestPassableTileByLocation(destination, this.currentGame.getMap());
        LinkedList<Tile> route;
        if ((route = MultiMenuFunctions.routeFinder(this.currentLocation, target, this.currentGame.getMap())) == null)
            return Message.NO_ROUTS_FOUND;

        LinkedList<Tile> copyRoute;
        for (MilitaryUnit unit : this.currentUnit) {
            copyRoute = new LinkedList<>(route);
            unit.setRoute(copyRoute);
            unit.setMoatTarget(destination);
        }

        return Message.SUCCESS;
    }

    public Message stop() {
        for (MilitaryUnit militaryUnit : currentUnit) {
            militaryUnit.stop();
        }
        return Message.UNIT_STOPPED;
    }
}
