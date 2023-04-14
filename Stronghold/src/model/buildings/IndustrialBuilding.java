package model.buildings;

import model.Government;
import model.buildings.enums.IndustrialBuildings;

public class IndustrialBuilding extends Building {

    private int productionRate;

    public IndustrialBuilding(Government government, IndustrialBuildings type) {
        super(government, type.getMaxHitPoints(), type.getFormingMaterial(), type.getFormingMaterialAmount());
    }
}
