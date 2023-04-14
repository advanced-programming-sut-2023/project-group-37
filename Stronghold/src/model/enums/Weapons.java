package model.enums;

public enum Weapons {
    ;

    private final Resources formingMaterial;

    Weapons(Resources formingMaterial) {
        this.formingMaterial = formingMaterial;
    }

    public Resources getFormingMaterial() {
        return this.formingMaterial;
    }

    public Weapons getWeaponByName(String name){
        //TODO: fill
        return null;
    }
}