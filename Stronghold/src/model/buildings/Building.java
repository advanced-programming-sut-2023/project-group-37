package model.buildings;

import model.Government;
import model.enums.Resources;

public abstract class Building {
    private static Building currentBuilding;
    private final int maxHitPoints;
    private int hitPoints;
    private final Resources formingMaterial;
    private final int formingMaterialAmount;
    private final Government government;

    public Building(Government government, int maxHp, Resources formingMaterial, int formingMaterialAmount) {
        this.maxHitPoints = maxHp;
        this.hitPoints = this.maxHitPoints;
        this.formingMaterial = formingMaterial;
        this.formingMaterialAmount = formingMaterialAmount;
        this.government = government;
    }

    public static Building getCurrentBuilding() {
        return currentBuilding;
    }

    public static void setCurrentBuilding(Building currentBuilding) {
        Building.currentBuilding = currentBuilding;
    }

    public void repair() {
        // TODO: check if works fine.
        this.government.getResources().put(this.formingMaterial, this.government.getResources().get(formingMaterial)
                - (int) Math.ceil((double) (maxHitPoints - hitPoints) / maxHitPoints) * formingMaterialAmount);
        this.hitPoints = this.maxHitPoints;
    }
}