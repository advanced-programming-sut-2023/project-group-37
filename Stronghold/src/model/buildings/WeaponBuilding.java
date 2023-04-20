package model.buildings;

import model.Government;
import model.buildings.enums.Buildings;
import model.enums.Weapons;

public class WeaponBuilding extends Building{

    private final Weapons weapon;

    private int productionRate;

    public WeaponBuilding(Government government, Buildings type,Weapons weapon) {
        super(government, type.getMaxHitPoints(), type.getFormingMaterial(),type.getFormingMaterialAmount());
        this.weapon = weapon;
    }

    public Weapons getWeapon() {
        return this.weapon;
    }
}