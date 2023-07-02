package view.animation;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.game.Tile;
import model.people.Troop;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MoveAnimation extends Transition {
    private static AnchorPane mainMap;
    private final List<Tile> route;
    private final ArrayList<Image> images;
    private final ImageView imageView;
    private int index = 1;

    public MoveAnimation(Troop troop, LinkedList<Tile> mainRoute, List<Tile> route) {
        this.route = route;
        this.images = new ArrayList<>();
        for (File file : troop.getAnimationFiles()) {
            this.images.add(new Image(file.getAbsolutePath()));
        }

        this.imageView = new ImageView(this.images.get(0));
        this.imageView.setLayoutX(route.get(0).getLayoutX());
        this.imageView.setLayoutY(route.get(0).getLayoutY());
        mainMap.getChildren().add(imageView);
        this.setCycleCount(Math.min(route.size(), this.images.size()));
        this.setCycleDuration(Duration.millis(100));
//
//        this.setOnFinished(actionEvent -> {
//            troop.setLocation(route.get(route.size()-1));
//            route.get(route.size()- 1).addMilitaryUnit(troop);
//            mainRoute.subList(0, route.size()).clear();
//            mainMap.getChildren().remove(imageView);
//        });
    }

    public static void setMainMap(AnchorPane mainMap) {
        MoveAnimation.mainMap = mainMap;
    }

    @Override
    protected void interpolate(double v) {
        try {
            this.imageView.setImage(this.images.get(index));
            this.imageView.setLayoutX(this.route.get(index).getLayoutX());
            this.imageView.setLayoutY(this.route.get(index).getLayoutY());
            index++;
        }
        catch (Exception ignored) {
            System.out.println(index);
            this.stop();
        }
    }
}
