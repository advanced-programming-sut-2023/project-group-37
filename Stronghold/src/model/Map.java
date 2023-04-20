package model;

public class Map {
    private int x;
    private int y;
    private Tile[][] gameField;

    public Map(int x, int y) {
        this.x = x;
        this.y = y;
        gameField = new Tile[x][y];
    }

    public Tile[][] getGameField() {
        return gameField;
    }
    public Tile getTile(int x,int y){
        return gameField[x][y];
    }
}