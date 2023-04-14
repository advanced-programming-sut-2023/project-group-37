package model.buildings;

import model.Government;
import model.enums.Resources;

public abstract class Building {
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

    public void repair() {
        // TODO: check if works fine.
        this.government.resources.put(this.formingMaterial, this.government.resources.get(formingMaterial)
                - (int) Math.ceil((double) (maxHitPoints - hitPoints) / maxHitPoints) * formingMaterialAmount);
        this.hitPoints = this.maxHitPoints;
    }
}
