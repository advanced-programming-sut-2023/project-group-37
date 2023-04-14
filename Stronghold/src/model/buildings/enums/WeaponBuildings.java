package model.buildings.enums;

import model.enums.Resources;
import model.enums.Weapons;

public enum WeaponBuildings {

    ;

    private final int maxHitPoints;
    private final Resources formingMaterial = Resources.WOOD;
    private final int formingMaterialAmount;
    private final Weapons weapon;

    WeaponBuildings(int maxHitPoints, int formingMaterialAmount, Weapons weapon) {
        this.maxHitPoints = maxHitPoints;
        this.formingMaterialAmount = formingMaterialAmount;
        this.weapon = weapon;
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

    public Weapons getWeapon() {
        return this.weapon;
    }
}
