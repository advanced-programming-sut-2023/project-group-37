package model.people;

import javafx.scene.image.Image;
import model.game.Government;
import model.game.Tile;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class Troop extends MilitaryUnit {
    private final TroopType type;
    private File standingImageFile;
    private ArrayList<File> animationFiles;
    private final boolean canClimbLadder;
    private Government loyalty;
    private boolean hasLadder;

    public Troop(Government loyalty, TroopType type, Tile location) {
        super(loyalty, location, type.getMaxHitpoints(), type.getDamage(), type.getRange(), type.getSpeed());
        this.type = type;
        this.loyalty = loyalty;
        try {
            this.animationFiles = MultiMenuFunctions.getAllImageFilesFromFolder(new File(Objects.requireNonNull(
                    Troop.class.getResource("/Image/Troop/animation/" + type.getName() + "/" +
                            loyalty.getColor())).toURI()));
            this.standingImageFile = this.animationFiles.get(0);

        } catch (Exception ignored) {
        }
        this.canClimbLadder = type.canClimbLadder();
        this.hasLadder = type == TroopType.LADDERMAN;
    }

    public Image getStandingImage() {
        return new Image(standingImageFile.getAbsolutePath());
    }

    public ArrayList<File> getAnimationFiles() {
        return animationFiles;
    }

    public TroopType getType() {
        return this.type;
    }

    @Override
    public boolean canGoUp() {
        return canClimbLadder;
    }
}
