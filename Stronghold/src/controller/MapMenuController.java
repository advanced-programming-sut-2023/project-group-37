package controller;

import model.Map;
import view.enums.messages.MapMenuMessages;

import java.util.regex.Matcher;

public class MapMenuController {
    private static Map map;

    public static void setMap(Map map) {
        MapMenuController.map = map;
    }

    public MapMenuMessages moveMap(Matcher matcher) {
        return null;
    }

    public MapMenuMessages showDetails(Matcher matcher) {
        return null;
    }
}
