package controller;

import model.game.Game;
import model.game.Government;
import model.game.Map;
import model.game.Tile;
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
        int size = map.getSize();
        StringBuilder message = new StringBuilder();

        int minX = x - 30;
        int maxX = x + 30;
        int minY = y - 30;
        int maxY = y + 30;

        if (minX < 0)
            minX = 0;
        if (maxX > size - 1)
            maxX = size - 1;
        if (minY < 0)
            minY = 0;
        if (maxY > size - 1)
            maxY = size - 1;

        for (int i = minY; i < maxY; i++) {
            for (int j = minX; j < maxX; j++) {
                message.append(map.getTileByLocation(i, j).getState());
            }
            message.append("\n");
        }

        return message.toString().trim();
    }

    public Message moveMap(Matcher matcher) {
        map.setTilesState();
        return null;
    }

    public Message showDetails(Matcher matcher) {
        return null;
    }

}
