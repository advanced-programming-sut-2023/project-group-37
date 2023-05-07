package controller;


import java.util.ArrayList;
import java.util.LinkedList;

import model.game.Tile;
import model.game.Map;

public class MultiMenuFunctions {

    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    public static String deleteQuotations(String string) {

        if (string == null)
            return "";

        StringBuilder stringBuilder = new StringBuilder(string);
        if (string.matches("\".+\"")) {

            stringBuilder.deleteCharAt(0);
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);

            string = stringBuilder.toString();
        }
        return string;
    }

    private static ArrayList<Tile> setNextNumber(ArrayList<Tile> targets, int nextNumber, boolean[][] tilePassability, Map map) {
        ArrayList<Tile> result = new ArrayList<>();
        Tile nextTile;
        int x,y;

        for (Tile tile : targets) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (i != 0 || j != 0) {
                        x = tile.getX() + i;
                        y = tile.getY() + j;
                        nextTile = map.getTileByLocation(x,y);
                        if (nextTile != null) {
                            if (nextTile.number == 0 && tilePassability[x][y]) {
                                nextTile.number = nextNumber;
                                result.add(nextTile);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    private static Tile getNeighbor(Tile tile, ArrayList<Tile> targets) {
        for (Tile neighbor : targets) {
            if (Math.abs((neighbor.getY())+ neighbor.getX()) - (tile.getY()) + tile.getX() == 1)
                return neighbor;
        }
        return null;
    }

    public static LinkedList<Tile> routeFinder(Tile origin, Tile destination, Map map) {
        map.resetNumbers();
        boolean[][] tilePassability = map.getTilesPassability();

        ArrayList<ArrayList<Tile>> targets = new ArrayList<>();
        targets.add(new ArrayList<>());
        targets.get(0).add(origin);

        int number = 1;
        while (targets.size() > 0 && !targets.get(number-1).contains(destination)) {
            targets.add(setNextNumber(targets.get(targets.size()-1), number, tilePassability, map));
            number++;
        }
        if (targets.get(targets.size()-1).size() == 0)
            return null;

        LinkedList<Tile> result = new LinkedList<>();
        result.add(0,origin);
        result.add(number, destination);

        Tile preTile;
        Tile tile = destination;
        int index = number-1;
        while (index > 0) {
            preTile = getNeighbor(tile, targets.get(number));
            result.add(index, preTile);
            tile = preTile;
            index --;
        }

        return result;
    }
}
