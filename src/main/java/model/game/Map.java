package model.game;

public class Map {
    private final int size;
    private final Tile[][] map;
    private final boolean[][] tilesPassability;

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
        return tilesPassability;
    }

    public void setTilesState() {
        for (int i =0; i < size; i++) {
            for (int j=0; j<size; j++)
                map[i][j].setState();
        }
    }

    public void resetNumbers() {
        for (int i =0; i < size; i++) {
            for (int j=0; j<size; j++)
                map[i][j].number = 0;
        }
    }

    public Tile getTileByLocation(int x, int y) {
        if (x >= size || y >= size || x < 0 || y < 0)
            return null;

        return map[x][y];
    }
    public boolean getPassabilitybyLocation(int x,int y){
        return this.map[x][y].isPassable();
    }
    private void initializeTiles(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.map[i][j] = new Tile(i,j);
            }
        }
    }

    public Tile getTileBySafeLocation(int x, int y) {
        return map[x][y];
    }
}
