package controller;

import model.game.Game;
import model.game.Government;
import model.game.Map;
import model.game.Tile;
import view.enums.Message;

import java.util.regex.Matcher;

public class MapMenuController {
    private Game game;
    private Map map;
    private Government government;

    public void setGovernment(Government government) {
        this.government = government;
    }

    public void setGame(Game game) {
        this.game = game;
        this.map = game.getMap();
    }

    public Tile[][] showMap(int x, int y) {
        map.setTilesState();
        int size = map.getSize();

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

        Tile[][] result = new Tile[maxX - minX][maxY - minY];

        for (int i = minX; i < maxX; i++) {
            for (int j = minY; j < maxY; j++) {
                result[i-minX][j-minY] = map.getTileByLocation(i, j);
            }
        }
        return result;
    }

    public Message moveMap(Matcher matcher) {
        return null;
    }

    public Message showDetails(Matcher matcher) {
        return null;
    }

}
