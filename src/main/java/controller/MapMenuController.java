package controller;

import model.game.Game;
import model.game.Government;
import model.game.Map;
import view.enums.Message;

import java.util.regex.Matcher;

public class MapMenuController {
    private static Game game;
    private static Map map;
    private static Government government;


    public static void setGovernment(Government government) {
        MapMenuController.government = government;
    }

    public static void setGame(Game game) {
        MapMenuController.game = game;
        MapMenuController.map = game.getMap();
    }

    public String showMap() {
        map.setTilesState();
        //todo;
        return null;
    }

    public Message moveMap(Matcher matcher) {
        map.setTilesState();
        return null;
    }

    public Message showDetails(Matcher matcher) {
        return null;
    }

}
