package model.buildings;

import model.Government;
import model.buildings.enums.Buildings;

public class Granary extends Building{

    private final int capacity;

    private int meat;
    private int apples;
    private int cheese;
    private int bread;

    public Granary(Government government, int capacity, Buildings type) {
        super(government, type.getMaxHitPoints(), type.getFormingMaterial(), type.getFormingMaterialAmount());
        this.capacity = capacity;
        // TODO: handle
    }

    public boolean isFull (){
        // TODO: handle
        return false;
    }

    public void changeResourceStock(String resource, int amount){

    }
}