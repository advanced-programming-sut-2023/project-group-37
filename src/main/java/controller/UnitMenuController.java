package controller;

import model.game.Game;
import model.game.Government;
import model.people.MilitaryUnit;
import view.enums.Message;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class UnitMenuController {
    private static Game game;
    private static Government government;
    private static ArrayList<MilitaryUnit> unit;
    public static void setGovernment(Government government) {
        UnitMenuController.government = government;
    }
    public static void setUnit(ArrayList<MilitaryUnit> unit) {
        UnitMenuController.unit = unit;
    }

    public static void setGame(Game game) {
        UnitMenuController.game = game;
    }

    public Message moveUnit(Matcher matcher){
        return null;
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
