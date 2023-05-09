package model.game;

import java.util.HashMap;

public class Map {
    private final int size;
    private final Tile[][] map;
    private final boolean[][] tilesPassability;

    private HashMap<Integer, Tile> headQuarters;

    // TODO: handle headquarters illegible on page 29!

    public Map(int size) {
        this.size = size;
        this.map = new Tile[size][size];
        this.tilesPassability = new boolean[size][size];
        this.initializeTiles();
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
