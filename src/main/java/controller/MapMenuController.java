package controller;

import model.game.Game;
import model.game.Government;
import model.game.Map;
import view.enums.Message;

import java.util.regex.Matcher;

public class MapMenuController {
    private static Game game;
    private static Map map;
    private Government government;


    public void setGovernment(Government government) {
        this.government = government;
    }

    public static void setGame(Game game) {
        MapMenuController.game = game;
        MapMenuController.map = game.getMap();
    }

    public String showMap(int x, int y) {
        map.setTilesState();
        int minX = x - 30;
        int maxX = x + 30;
        int minY = y -30;
        int maxY = y + 30;

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
