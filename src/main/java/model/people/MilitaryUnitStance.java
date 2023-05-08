package model.people;

public enum MilitaryUnitStance {
    STANDING,
    DEFENSIVE,
    AGGRESSIVE
    ;
    public static MilitaryUnitStance getByState(String state) {
        try {
            return valueOf(state.toUpperCase());
        } catch (Exception ignored) {
            return null;
        }
    }
}
