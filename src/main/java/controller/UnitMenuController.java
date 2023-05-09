package controller;

import model.game.Game;
import model.game.Government;
import model.game.Tile;
import model.people.*;
import view.enums.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;

public class UnitMenuController {
    private static Game game;
    private Government government;
    private ArrayList<MilitaryUnit> unit;
    private Tile location;
    public void setGovernment(Government government) {
        this.government = government;
    }
    public void setUnit(ArrayList<MilitaryUnit> unit, Tile location) {
        this.unit = unit;
        this.location = location;
    }

    public static void setGame(Game game) {
        UnitMenuController.game = game;
    }

    public String moveUnit(int x, int y) {
        Tile destination = game.getMap().getTileByLocation(x, y);

        if (destination == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        if (destination.equals(location))
            return Message.CURRENT_LOCATION.toString();

        LinkedList<Tile> route = MultiMenuFunctions.routeFinder(location, destination, game.getMap());

        if (route == null)
            return Message.NO_ROUTS_FOUND.toString();

        for (MilitaryUnit militaryUnit : unit) {
            militaryUnit.setRoute(route);
        }

        return Message.SUCCESS.toString();
    }
    public Message patrolUnit(int x1, int y1, int x2, int y2){
        Tile tile1 = game.getMap().getTileByLocation(x1, y1), tile2 = game.getMap().getTileByLocation(x2, y2);
        if (tile1 == null || tile2 == null)
            return Message.ADDRESS_OUT_OF_BOUNDS;

        if (tile1 != location) {
            LinkedList<Tile> route = MultiMenuFunctions.routeFinder(location, tile1, game.getMap());

            for (MilitaryUnit militaryUnit : unit) {
                militaryUnit.setRoute(route);
            }
        }

        LinkedList<Tile> patrolRoute = MultiMenuFunctions.routeFinder(tile1, tile2, game.getMap());

        for (MilitaryUnit militaryUnit : unit) {
            militaryUnit.setPatrol(patrolRoute);
        }

        return Message.SUCCESS;
    }
    public String setUnitState(String state){
        for (MilitaryUnit militaryUnit : unit) {
            militaryUnit.setStance(MilitaryUnitStance.getByState(state));
        }
        return Message.STATE_IS_SET.toString();
    }
    public String attack(int x, int y, boolean isEarth){
        Tile target = game.getMap().getTileByLocation(x, y);
        if (target == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        boolean isEnemy = false;
        if (target.getBuilding() != null) {
            if (target.getBuilding().getLoyalty() != government)
                isEnemy = true;
        }

        if (!isEnemy) {
            if (target.getMilitaryUnits().size() != 0) {
                for (MilitaryUnit militaryUnit : target.getMilitaryUnits()) {
                    if (militaryUnit.getLoyalty() != government) {
                        isEnemy = true;
                        break;
                    }
                }
            }
        }

        if (!isEnemy)
            return Message.NO_ENEMY.toString();

        if (!isEarth) {
            ArrayList<MilitaryUnit> rangedUnits = new ArrayList<>();
            boolean hasRangedUnit = false;

            double distance = MultiMenuFunctions.distance(location, target);

            for (MilitaryUnit militaryUnit : unit) {
                if (militaryUnit.getRange() != 1) {
                    hasRangedUnit = true;
                    break;
                }
            }

            if (!hasRangedUnit)
                return Message.NO_ARCHER.toString();

            for (MilitaryUnit militaryUnit : unit) {
                if (militaryUnit.getRange() + 0.3 > distance)
                    rangedUnits.add(militaryUnit);
            }

            if (rangedUnits.size() == 0)
                return Message.OUT_OF_RANGE.toString();

            for (MilitaryUnit militaryUnit : rangedUnits)
                militaryUnit.setTarget(target);

            return Message.SUCCESS.toString();
        }
        else {
            ArrayList<MilitaryUnit> mealyUnits = new ArrayList<>();

            for (MilitaryUnit militaryUnit : unit) {
                if (militaryUnit.getRange() == 1)
                    mealyUnits.add(militaryUnit);
            }

            if (mealyUnits.size() == 0)
                return Message.NO_MEALY_UNIT.toString();

            for (MilitaryUnit militaryUnit : mealyUnits)
                militaryUnit.setTarget(target);

            return Message.SUCCESS.toString();
        }
    }
    public Message pourOil(Matcher matcher){
        return null;
    }
    public Message digTunnel(Matcher matcher){
        return null;
    }
    public Message buildEquipment(Matcher matcher){
        return null;
    }
    public Message disbandUnit(){
        return null;
    }
    public Message digMoat(Matcher matcher){
        return null;
    }
}
