package controller;

import model.game.Game;
import model.game.Government;
import model.game.Tile;
import model.people.MilitaryUnit;
import view.enums.Message;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class UnitMenuController {
    private static Game game;
    private Government government;
    private static ArrayList<MilitaryUnit> unit;
    public void setGovernment(Government government) {
        this.government = government;
    }
    public static void setUnit(ArrayList<MilitaryUnit> unit) {
        UnitMenuController.unit = unit;
    }

    public static void setGame(Game game) {
        UnitMenuController.game = game;
    }

    public String moveUnit(int x, int y) {
        Tile origin = unit.get(0).getLocation();
        Tile destination = game.getMap().getTileByLocation(x, y);

        if (destination == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        if (destination.equals(origin))
            return Message.CURRENT_LOCATION.toString();

        ArrayList<Tile> route = MultiMenuFunctions.routeFinder(origin, destination);

        if (route == null)
            return Message.NO_ROUTS_FOUND.toString();

        for (MilitaryUnit militaryUnit : unit) {
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
