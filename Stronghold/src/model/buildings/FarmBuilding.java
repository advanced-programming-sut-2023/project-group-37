package model.buildings;

import model.Government;
import model.buildings.enums.FarmBuildings;
import model.enums.Foods;

public class FarmBuilding extends Building {

    public FarmBuilding(Government government, FarmBuildings type) {
        super(government, type.getMaxHitPoints(), type.getFormingMaterial(),type.getFormingMaterialAmount());
    }


}
