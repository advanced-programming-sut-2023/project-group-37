package controller;

import model.game.Map;
import view.enums.Message;

import java.util.regex.Matcher;

public class MapMenuController {
    private static Map map;

    public static void setMap(Map map) {
        MapMenuController.map = map;
    }

    public Message moveMap(Matcher matcher) {
        return null;
    }

    public Message showDetails(Matcher matcher) {
        return null;
    }
}
