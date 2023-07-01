package view.animation;

import javafx.animation.Transition;
import model.people.MilitaryUnit;

public class MoveAnimation extends Transition {
    private final MilitaryUnit militaryUnit;

    public MoveAnimation(MilitaryUnit militaryUnit) {
        this.militaryUnit = militaryUnit;
    }

    @Override
    protected void interpolate(double v) {

    }
}
