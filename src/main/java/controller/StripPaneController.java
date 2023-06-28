package controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.buildings.*;
import model.game.Tile;
import model.people.*;
import view.enums.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class StripPaneController {
    private final MapController mapController;
    private final int sizeOfImages;
    private final Pane stripPane;
    private final GameController gameController;
    private final HashMap<BuildingType, ImageView> buildingTypeImages;
    private final HashMap<DefensiveBuildingType, ImageView> defensiveBuildingTypeImages;
    private final HashMap<TroopType, ImageView> troopTypeImages;
    private final HashMap<MilitaryMachineType, ImageView> militaryMachineTypeImages;

    public StripPaneController(Pane stripPane) {
        this.sizeOfImages = 70;
        this.stripPane = stripPane;
        this.buildingTypeImages = new HashMap<>();
        this.defensiveBuildingTypeImages = new HashMap<>();
        this.troopTypeImages = new HashMap<>();
        this.militaryMachineTypeImages = new HashMap<>();
        this.mapController = MapController.getInstance();
        this.gameController = GameController.getInstance();

        for (BuildingType buildingType : BuildingType.values()) {
            ImageView imageView = new ImageView(new Image(buildingType.getImage().getUrl(),
                    sizeOfImages, sizeOfImages, false, false));
            imageView.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                }
            });
            imageView.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                }
            });

            imageView.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Tile tile = mapController.getTileByXY(mouseEvent.getSceneX(), mouseEvent.getSceneY());
                    if (tile == null) return;
                    System.out.println(mapController.getGame().getGameMenuController().dropBuilding(tile, buildingType.getName()));
                }
            });
            this.buildingTypeImages.put(buildingType, imageView);
        }

        for (DefensiveBuildingType defensiveBuildingType : DefensiveBuildingType.values()) {
            ImageView imageView = new ImageView(new Image(
                    defensiveBuildingType.getImage().getUrl(), sizeOfImages, sizeOfImages, false, false));
            imageView.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                }
            });
            imageView.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                }
            });

            imageView.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Tile tile = mapController.getTileByXY(mouseEvent.getSceneX(), mouseEvent.getSceneY());
                    if (tile == null) return;
                    mapController.getGame().getGameMenuController().dropBuilding(tile, defensiveBuildingType.getName());
                }
            });

            this.defensiveBuildingTypeImages.put(defensiveBuildingType, imageView);
        }

        for (TroopType troopType : TroopType.values()) {
            ImageView imageView = new ImageView(new Image(
                    troopType.getImage().getUrl(), sizeOfImages, sizeOfImages, false, false));

            this.troopTypeImages.put(troopType, imageView);
        }

