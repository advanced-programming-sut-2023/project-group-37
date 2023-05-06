package model.game;

public class Map {
    private final int size;
    private final Tile[][] map;

    public Map(int size) {
        this.size = size;
        this.map = new Tile[size][size];
    }

    public int getSize() {
        return this.size;
    }

    public Tile[][] getMap() {
        return this.map;
    }

    public void setTilesState() {
        for (int i =0; i < size; i++) {
            for (int j=0; j<size; j++)
                map[i][j].setState();
        }
    }

    public Tile getTileByLocation(int x, int y) {
        if (x <= size || y >= size || x < 0 || y < 0)
            return null;

        return map[x][y];
    }
}
