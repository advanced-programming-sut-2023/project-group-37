package controller;

import model.people.MilitaryUnit;
import model.people.Troop;
import model.user.User;
import view.enums.Message;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class UnitMenuController {
    private static User currentUser;
    private static ArrayList<MilitaryUnit> unit;
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    public static void setUnit(ArrayList<MilitaryUnit> unit) {
        UnitMenuController.unit = unit;
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
