package model.graphic;

import controller.MapController;
import controller.MultiMenuFunctions;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class DownPane extends Pane{
    private MapController mapController;

    public DownPane(MapController mapController) {
        this.mapController = mapController;
    }

    public void initialize(Pane gamePane) {
        this.setPrefWidth(gamePane.getPrefWidth());
        this.setPrefHeight(162);
        this.setLayoutY(gamePane.getPrefHeight() - this.getPrefHeight());

        Pane detailPane = new Pane();
        detailPane.setPrefWidth(1138);
        detailPane.setPrefHeight(162);
        detailPane.setLayoutX(162);
        MultiMenuFunctions.setBackground(detailPane, "details.jpg");
        mapController.createDetailLabels(detailPane);

        Pane stripPane = new Pane();
        stripPane.setPrefWidth(890);
        stripPane.setPrefHeight(101);

        stripPane.setLayoutX(196);
        stripPane.setLayoutY(61);

        stripPane.setBackground(Background.fill(Color.BLUE));
        this.setBackground(Background.fill(Color.web("#795C32")));

        this.getChildren().add(detailPane);
        this.getChildren().add(stripPane);
        gamePane.getChildren().add(this);
    }
    public void addBuildingIcons(){
        this.getChildren().add(null);
    }

}
