package controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.buildings.BuildingCategory;
import model.buildings.BuildingType;
import model.buildings.DefensiveBuildingType;

import java.util.ArrayList;
import java.util.HashMap;

public class StripPaneController {
    private final int sizeOfImages;
    private final Pane stripPane;
    private final HashMap<BuildingType, ImageView> buildingTypeImages;
    private final HashMap<DefensiveBuildingType, ImageView> defensiveBuildingTypeImages;

    public StripPaneController(Pane stripPane) {
        this.sizeOfImages = 60;
        this.stripPane = stripPane;
        this.buildingTypeImages = new HashMap<>();
        this.defensiveBuildingTypeImages = new HashMap<>();

        for (BuildingType buildingType : BuildingType.values()) {
            ImageView imageView =  new ImageView(new Image(buildingType.getImage().getUrl(),
                    sizeOfImages, sizeOfImages, false, false));

//            imageView.setOnDragDropped();
            this.buildingTypeImages.put(buildingType, imageView);
        }

        for (DefensiveBuildingType defensiveBuildingType : DefensiveBuildingType.values()) {
            ImageView imageView =  new ImageView(new Image(
                    defensiveBuildingType.getImage().getUrl(), sizeOfImages, sizeOfImages, false, false));
//            imageView.setOnDragDropped();

            this.defensiveBuildingTypeImages.put(defensiveBuildingType,imageView);
        }
    }

    private ImageView getImageView(BuildingType buildingType) {
        return this.buildingTypeImages.get(buildingType);
    }

    private ImageView getImageView(DefensiveBuildingType defensiveBuildingType) {
        return this.defensiveBuildingTypeImages.get(defensiveBuildingType);
    }

    public void insertImages(BuildingCategory category) {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();

        int i = 0;
        for (BuildingType buildingType :BuildingType.values()) {
            if (buildingType.getCategory() == category) {
                ImageView imageView = this.getImageView(buildingType);
                if (imageView == null)
                    continue;

                imageView.setLayoutX(40 + i * (sizeOfImages + 10));
                imageView.setLayoutY(20);
                this.stripPane.getChildren().add(imageView);

                i++;
            }
        }

        for (DefensiveBuildingType defensiveBuildingType :DefensiveBuildingType.values()) {
            if (defensiveBuildingType.getCategory() == category) {
                ImageView imageView = this.getImageView(defensiveBuildingType);
                if (imageView == null)
                    continue;

                imageView.setLayoutX(40 + i * (sizeOfImages + 10));
                imageView.setLayoutY(20);
                this.stripPane.getChildren().add(imageView);

                i++;
            }
        }
    }
}