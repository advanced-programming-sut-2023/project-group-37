package model.buildings;

import model.Government;
import model.buildings.enums.Buildings;

public class TownBuilding extends Building{

    public TownBuilding(Government government, Buildings type) {
        super(government, type.getMaxHitPoints(), type.getFormingMaterial(),type.getFormingMaterialAmount());
    }
}