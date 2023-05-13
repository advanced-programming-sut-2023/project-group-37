package model.people;

import model.game.Government;
import model.game.Item;
import model.game.Tile;

import java.util.LinkedList;

public class Troop extends MilitaryUnit {

    private final TroopType type;
    private final boolean canClimbLadder;
    private final boolean canDigMoat;
    private boolean hasLadder;

    public Troop(Government loyalty, TroopType type, Tile location) {
        super(loyalty, location, type.getMaxHitpoints(), type.getDamage(), type.getRange(), type.getSpeed());
        this.type = type;
        this.canClimbLadder = type.canClimbLadder();
        this.canDigMoat = type.canDigMoat();
        this.hasLadder = type == TroopType.LADDERMAN;
    }

    public TroopType getType() {
        return this.type;
    }

    public boolean canDigMoat() {
        return this.canDigMoat;
    }

    // TODO: implement drop ladder!

    @Override
    public boolean canGoUp() {
        return canClimbLadder;
    }
}
