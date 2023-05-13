package model.game;

import java.util.ArrayList;

public class Territory {
    private final Map map;
    private final Government owner;
    private final ArrayList<Tile> coveredTiles;
    private final Tile keep;
    private final Tile village;
    private final Tile firstStockpileLocation;

    private Territory(Map map, Government owner, ArrayList<Tile> coveredTiles, Tile keep) {
        this.map = map;
        this.owner = owner;
        this.coveredTiles = coveredTiles;
        this.keep = keep;
        this.village = this.map.getTileByLocation(keep.getX(), keep.getY() - 1);
        this.firstStockpileLocation = this.map.getTileByLocation(keep.getX() + 1, keep.getY());
    }

    public Map getMap() {
        return this.map;
    }

    public Government getOwner() {
        return this.owner;
    }

    public ArrayList<Tile> getCoveredTiles() {
        return this.coveredTiles;
    }

    public Tile getKeep() {
        return this.keep;
    }

    public Tile getVillage() {
        return this.village;
    }

    public Tile getFirstStockpileLocation() {
        return this.firstStockpileLocation;
    }

    public boolean containsTile(Tile tile) {
        return this.coveredTiles.contains(tile);
    }
}
