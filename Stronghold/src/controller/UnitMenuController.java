package controller;

import model.people.Troop;
import model.user.User;
import view.enums.messages.UnitMenuMessages;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class UnitMenuController {
    private static User currentUser;
    private static ArrayList<Troop> unit;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void setUnit(ArrayList<Troop> unit) {
        UnitMenuController.unit = unit;
    }


    public UnitMenuMessages moveUnit(Matcher matcher){
        return null;
    }
    public UnitMenuMessages patrolUnit(Matcher matcher){
        return null;
    }
    public UnitMenuMessages setUnitState(Matcher matcher){
        return null;
    }
    public UnitMenuMessages attack(Matcher matcher){
        return null;
    }
    public UnitMenuMessages pourOil(Matcher matcher){
        return null;
    }
    public UnitMenuMessages digTunnel(Matcher matcher){
        return null;
    }
    public UnitMenuMessages buildEquipment(Matcher matcher){
        return null;
    }
    public UnitMenuMessages disbandUnit(){
        return null;
    }
    public UnitMenuMessages digMoat(Matcher matcher){
        return null;
    }
}
