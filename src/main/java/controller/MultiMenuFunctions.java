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
        int x,y;

        for (Tile tile : targets) {
            x = tile.getX(); y = tile.getY();

            nextTile = map.getTileByLocation(x+1,y);
            if (nextTile != null) {
                if (nextTile.number == 0 && tilePassability[x+1][y]) {
                    nextTile.number = nextNumber;
                    result.add(nextTile);
                }
            }
            nextTile = map.getTileByLocation(x-1,y);
            if (nextTile != null) {
                if (nextTile.number == 0 && tilePassability[x-1][y]) {
                    nextTile.number = nextNumber;
                    result.add(nextTile);
                }
            }
            nextTile = map.getTileByLocation(x,y+1);
            if (nextTile != null) {
                if (nextTile.number == 0 && tilePassability[x][y+1]) {
                    nextTile.number = nextNumber;
                    result.add(nextTile);
                }
            }
            nextTile = map.getTileByLocation(x,y-1);
            if (nextTile != null) {
                if (nextTile.number == 0 && tilePassability[x][y-1]) {
                    nextTile.number = nextNumber;
                    result.add(nextTile);
                }
            }
        }
        return result;
    }

    private static boolean isNeighbor(Tile neighbor, Tile tile) {
        if (tile.getX() - neighbor.getX() == 1 || neighbor.getX() - tile.getX() == 1)
            return tile.getY() == neighbor.getY();

        if (tile.getY() - neighbor.getY() == 1 || neighbor.getY() - tile.getY() == 1)
            return tile.getX() == neighbor.getX();

        return false;
    }

    private static Tile getNeighbor(Tile tile, ArrayList<Tile> targets) {
        for (Tile neighbor : targets) {

            if (isNeighbor(neighbor, tile))
                return neighbor;
        }
        System.out.println(tile.getX() + " " + tile.getY() + " " + tile.number);
        return null;
    }

    public static LinkedList<Tile> routeFinder(Tile origin, Tile destination, Map map) {
        map.resetNumbers();
        boolean[][] tilePassability = map.getTilesPassability();

        if (!destination.isPassable())
            return routeFinder2(origin, destination, map);

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

        return getFinalRoute(origin, destination, targets);
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
        if (!tilePassability[destination.getX()][destination.getY()])
            return null;

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

        return getFinalRoute(origin, destination, targets);
    }

    private static LinkedList<Tile> getFinalRoute(Tile origin, Tile destination, ArrayList<ArrayList<Tile>> targets) {
        LinkedList<Tile> result = new LinkedList<>();
        result.add(0, origin);
        result.add(1, destination);

        Tile preTile;
        Tile tile = destination;
        int index = targets.size() -2;
        while (index > 0) {
            preTile = getNeighbor(tile, targets.get(index));
            result.add(1, preTile);
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
