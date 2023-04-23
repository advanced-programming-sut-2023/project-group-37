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
}
