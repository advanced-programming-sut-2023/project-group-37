package controller;


import java.util.ArrayList;
import java.util.LinkedList;

import model.buildings.Building;
import model.buildings.DefensiveBuilding;
import model.game.Texture;
import model.game.Tile;
import model.game.Map;
import model.people.MilitaryUnit;

public class MultiMenuFunctions {

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
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
        int x, y;

        for (Tile tile : targets) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (i != 0 || j != 0) {
                        x = tile.getX() + i;
                        y = tile.getY() + j;
                        nextTile = map.getTileByLocation(x, y);
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
            if (Math.abs((neighbor.getY()) + neighbor.getX()) - (tile.getY()) + tile.getX() == 1)
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
        while (targets.size() > 0 && !targets.get(number - 1).contains(destination)) {
            targets.add(setNextNumber(targets.get(targets.size() - 1), number, tilePassability, map));
            number++;
        }
        if (targets.get(targets.size() - 1).size() == 0)
            return routeFinder2(origin, destination, map);

        return getFinalRoute(origin, destination, targets, number);
    }

    private static LinkedList<Tile> routeFinder2(Tile origin, Tile destination, Map map) {
        boolean canUnitsGoUp = true;

        for (MilitaryUnit militaryUnit : origin.getMilitaryUnits()) {
            if (!militaryUnit.canGoUp()) {
                canUnitsGoUp = false;
                break;
            }
        }
        if (!canUnitsGoUp)
            return null;

        map.resetNumbers();
        boolean[][] tilePassability = map.getTilesPassability();
        Building building;
        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                if ((building = map.getTileByLocation(i, j).getBuilding()) != null) {
                    if (building instanceof DefensiveBuilding) {
                        tilePassability[i][j] = ((DefensiveBuilding) building).canBeReached();
                    }
                }
            }
        }

        ArrayList<ArrayList<Tile>> targets = new ArrayList<>();
        targets.add(new ArrayList<>());
        targets.get(0).add(origin);

        int number = 1;
        while (targets.size() > 0 && !targets.get(number - 1).contains(destination)) {
            targets.add(setNextNumber(targets.get(targets.size() - 1), number, tilePassability, map));
            number++;
        }
        if (targets.get(targets.size() - 1).size() == 0)
            return null;

        return getFinalRoute(origin, destination, targets, number);
    }

    private static LinkedList<Tile> getFinalRoute(Tile origin, Tile destination, ArrayList<ArrayList<Tile>> targets, int number) {
        LinkedList<Tile> result = new LinkedList<>();
        result.add(0, origin);
        result.add(number, destination);

        Tile preTile;
        Tile tile = destination;
        int index = number - 1;
        while (index > 0) {
            preTile = getNeighbor(tile, targets.get(number));
            result.add(index, preTile);
            tile = preTile;
            index--;
        }

        return result;
    }

    public static double distance(Tile tile1, Tile tile2) {
        int x1 = tile1.getX(), y1 = tile1.getY(), x2 = tile2.getX(), y2 = tile2.getY();
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public static Tile getMiddle(Tile tile1, Tile tile2, Map map) {
        int x = (tile2.getX() - tile1.getX()) / 2;
        int y = (tile2.getY() - tile1.getY()) / 2;
        return map.getTileByLocation(x, y);
    }

    public static Tile getNearestPassableTileByLocation(Tile tile, Map map) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (map.getPassabilityByLocation(tile.getX() + i, tile.getY() + j))
                    return map.getTileByLocation(tile.getX() + i, tile.getY() + j);
            }
        }
        return tile;
    }

    public static Tile getNearestMoatTileByLocation(Tile tile, Map map) {
        Tile target;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((target = map.getTileByLocation(tile.getX() + i, tile.getY() + j)).getTexture() == Texture.MOAT)
                    return target;
            }
        }
        return tile;
    }
}
