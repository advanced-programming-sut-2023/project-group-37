package model.buildings.enums;

import model.enums.Foods;
import model.enums.Resources;

public enum FarmBuildings {
    ;

    private final int maxHitPoints;
    private final Resources formingMaterial;
    private final int formingMaterialAmount;
    private final Foods food;

    FarmBuildings(int maxHitPoints, Resources formingMaterial, int formingMaterialAmount, Foods food) {
        this.maxHitPoints = maxHitPoints;
        this.formingMaterial = formingMaterial;
        this.formingMaterialAmount = formingMaterialAmount;
        this.food = food;
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

    public Foods getFood() {
        return food;
    }
}

