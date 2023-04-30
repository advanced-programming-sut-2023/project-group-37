package controller;

import model.game.Government;
import model.game.Map;
import model.game.Tile;
import view.enums.Message;

import java.util.regex.Matcher;

public class MapMenuController {
    private static Map map;
    private static Government government;

    public static void setMap(Map map) {
        MapMenuController.map = map;
    }

    public static void setGovernment(Government government) {
        MapMenuController.government = government;
    }

    public String showMap() {
        map.setAllTiles();
        //todo;
        return null;
    }

    public Message moveMap(Matcher matcher) {
        map.setAllTiles();
        return null;
    }

    public Message showDetails(Matcher matcher) {
        return null;
    }

}
