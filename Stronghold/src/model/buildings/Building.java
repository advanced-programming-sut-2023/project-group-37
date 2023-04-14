package model.buildings;

import model.Government;
import model.enums.Resources;

public abstract class Building {
    private final int maxHitPoints;
    private int hitPoints;
    private final Resources formingMaterial;
    private final int formingMaterialAmount;
    private final Government government;

    public Building(Government government, int maxHitPoints, Resources formingMaterial, int formingMaterialAmount) {
        this.maxHitPoints = maxHitPoints;
        this.hitPoints = this.maxHitPoints;
        this.formingMaterial = formingMaterial;
        this.formingMaterialAmount = formingMaterialAmount;
        this.government = government;
    }

    public void repair() {
        // TODO: check if works fine.
    }
}