package model.buildings;

import model.Government;
import model.buildings.enums.TownBuildings;

public class TownBuilding extends Building{

    public TownBuilding(Government government, TownBuildings type) {
        super(government, type.getMaxHitPoints(), type.getFormingMaterial(),type.getFormingMaterialAmount());
    }
}
