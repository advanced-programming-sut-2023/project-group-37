package model.game;

public class Map {
    private final int size;
    private final Tile[][] map;

    // TODO: handle headquarters illegible on page 29!

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

    public void setAllTiles() {
        for (int i =0; i < size; i++) {
            for (int j=0; j<size; j++)
                map[i][j].setState();
        }
    }
}
