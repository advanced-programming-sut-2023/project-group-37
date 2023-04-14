package model.buildings.enums;

import model.enums.Resources;

public enum CastleBuildings {
    ;

    private final int maxHitPoints;
    private final Resources formingMaterial;
    private final int formingMaterialAmount;

    CastleBuildings(int maxHitPoints, Resources formingMaterial, int formingMaterialAmount) {
        this.maxHitPoints = maxHitPoints;
        this.formingMaterial = formingMaterial;
        this.formingMaterialAmount = formingMaterialAmount;
    }

    public int getMaxHitPoints() {
        return this.maxHitPoints;
    }

    public Resources getFormingMaterial() {
        return this.formingMaterial;
    }

    public int getFormingMaterialAmount() {
        return this.formingMaterialAmount;
    }
}

