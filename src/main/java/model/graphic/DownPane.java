package model.graphic;

import controller.CaptchaController;
import controller.MapController;
import controller.MultiMenuFunctions;
import controller.StripPaneController;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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

        stripPane.setBackground(Background.fill(Color.BLUE));
        this.setBackground(Background.fill(Color.web("#795C32")));

        this.getChildren().add(detailPane);
        this.getChildren().add(stripPane);
        addButtons();
        gamePane.getChildren().add(this);
    }

    public void addBuildingIcons() {

    }

    public void addButtons() throws URISyntaxException {
        File file = new File(Objects.requireNonNull(CaptchaController.class.getResource("/Image/Buildings")).toURI());
        ArrayList<File> buildings = MultiMenuFunctions.getAllImageFilesFromFolder(file);

        createRec(210, null, "CastleBuildings");
        createRec(270, null, "TownBuildings");
        createRec(330, null, "FarmBuildings");
        createRec(390, null, "IndustryBuildings");
        createRec(450, null, "WeaponBuildings");
        createRec(510, null, "FoodProcessingBuildings");
    }

    public void createRec(double x, String absoluteAddress, String buttonName) {
        Rectangle rectangle = new Rectangle(40, 40);
        rectangle.setId(buttonName);
        rectangle.setLayoutX(x);
        rectangle.setLayoutY(10);
        rectangle.setFill(Color.WHITE);//Todo : image
        this.getChildren().add(rectangle);
        rectangle.setOnMouseClicked(mouseEvent -> this.stripPaneController.insertImages(rectangle.getId()));
    }
}