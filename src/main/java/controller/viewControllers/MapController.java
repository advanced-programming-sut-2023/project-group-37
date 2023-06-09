package controller.viewControllers;

import controller.MultiMenuFunctions;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.game.Game;
import model.game.Government;
import model.game.Map;
import model.game.Tile;
import model.people.TroopType;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class MapController {
    private static final MapController MAP_CONTROLLER;
    private Game game;
    private Map map;
    private GridPane mainMap;
    private Pane downPane;
    private Pane detailPane;
    private GridPane miniMap;
    private Pane chooserRectangle;
    private Government currentGovernment;
    private int currentX;
    private int currentY;

    static {
        MAP_CONTROLLER = new MapController();
    }

    private MapController() {
        this.currentX = 0;
        this.currentY = 0;
    }

    public static MapController getInstance() {
        return MAP_CONTROLLER;
    }

    public void setCurrentGovernment(Government currentGovernment) {
        this.currentGovernment = currentGovernment;
    }

    public void setGame(Game game) {
        this.game = game;
        this.map = game.getMap();
    }

    public void setGamePane(Pane gamePane) {
        Map.loadMaps();
        this.map =  Map.getMaps().get(0); // todo : wtf ?
        Tile[][] tiles = this.map.getField();

        gamePane.setPrefHeight(740); // 37 tiles
        gamePane.setPrefWidth(1300); // 65 tiles

        this.createMainMap(gamePane, tiles);
        this.createDetailPane(gamePane);
        this.createChooserRectangle();
        this.creatMiniMap(tiles);
        // todo : remove this :
        MultiMenuFunctions.setTileImage(tiles[20][15], TroopType.ARCHER.getImage());
    }

    private void createMainMap(Pane gamePane, Tile[][] tiles) {
        this.mainMap = new GridPane();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                this.mainMap.add(tiles[i][j], i, j);
            }
        }

        this.mainMap.setLayoutX(0);
        this.mainMap.setLayoutY(0);

        gamePane.getChildren().add(this.mainMap);

        this.mainMap.setOnKeyPressed(keyEvent -> {
            String keyName = keyEvent.getCode().getName();

            switch (keyName) {
                case "Left" -> {
                    goLeft(); goLeft(); goLeft();
                }
                case "Right" -> {
                    goRight(); goRight(); goRight();
                }
                case "Up" -> {
                    goUp(); goUp(); goUp();
                }
                case "Down" -> {
                    goDown(); goDown(); goDown();
                }
            }
        });

        AtomicBoolean isNotDragged = new AtomicBoolean(true);

        final double[] startX = new double[1];
        final double[] startY = new double[1];
        this.mainMap.setOnMousePressed(mouseEvent -> {
            startX[0] = mouseEvent.getSceneX();
            startY[0] = mouseEvent.getSceneY();
        });

        this.mainMap.setOnMouseDragged(mouseEvent -> {
            double x = mouseEvent.getSceneX();
            double y = mouseEvent.getSceneY();

            ArrayList<Tile> rectangleTiles = this.map.getRectangleTilesByXY(startX[0], startY[0], x, y);

            Tile firstTile = this.map.getTileByXY(startX[0], startY[0]);
            Tile secondTile = this.map.getTileByXY(x, y);

            for (Tile rectangleTile : rectangleTiles) {
                if (rectangleTile.getLocationX() == Math.min(firstTile.getLocationX(), secondTile.getLocationX()))
                    rectangleTile.setLeftRectangleEffect();

                else if (rectangleTile.getLocationX() == Math.max(firstTile.getLocationX(), secondTile.getLocationX()))
                    rectangleTile.setRightRectangleEffect();

                if (rectangleTile.getLocationY() == Math.min(firstTile.getLocationY(), secondTile.getLocationY()))
                    rectangleTile.setUpRectangleEffect();

                else if (rectangleTile.getLocationY() == Math.max(firstTile.getLocationY(), secondTile.getLocationY()))
                    rectangleTile.setDownRectangleEffect();
            }

            isNotDragged.set(false);
        });

        this.mainMap.setOnMouseClicked(mouseEvent -> {
            if (isNotDragged.get()) {
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();

                Tile tile = this.map.getTileByXY(x, y);
                tile.setSelectedEffect();
            }
            isNotDragged.set(true);
        });
    }

    public void goTo(int x, int y) {
        if (x < 32 || y < 18 || x > this.map.getSize() - 34 || y > this.map.getSize() - 29)
            return;

        this.mainMap.setLayoutX((32 - x) * 20);
        this.mainMap.setLayoutY((18 - y) * 20);
    }

    public void createDetailPane(Pane gamePane) {
        this.downPane = new Pane();

        this.downPane.setPrefWidth(gamePane.getPrefWidth());
        this.downPane.setPrefHeight(162);
        this.downPane.setLayoutY(gamePane.getPrefHeight() - this.downPane.getPrefHeight());

        this.detailPane = new Pane();
        this.detailPane.setPrefWidth(1138);
        this.detailPane.setPrefHeight(162);
        this.detailPane.setLayoutX(162);
        MultiMenuFunctions.setBackground(this.detailPane, "details.png");

        this.downPane.setBackground(Background.fill(Color.web("#795C32")));

        this.downPane.getChildren().add(this.detailPane);
        gamePane.getChildren().add(this.downPane);
    }

    private void createChooserRectangle() {
        this.chooserRectangle = new Pane();
        this.chooserRectangle.setBackground(Background.EMPTY);
        this.chooserRectangle.setBorder(new Border(new BorderStroke(
                Color.DARKRED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        this.chooserRectangle.setPrefWidth(65 * 0.8);
        this.chooserRectangle.setPrefHeight(27 * 0.8);

        this.chooserRectangle.setLayoutX(1);
        this.chooserRectangle.setLayoutY(1);

        this.downPane.getChildren().add(this.chooserRectangle);
    }

    private void creatMiniMap(Tile[][] tiles) {
        this.miniMap = new GridPane();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                this.miniMap.add(tiles[i][j].getMiniTile(), i, j);
            }
        }

        this.miniMap.setBorder(new Border(new BorderStroke(
                Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        this.downPane.getChildren().add(this.miniMap);
    }

    public void goUp() {
        if (this.mainMap.getLayoutY() == 0)
            return;
        this.mainMap.setLayoutY(this.mainMap.getLayoutY() + 20);
        this.chooserRectangle.setLayoutY(this.chooserRectangle.getLayoutY() - 0.8);
    }

    public void goLeft() {
        if (this.mainMap.getLayoutX() == 0)
            return;
        this.mainMap.setLayoutX(this.mainMap.getLayoutX() + 20);
        this.chooserRectangle.setLayoutX(this.chooserRectangle.getLayoutX() - 0.8);
    }

    public void goDown() {
        if (this.mainMap.getLayoutY() == -172 * 20)
            return;
        this.mainMap.setLayoutY(this.mainMap.getLayoutY() - 20);
        this.chooserRectangle.setLayoutY(this.chooserRectangle.getLayoutY() + 0.8);
    }

    public void goRight() {
        if (this.mainMap.getLayoutX() == -134 * 20)
            return;
        this.mainMap.setLayoutX(this.mainMap.getLayoutX() - 20);
        this.chooserRectangle.setLayoutX(this.chooserRectangle.getLayoutX() + 0.8);
    }

    public GridPane getMainMap() {
        return mainMap;
    }

    public Pane getDetailPane() {
        return this.detailPane;
    }

}
