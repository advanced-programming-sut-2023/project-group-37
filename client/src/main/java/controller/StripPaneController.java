package controller;

import controller.stripControllers.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.buildings.*;
import model.game.Tile;
import model.people.MilitaryUnit;
import view.enums.Message;
import connection.PopUp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class StripPaneController {
    private final MapController mapController;
    private final int sizeOfImages;
    private final Pane stripPane;
    private final ShopMenuController shopMenuController;
    private final UnitMenuController unitMenuController;
    private final BuildingMenuController buildingMenuController;
    private final TradeMenuController tradeMenuController;
    private final PopularityMenuController popularityMenuController;
    private final GameController gameController;
    private final HashMap<BuildingType, ImageView> buildingTypeImages;
    private final HashMap<DefensiveBuildingType, ImageView> defensiveBuildingTypeImages;
    private ArrayList<Tile> selectedTiles;
    private Rectangle towerRec;
    private Rectangle gateHouseRec;
    private Rectangle militaryRec;

    public StripPaneController(Pane stripPane) {
        this.sizeOfImages = 70;
        this.stripPane = stripPane;
        this.buildingTypeImages = new HashMap<>();
        this.defensiveBuildingTypeImages = new HashMap<>();

        this.mapController = MapController.getInstance();
        this.gameController = GameController.getInstance();
        this.unitMenuController = new UnitMenuController(stripPane);
        this.buildingMenuController = new BuildingMenuController(stripPane, this);
        this.tradeMenuController = new TradeMenuController(stripPane);
        this.popularityMenuController = new PopularityMenuController(stripPane);
        this.shopMenuController = new ShopMenuController(stripPane, tradeMenuController);

        for (BuildingType buildingType : BuildingType.values()) {
            ImageView imageView = MultiMenuFunctions.getImageView(buildingType.getImage(), this.sizeOfImages);
            imageView.setOnMousePressed(mouseEvent -> {

            });
            imageView.setOnMouseDragged(mouseEvent -> {
            });

            imageView.setOnMouseReleased(mouseEvent -> {
                Tile tile = mapController.getTileByXY(mouseEvent.getSceneX(), mouseEvent.getSceneY());
                if (tile == null) return;
                String message = mapController.getGame().getGameMenuController().dropBuilding(tile, buildingType.getName());
                if (!Objects.equals(message, Message.DROP_BUILDING_SUCCESS.toString()))
                    new Alert(Alert.AlertType.INFORMATION, message).show();
            });
            this.buildingTypeImages.put(buildingType, imageView);
        }

        for (DefensiveBuildingType defensiveBuildingType : DefensiveBuildingType.values()) {
            ImageView imageView = MultiMenuFunctions.getImageView(defensiveBuildingType.getImage(), this.sizeOfImages);
            imageView.setOnMousePressed(mouseEvent -> {

            });
            imageView.setOnMouseDragged(mouseEvent -> {
            });

            imageView.setOnMouseReleased(mouseEvent -> {
                Tile tile = mapController.getTileByXY(mouseEvent.getSceneX(), mouseEvent.getSceneY());
                if (tile == null) return;
                String message = mapController.getGame().getGameMenuController().dropBuilding(tile, defensiveBuildingType.getName());
                if (!Objects.equals(message, Message.DROP_BUILDING_SUCCESS.toString()))
                    new Alert(Alert.AlertType.INFORMATION, message).show();
            });

            this.defensiveBuildingTypeImages.put(defensiveBuildingType, imageView);
        }
    }

    private ImageView getImageView(BuildingType buildingType) {
        return this.buildingTypeImages.get(buildingType);
    }

    private ImageView getImageView(DefensiveBuildingType defensiveBuildingType) {
        return this.defensiveBuildingTypeImages.get(defensiveBuildingType);
    }

    public void getRectangles(Rectangle towerRec, Rectangle militaryRec, Rectangle gateHouseRec) {
        towerRec.setLayoutX(760);
        towerRec.setLayoutY(10);
        militaryRec.setLayoutX(800);
        militaryRec.setLayoutY(10);
        gateHouseRec.setLayoutX(800);
        gateHouseRec.setLayoutY(55);

        this.towerRec = towerRec;
        this.militaryRec = militaryRec;
        this.gateHouseRec = gateHouseRec;
    }

    public void insertImages(BuildingCategory category) {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();

        if (category == BuildingCategory.CASTLE_BUILDINGS) {
            this.stripPane.getChildren().add(this.towerRec);
            this.stripPane.getChildren().add(this.gateHouseRec);
            this.stripPane.getChildren().add(this.militaryRec);
        }

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

    public void runShopMenu() {
        shopMenuController.run();
    }

    public void insertSelectedTiles(ArrayList<Tile> selectedTiles) {
        this.selectedTiles = selectedTiles;
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();

        if (selectedTiles.size() == 0)
            return;

        boolean hasMilitaryUnit = false;
        for (Tile tile : selectedTiles) {
            if (tile.getMilitaryUnits().size() > 0) {
                for (MilitaryUnit militaryUnit : tile.getMilitaryUnits()) {
                    if (militaryUnit.getLoyalty() == gameController.getCurrentGame().getCurrentTurnGovernment()) {
                        hasMilitaryUnit = true;
                        break;
                    }
                }
            }
        }

        Building building = null;
        for (Tile tile : selectedTiles) {
            if (tile.hasBuilding()) {
                if (tile.getBuilding().getLoyalty() == gameController.getCurrentGame().getCurrentTurnGovernment()) {
                    building = tile.getBuilding();
                    break;
                }
            }
        }

        if (hasMilitaryUnit) {
            this.unitMenuController.run(selectedTiles);
            if (building != null) {
                ImageView imageView;
                if (building instanceof DefensiveBuilding defensiveBuilding)
                    imageView = MultiMenuFunctions.getImageView(defensiveBuilding.getDefensiveType().getImage(), this.sizeOfImages);

                else imageView = MultiMenuFunctions.getImageView(building.getType().getImage(), this.sizeOfImages);

                Building finalBuilding = building;
                imageView.setOnMouseClicked((MouseEvent mouseEvent) -> {
                    this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();
                    this.buildingMenuController.run(finalBuilding);
                });

                imageView.setLayoutY(20);
                imageView.setLayoutX(750);
                this.stripPane.getChildren().add(imageView);
            }
        } else if (building != null)
            this.buildingMenuController.run(building);
    }

    public void setForTradeMenu() {
        tradeMenuController.run();
    }

    public void deselect() {
        this.unitMenuController.deselect();
    }

    public boolean attack(Tile tile) {
        PopUp popUp = this.unitMenuController.attack(tile);
        popUp.show();

        return popUp == PopUp.WILL_ATTACK;
    }

    public void move(Tile tile) {
        this.unitMenuController.move(tile).show();
    }

    public void runPopularityMenu() {
        this.popularityMenuController.run();
    }

    public void paste() {
        if (this.selectedTiles == null || this.selectedTiles.size() == 0 || BuildingMenuController.getCopyBuilding() == null)
            return;

        for (Tile tile : selectedTiles) {
            String message = this.gameController.dropBuilding(tile, BuildingMenuController.getCopyBuilding());
            if (!Objects.equals(message, Message.DROP_BUILDING_SUCCESS.toString())) {
                new Alert(Alert.AlertType.INFORMATION, message).show();
                break;
            }
        }
    }
}