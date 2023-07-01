package model.buildings;

public enum BuildingCategory {

    CASTLE_BUILDINGS("castle"),
    TOWERS("tower"),
    MILITARY_BUILDINGS("military"),
    GATEHOUSES("gatehouse"),
    INDUSTRY_BUILDINGS("industry"),
    TOWN_BUILDINGS("town"),
    FARM_BUILDINGS("farm"),
    WEAPON_BUILDINGS("weapon"),
    FOOD_PROCESSING_BUILDINGS("food");

    private final String name;

    BuildingCategory(String name){
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
