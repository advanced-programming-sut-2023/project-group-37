package controller;

import model.game.Game;
import model.game.Government;
import model.game.Tile;
import model.people.MilitaryUnit;
import view.enums.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;

public class UnitMenuController {
    private static Game game;
    private Government government;
    private HashMap<MilitaryUnit, Integer> unit;
    private Tile location;
    public void setGovernment(Government government) {
        this.government = government;
    }
    public void setUnit(HashMap<MilitaryUnit, Integer> unit, Tile location) {
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

        for (MilitaryUnit militaryUnit : unit.keySet()) {
            militaryUnit.setRoute(route);
        }

        return Message.SUCCESS.toString();
    }
    public Message patrolUnit(Matcher matcher){
        return null;
    }
    public Message setUnitState(Matcher matcher){
        return null;
    }
    public Message attack(Matcher matcher){
        return null;
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
