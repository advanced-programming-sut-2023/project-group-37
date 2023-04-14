package model.buildings;

import model.Government;
import model.buildings.enums.Buildings;

public class FoodProcessingBuilding extends Building {

    private int productionRate;

    public FoodProcessingBuilding(Government government, Buildings type) {
        super(government, type.getMaxHitPoints(), type.getFormingMaterial(),type.getFormingMaterialAmount());
    }
}
