package view.animation;

import controller.MultiMenuFunctions;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class FaceAnimation extends Transition {

    private static final ArrayList<Image> faceStates;
    private final ImageView targetImageView;
    private int currentState;
    private final int targetState;

    static {
        faceStates = new ArrayList<>();
        for (File file : MultiMenuFunctions.getAllImageFilesFromFolder(new File(Objects.requireNonNull
                (FaceAnimation.class.getResource("/Image/Face Animation/")).toExternalForm())))
            faceStates.add(new Image(file.getPath()));
    }

    public FaceAnimation(ImageView target, int popularityValue1, int popularityValue2) {
        this.targetImageView = target;
        this.currentState = getStateByPopularityValue(popularityValue1);
        this.targetState = getStateByPopularityValue(popularityValue2);
//        this.setCycleCount(10);
        this.setCycleCount(Math.abs(targetState-currentState));
        this.setCycleDuration(Duration.seconds(0.5));
    }

    private int getStateByPopularityValue(int value) {
        return value > 80 ? 10 : value > 40 ? 5 : 0;
    }

    @Override
    protected void interpolate(double v) {
        if (targetState > currentState)
            this.currentState++;
        else
            this.currentState--;
        targetImageView.setImage(faceStates.get(this.currentState));
//        if (this.currentState == this.targetState)
//            stop();
    }
}
