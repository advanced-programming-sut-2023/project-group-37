package model.buildings;

import model.Government;
import model.buildings.enums.FoodProcessingBuildings;

public class FoodProcessingBuilding extends Building {

    private int productionRate;

    public FoodProcessingBuilding(Government government, FoodProcessingBuildings type) {
        super(government, type.getMaxHitPoints(), type.getFormingMaterial(),type.getFormingMaterialAmount());
    }
}
