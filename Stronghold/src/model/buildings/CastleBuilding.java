package model.buildings;

import model.Government;
import model.buildings.enums.CastleBuildings;

public class CastleBuilding extends Building{

    public CastleBuilding(Government government, CastleBuildings type) {
        super(government, type.getMaxHitPoints(), type.getFormingMaterial(),type.getFormingMaterialAmount());
    }
}
