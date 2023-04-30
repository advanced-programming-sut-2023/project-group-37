package model.people;

import model.game.Government;
import model.game.Item;

public class Troop extends MilitaryUnit {

    private final TroopType type;
    private final Item weapon;
    private final Item armor;
    private final boolean canClimbLadder;
    private final boolean canDigMoat;
    private boolean hasLadder;
    public Troop(Government loyalty, TroopType type) {
        super(loyalty, type.getMaxHitpoints(), type.getDamage(), type.getRange(), type.getSpeed());
        this.type = type;
        this.weapon = type.getWeapon();
        this.armor = type.getArmor();
        this.canClimbLadder = type.canClimbLadder();
        this.canDigMoat = type.canDigMoat();
        this.hasLadder = type == TroopType.LADDERMAN;
    }

    public TroopType getType() {
        return this.type;
    }

    public boolean canClimbLadder(){
        return this.canClimbLadder;
    }

    public boolean canDigMoat(){
        return this.canDigMoat;
    }

    // TODO: implement drop ladder!
}
