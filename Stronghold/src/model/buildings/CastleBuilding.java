package model.buildings;

import model.Government;
import model.enums.Resources;

public class CastleBuilding extends Building{

    public CastleBuilding(Government government, int maxHitPoints, Resources formingMaterial, int formingMaterialAmount) {
        super(government, maxHitPoints, formingMaterial, formingMaterialAmount);
    }
}
