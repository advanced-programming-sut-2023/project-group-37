package model.buildings;

import model.Government;
import model.buildings.enums.Buildings;

public class FarmBuilding extends Building{

    private int productionRate;

    public FarmBuilding(Government government, Buildings type) {
        super(government, type.getMaxHitPoints(), type.getFormingMaterial(),type.getFormingMaterialAmount());
    }

}
