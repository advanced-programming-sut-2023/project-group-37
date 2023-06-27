package model.graphic;

import controller.CaptchaController;
import controller.MapController;
import controller.MultiMenuFunctions;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;

public class DownPane extends Pane {
    private final MapController mapController;
    private final Pane stripPane;

    public DownPane(MapController mapController) {
        this.mapController = mapController;
        stripPane = new Pane();
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
        File file = new File(Objects.requireNonNull(CaptchaController.class.getResource("/Image/MenuBuildings")).toURI());
        ArrayList<File> buildings = MultiMenuFunctions.getAllImageFilesFromFolder(file);

        Rectangle CastleBuildings = createRec(210, null, "Castle");
        Rectangle TownBuildings = createRec(270, null, "Town");
        Rectangle FarmBuildings = createRec(330, null, "Farm");
        Rectangle IndustryBuildings = createRec(390, null, "Industry");
        Rectangle WeaponsBuildings = createRec(450, null, "Weapon");
        Rectangle FoodBuildings = createRec(510, null, "Food");

    }

    public Rectangle createRec(double x, String absoluteAddress, String buttonName) {
        Rectangle r = new Rectangle(40, 40);
        r.setId(buttonName);
        r.setLayoutX(x);
        r.setLayoutY(10);
        r.setFill(Color.WHITE);//Todo : image
        this.getChildren().add(r);
        setAction(r);
        return r;
    }

    public void setAction(Rectangle r) {
        r.setOnMouseClicked(mouseEvent -> {
            updateStripPane(stripPane, r.getId());
        });
    }

    public void updateStripPane(Pane stripPane, String buttonName) {
        //TODO
    }

}