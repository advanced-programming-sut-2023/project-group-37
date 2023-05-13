package model.game;

import java.util.ArrayList;
import java.util.HashMap;

public class Map {

    // TODO: handle load maps!
    private static final ArrayList<Map> maps;
    private final String name;
    private final int size;
    private final Tile[][] field;
    private final boolean[][] tilesPassability;
    private HashMap<Integer, Territory> territories;

    // TODO: should be final?

    static {
        maps = new ArrayList<>();
    }

    public Map(String name) {
        this.name = name;
        this.size = 200;
        this.field = new Tile[size][size];
        this.tilesPassability = new boolean[size][size];
        this.territories = new HashMap<>();
        this.initializeTiles();
    }

    public Map(String name, int size, Tile[][] map, boolean[][] tilesPassability,
               HashMap<Integer, Territory> territories) {
        this.name = name;
        this.size = size;
        this.field = map;
        this.tilesPassability = tilesPassability;
        this.territories = territories;
        // TODO: assign territories
    }

    public static Map getMapByName(String name) {
        for (Map map : maps)
            if (map.getName().equals(name))
                return map;
        return null;
    }

    public static ArrayList<Map> getMaps() {
        return maps;
    }

    public static Map getMapCopyByName(String name) {
        for (Map map : maps)
            if (map.getName().equals(name))
                return new Map(name, map.size, map.getField(), map.getTilesPassability(), map.getTerritories());
        return null;
    }

    public static void loadMaps() {
        maps.add(GenerateMap.createMap1());
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }

    public Tile[][] getField() {
        return this.field;
    }

    public boolean[][] getTilesPassability() {
        this.setTilesPassability();
        return this.tilesPassability;
    }

    public HashMap<Integer, Territory> getTerritories() {
        return this.territories;
    }

    public void setTilesState() {
        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++)
                this.field[i][j].setState();
    }

    public void setTilesPassability() {
        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++)
                tilesPassability[i][j] = this.field[i][j].isPassable();
    }

    public void resetNumbers() {
        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++)
                this.field[i][j].number = 0;
    }

    public Tile getTileByLocation(int x, int y) {
        if (x >= this.size || y >= this.size || x < 0 || y < 0)
            return null;

        return this.field[x][y];
    }

    public boolean getPassabilityByLocation(int x, int y) {
        return this.field[x][y].isPassable();
    }

    private void initializeTiles() {
        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++)
                this.field[i][j] = new Tile(i, j);
    }

    public Territory getCopyKeepByNumber(Government owner, int territoryNumber) {
        return this.territories.get(territoryNumber).getCopyTerritory(owner);
    }

    public HashMap<Integer, Territory> getKeeps() {
        return this.territories;
    }
}
