package model.buildings;

import model.Government;
import model.buildings.enums.FarmBuildings;

public class FarmBuilding extends Building{

    private int productionRate;

    public FarmBuilding(Government government, FarmBuildings type) {
        super(government, type.getMaxHitPoints(), type.getFormingMaterial(),type.getFormingMaterialAmount());
    }

}
