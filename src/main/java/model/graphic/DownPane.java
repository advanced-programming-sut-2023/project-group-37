package model.graphic;

import controller.CaptchaController;
import controller.MapController;
import controller.MultiMenuFunctions;
import controller.StripPaneController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.buildings.BuildingCategory;
import model.game.Tile;
import view.menus.RegisterMenu;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;

public class DownPane extends Pane {
    private final MapController mapController;
    private final Pane stripPane;
    private final StripPaneController stripPaneController;

    public DownPane(MapController mapController) {
        this.mapController = mapController;
        this.stripPane = new Pane();
        this.stripPaneController = new StripPaneController(this.stripPane);
    }

    public void initialize(Pane gamePane) throws URISyntaxException {
        this.setPrefWidth(gamePane.getPrefWidth());
        this.setPrefHeight(162);
        this.setLayoutY(gamePane.getPrefHeight() - this.getPrefHeight());

        Pane detailPane = new Pane();
        detailPane.setPrefWidth(1138);
        detailPane.setPrefHeight(162);
        detailPane.setLayoutX(162);
        MultiMenuFunctions.setBackground(detailPane, "details.jpg");
        mapController.createDetailLabels(detailPane);

        stripPane.setPrefWidth(890);
        stripPane.setPrefHeight(101);

        stripPane.setLayoutX(196);
        stripPane.setLayoutY(61);

        this.setBackground(Background.fill(Color.web("#795C32")));

        this.getChildren().add(detailPane);
        this.getChildren().add(stripPane);
        addButtons();
        gamePane.getChildren().add(this);
    }

    public void addBuildingIcons() {

    }

    public void addButtons() throws URISyntaxException {
        String address = Objects.requireNonNull(RegisterMenu.class.getResource("/Image/Button/")).toExternalForm();

        createRec(210, address + "castle.jpg", BuildingCategory.CASTLE_BUILDINGS);
        createRec(570, address + "tower.jpg", BuildingCategory.TOWERS);
        createRec(630, address + "military.jpg", BuildingCategory.MILITARY_BUILDINGS);
        createRec(690, address + "gatehouse.jpg", BuildingCategory.GATEHOUSES);
        createRec(270, address + "town.jpg", BuildingCategory.TOWN_BUILDINGS);
        createRec(330, address + "farm.jpg", BuildingCategory.FARM_BUILDINGS);
        createRec(390, address + "industry.jpg", BuildingCategory.INDUSTRY_BUILDINGS);
        createRec(450, address + "weapon.jpg", BuildingCategory.WEAPON_BUILDINGS);
        createRec(510, address + "food-processing.jpg", BuildingCategory.FOOD_PROCESSING_BUILDINGS);
    }

    public void createRec(double x, String absoluteAddress, BuildingCategory category) {
        Rectangle rectangle = new Rectangle(40, 40);
        rectangle.setLayoutX(x);
        rectangle.setLayoutY(15);
        rectangle.setFill(new ImagePattern(new Image(absoluteAddress)));
        this.getChildren().add(rectangle);
        rectangle.setOnMouseClicked(mouseEvent -> this.stripPaneController.insertImages(category));
    }

    public void setForTiles(ArrayList<Tile> selectedTiles) {
        this.stripPaneController.insertSelectedTiles(selectedTiles);
    }

    public void setForTradeMenu() {
        this.stripPaneController.setForTradeMenu();
    }

    public void setForShopMenu() {
        this.stripPaneController.runShopMenu();
    }

    public void attack(Tile tile) {
        this.stripPaneController.attack(tile);
    }

    public void move(Tile tile) {
        this.stripPaneController.move(tile);
    }
}