//        for (MilitaryMachineType militaryMachineType : MilitaryMachineType.values()) {
//            ImageView imageView = new ImageView(new Image(
//                    militaryMachineType.getImage().getUrl(), sizeOfImages, sizeOfImages, false, false));
//
//            this.militaryMachineTypeImages.put(militaryMachineType, imageView);
//        } todo
    }

    private ImageView getImageView(BuildingType buildingType) {
        return this.buildingTypeImages.get(buildingType);
    }

    private ImageView getImageView(DefensiveBuildingType defensiveBuildingType) {
        return this.defensiveBuildingTypeImages.get(defensiveBuildingType);
    }

    private ImageView getImageView(MilitaryUnit militaryUnit) {
        if (militaryUnit instanceof Troop troop)
            return this.troopTypeImages.get(troop.getType());

        else if (militaryUnit instanceof MilitaryMachine militaryMachine)
            return this.militaryMachineTypeImages.get(militaryMachine.getType());

        return null;
    }

    public void insertImages(BuildingCategory category) {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();

        int i = 0;
        for (BuildingType buildingType : BuildingType.values()) {
            if (buildingType.getCategory() == category) {
                ImageView imageView = this.getImageView(buildingType);
                if (imageView == null)
                    continue;

                imageView.setLayoutX(100 + i * (sizeOfImages + 50));
                imageView.setLayoutY(15);
                this.stripPane.getChildren().add(imageView);

                i++;
            }
        }

        for (DefensiveBuildingType defensiveBuildingType : DefensiveBuildingType.values()) {
            if (defensiveBuildingType.getCategory() == category) {
                ImageView imageView = this.getImageView(defensiveBuildingType);
                if (imageView == null)
                    continue;

                imageView.setLayoutX(100 + i * (sizeOfImages + 50));
                imageView.setLayoutY(20);
                this.stripPane.getChildren().add(imageView);

                i++;
            }
        }
    }

    private void uploadTroopImages(BuildingType buildingType) {

    }

    private void uploadBuildingMenu(Building building) {
        Label healthLabel = new Label("Health");
        ProgressBar healthBar = new ProgressBar(1);
        double health = building.getHitpoints() / (double) building.getMaxHitpoints();
        healthBar.setProgress(health);
        if (health < 0.33)
            healthBar.setStyle("-fx-progress-color: red");
        else if (health < 0.66)
            healthBar.setStyle("-fx-progress-color: yellow");
        else healthBar.setStyle("-fx-progress-color: green");

        healthLabel.setLayoutY(10);
        healthLabel.setLayoutX(10);
        healthBar.setLayoutY(10);
        healthBar.setLayoutX(30);

        this.stripPane.getChildren().add(healthLabel);
        this.stripPane.getChildren().add(healthBar);

        if (building instanceof DefensiveBuilding defensiveBuilding) {
            Button repairButton = new Button();
            repairButton.setGraphic(new ImageView()); // todo
            repairButton.setOnMouseClicked((MouseEvent mouseEvent) -> {
                Message message = this.gameController.repair(defensiveBuilding);
                if (message == Message.REPAIR_SUCCESS) {
                    // todo : image
                    repairButton.setDisable(true);
                }
            });
        }

        switch (building.getType()) {
            case BARRACKS -> uploadTroopImages(BuildingType.BARRACKS);
            case MERCENARY_POST -> uploadTroopImages(BuildingType.MERCENARY_POST);
            case ENGINEER_GUILD -> uploadTroopImages(BuildingType.ENGINEER_GUILD);
            case TUNNELER_GUILD -> uploadTroopImages(BuildingType.TUNNELER_GUILD);
            case MARKET -> this.runShopMenu();
        }
    }

    private void runShopMenu() {

    }

    public void insertSelectedTiles(ArrayList<Tile> selectedTiles) {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();

        boolean hasMilitaryUnit = false;
        for (Tile tile : selectedTiles) {
            if (tile.getMilitaryUnits().size() > 0) {
                hasMilitaryUnit = true;
                break;
            }
        }

        if (!hasMilitaryUnit) {
            Building building = null;
            for (Tile tile : selectedTiles) {
                if (tile.hasBuilding()) {
                    building = tile.getBuilding();
                    break;
                }
            }
            if (building != null)
                this.uploadBuildingMenu(building);
        } else {
            ArrayList<MilitaryUnit> militaryUnits = new ArrayList<>();
            for (Tile tile : selectedTiles)
                militaryUnits.addAll(tile.getMilitaryUnits());

            HashMap<MilitaryUnit, Integer> militaryUnitCounts = new HashMap<>();
            for (MilitaryUnit militaryUnit : militaryUnits)
                militaryUnitCounts.put(militaryUnit, militaryUnitCounts.getOrDefault(militaryUnit, 0) + 1);

            int i = 0;
            for (MilitaryUnit militaryUnit : militaryUnitCounts.keySet()) {
                ImageView imageView = this.getImageView(militaryUnit);
                if (imageView == null)
                    continue;

                imageView.setLayoutX(70 + i * (sizeOfImages + 50));
                imageView.setLayoutY(20);

                Label label = new Label(String.valueOf(militaryUnitCounts.get(militaryUnit)));
                label.setLayoutY(30 + sizeOfImages);
                label.setLayoutX(70 + i * (sizeOfImages + 50) - sizeOfImages/2f);

                this.stripPane.getChildren().add(label);
                this.stripPane.getChildren().add(imageView);

                i++;
            }
        }
    }

    public void setForTradeMenu() {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();


    }
}