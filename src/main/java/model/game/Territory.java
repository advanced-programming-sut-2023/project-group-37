package model.game;

import java.util.ArrayList;

public class Territory {
    private final Map map;
    private Government owner;
    private final int territoryNumber;
    private final ArrayList<Tile> coveredTiles;
    private final Tile keep;
    private final Tile village;
    private final Tile firstStockpileLocation;

    public Territory(Map map, int territoryNumber, Tile keep) {
        this.map = map;
        this.territoryNumber = territoryNumber;
        this.coveredTiles = new ArrayList<>();
        this.keep = keep;
        this.village = this.map.getTileByLocation(keep.getX(), keep.getY() - 1);
        this.village.setPassability(false);
        this.firstStockpileLocation = this.map.getTileByLocation(keep.getX() + 1, keep.getY());
    }

    public Territory getCopyTerritory(Government owner) {
        Territory copy =new Territory(this.map, this.territoryNumber, this.keep);
        copy.setOwner(owner);
        return copy;
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

    public void addCoveredTile(Tile tile) {
        this.coveredTiles.add(tile);
    }

    public Tile getKeep() {
        return this.keep;
    }

    public Tile getVillage() {
        return this.village;
    }

    public int getTerritoryNumber() {
        return territoryNumber;
    }

    public Tile getFirstStockpileLocation() {
        return this.firstStockpileLocation;
    }

    public void setOwner(Government owner) {
        this.owner = owner;
    }

    public boolean containsTile(Tile tile) {
        return this.coveredTiles.contains(tile);
    }

}
