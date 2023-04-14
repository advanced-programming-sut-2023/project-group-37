package model.buildings;

import model.Government;
import model.buildings.enums.WeaponBuildings;
import model.enums.Weapons;

public class WeaponBuilding extends Building{

    private final Weapons weapon;

    public WeaponBuilding(Government government, WeaponBuildings type) {
        super(government, type.getMaxHitPoints(), type.getFormingMaterial(),type.getFormingMaterialAmount());
        this.weapon = type.getWeapon();
    }

    public Weapons getWeapon() {
        return this.weapon;
    }
}
