package model.buildings;

import model.Government;
import model.buildings.enums.Buildings;

public class IndustrialBuilding extends Building {

    private int productionRate;

    public IndustrialBuilding(Government government, Buildings type) {
        super(government, type.getMaxHitPoints(), type.getFormingMaterial(), type.getFormingMaterialAmount());
    }
}
