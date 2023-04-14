package model.buildings;

import model.Government;

public class Stockpile{

    private final int capacity;

    private int wheat;
    private int flour;
    private int hops;
    private int ale;
    private int stone;
    private int iron;
    private int wood;
    private int pitch;

    public Stockpile(Government government, int capacity, int wheat, int flour, int hops, int ale, int stone, int iron,
                     int wood, int pitch) {
        this.capacity = capacity;
        // TODO: handle
    }

    public boolean isFull (){
        // TODO: handle
        return false;
    }

    public void changeResourceStock(String resource, int amount){
        switch (resource) {
            case "wheat" -> this.wheat += amount;
            case "flour" -> this.flour += amount;
            case "hops" -> this.hops += amount;
            case "ale" -> this.ale += amount;
            case "stone" -> this.stone += amount;
            case "iron" -> this.iron += amount;
            case "wood" -> this.wood += amount;
            case "pitch" -> this.pitch += amount;
        }
    }
}
