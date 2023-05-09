package model.game;

import java.util.ArrayList;
import java.util.HashMap;

public class Map {

    // TODO: handle load maps!
    private final static ArrayList<Map> maps;
    private final String name;
    private final int size;
    private final Tile[][] map;
    private final boolean[][] tilesPassability;
    private HashMap<Integer, Government> territories;
    private HashMap<Integer, Tile> headQuarters;

    static {
        maps = new ArrayList<>();
    }

    // TODO: handle headquarters illegible on page 29!

    public Map(String name, int size) {
        this.name = name;
        this.size = size;
        this.map = new Tile[size][size];
        this.tilesPassability = new boolean[size][size];
        this.initializeTiles();
    }

    public Map(String name, int size, Tile[][] map, boolean[][] tilesPassability,
               HashMap<Integer, Government> territories, HashMap<Integer, Tile> headQuarters) {
        this.name = name;
        this.size = size;
        this.map = map;
        this.tilesPassability = tilesPassability;
        this.territories = territories;
        this.headQuarters = headQuarters;
        this.initializeTiles();
    }

    public static Map getMapByName(String name) {
        for (Map map : maps)
            if (map.getName().equals(name))
                return map;
        return null;
    }

    public static Map getMapCopyByName(String name) {
        for (Map map : maps)
            if (map.getName().equals(name))
                return new Map(name, map.size, map.getMap(), map.getTilesPassability(),
                        map.getTerritories(), map.getHeadQuarters());
        return null;
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }

    public Tile[][] getMap() {
        return this.map;
    }

    public boolean[][] getTilesPassability() {
        return this.tilesPassability;
    }

    public HashMap<Integer, Government> getTerritories() {
        return this.territories;
    }

    public HashMap<Integer, Tile> getHeadQuarters() {
        return this.headQuarters;
    }

    public void setTilesState() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++)
                this.map[i][j].setState();
        }
    }

    public void resetNumbers() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++)
                this.map[i][j].number = 0;
        }
    }

    public Tile getTileByLocation(int x, int y) {
        if (x >= this.size || y >= this.size || x < 0 || y < 0)
            return null;

        return this.map[x][y];
    }

    public boolean getPassabilityByLocation(int x, int y) {
        return this.map[x][y].isPassable();
    }

    private void initializeTiles() {
        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++)
                this.map[i][j] = new Tile(i, j);
    }

    public Tile getTileBySafeLocation(int x, int y) {
        return this.map[x][y];
    }
}
