package model.enums;

public enum Weapons {
    ;

    private final Resources formingMaterial;
    private final int formingMaterialAmount;
    Weapons(Resources formingMaterial, int formingMaterialAmount) {
        this.formingMaterial = formingMaterial;
        this.formingMaterialAmount = formingMaterialAmount;
    }

    public Resources getFormingMaterial() {
        return this.formingMaterial;
    }

    public Weapons getWeaponByName(String name){
        //TODO: fill
        return null;
    }
}