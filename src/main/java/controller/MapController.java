package controller;

import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.game.Game;
import model.game.Government;
import model.game.Map;
import model.game.Tile;
import model.graphic.CursorType;
import model.graphic.DownPane;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class MapController {
    private static final MapController MAP_CONTROLLER;
    private Game game;
    private Map map;
    private AnchorPane mainMap;
    private DownPane downPane;
    private Pane chooserRectangle;
    private Label goldLabel;
    private Label popularityLabel;
    private Government currentGovernment;

    private ArrayList<Tile> selectedTiles;
    private int currentX;
    private int currentY;
    private int cursorRight;
    private int cursorDown;
    private boolean isSelectedForMoveOrAttack;

    static {
        MAP_CONTROLLER = new MapController();
    }

    private MapController() {
        this.currentX = 0;
        this.currentY = 0;
        this.cursorDown = 0;
        this.cursorRight = 0;
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

    public void setGamePane(Pane gamePane) throws URISyntaxException {
        Map.loadMaps();
        this.map =  Map.getMaps().get(0); // todo : wtf ?
        Tile[][] tiles = this.map.getField();

        gamePane.setPrefHeight(740); // 37 tiles
        gamePane.setPrefWidth(1300); // 65 tiles

        this.createMainMap(gamePane, tiles);
        this.createDownPane(gamePane);
        this.createChooserRectangle();
        this.creatMiniMap(tiles);
    }

    private void createMainMap(Pane gamePane, Tile[][] tiles) {
        this.mainMap = new AnchorPane();
        for (Tile[] value : tiles) {
            for (int j = 0; j < tiles[0].length; j++) {
                this.mainMap.getChildren().add(value[j]);
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
                case "Z" -> {
                    Tile.zoom();
                    this.map.updateSizes();
                    this.map.updateImages();
                }
                case "X" -> {
                    Tile.zoomOut();
                    this.map.updateSizes();
                    this.map.updateImages();
                }
                case "M" -> {
                    this.setCursorOn(CursorType.SELECT_MOVE_DESTINATION);
                    this.isSelectedForMoveOrAttack = true;
                }
                case "A" -> {
                    this.setCursorOn(CursorType.SELECT_ATTACK_DESTINATION);
                    this.isSelectedForMoveOrAttack = true;
                }
            }
        });

        AtomicBoolean isNotDragged = new AtomicBoolean(true);

        final double[] startX = new double[1];
        final double[] startY = new double[1];
        this.mainMap.setOnMousePressed(mouseEvent -> {
            startX[0] = mouseEvent.getX();
            startY[0] = mouseEvent.getY();
        });

        this.mainMap.setOnMouseDragged(mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();

            ArrayList<Tile> rectangleTiles = this.map.getRectangleTilesByXY(startX[0] + Tile.getTileSize() * this.cursorRight,
                    startY[0] + Tile.getTileSize() * this.cursorDown, x + Tile.getTileSize()
                            * this.cursorRight, y + Tile.getTileSize() * this.cursorDown);

            Tile firstTile = this.map.getTileByXY(startX[0] + Tile.getTileSize() * this.cursorRight,
                    startY[0] + Tile.getTileSize() * this.cursorDown);
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

//            this.setSelectedTiles(rectangleTiles);

            isNotDragged.set(false);
        });

        this.mainMap.setOnMouseClicked(mouseEvent -> {
            if (isNotDragged.get()) {
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();

                Tile tile = this.map.getTileByXY(x + Tile.getTileSize() * this.cursorRight,
                        y + Tile.getTileSize() * this.cursorDown);

                tile.setSelectedEffect();
                ArrayList<Tile> selectedTiles = new ArrayList<>();
                selectedTiles.add(tile);
                this.setSelectedTiles(selectedTiles);
            }
            isNotDragged.set(true);
        });

    }

    public void goTo(int x, int y) {
        if (x < 32 || y < 18 || x > this.map.getSize() - 34 || y > this.map.getSize() - 29)
            return;

        this.mainMap.setLayoutX((32 - x) * Tile.getTileSize());
        this.mainMap.setLayoutY((18 - y) * Tile.getTileSize());
    }

    public void createDownPane(Pane gamePane) throws URISyntaxException {
        this.downPane = new DownPane(this);
        this.downPane.initialize(gamePane);

    }

    public void createDetailLabels(Pane detailPane) {
        // todo : set with currentGovernment :
        this.goldLabel = new Label(String.valueOf(100));
        this.popularityLabel = new Label(String.valueOf(100));

        this.goldLabel.setLayoutX(978);
        this.goldLabel.setLayoutY(93);
        this.popularityLabel.setLayoutX(965);
        this.popularityLabel.setLayoutY(113);

        this.goldLabel.setStyle("-fx-font-style: italic; -fx-font-size: 12; -fx-text-fill: green;");
        this.popularityLabel.setStyle("-fx-font-style: italic; -fx-font-size: 12; -fx-text-fill: green;");

        detailPane.getChildren().add(this.goldLabel);
        detailPane.getChildren().add(this.popularityLabel);
    }

    private void createChooserRectangle() {
        this.chooserRectangle = new Pane();
        this.chooserRectangle.setBackground(Background.EMPTY);
        this.chooserRectangle.setBorder(new Border(new BorderStroke(
                Color.DARKRED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        this.chooserRectangle.setPrefWidth(65 * 0.04 * Tile.getTileSize());
        this.chooserRectangle.setPrefHeight(27 * 0.04 * Tile.getTileSize());

        this.chooserRectangle.setLayoutX(1);
        this.chooserRectangle.setLayoutY(1);

        this.downPane.getChildren().add(this.chooserRectangle);
    }

    private void creatMiniMap(Tile[][] tiles) {
        GridPane miniMap = new GridPane();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                miniMap.add(tiles[i][j].getMiniTile(), i, j);
            }
        }

        miniMap.setBorder(new Border(new BorderStroke(
                Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        this.downPane.getChildren().add(miniMap);

        this.map.updateImages();
    }

    private void setCursorOn(CursorType cursorType) {
        this.mainMap.setCursor(cursorType.getImageCursor());
        this.cursorRight = 1;
        this.cursorDown = 1;
    }

    private void setSelectedTiles(ArrayList<Tile> selectedTiles) {
        this.selectedTiles = selectedTiles;
        this.cursorRight = 0;
        this.cursorDown = 0;
//        this.mainMap.setCursor(CursorType.DEFAULT.getImageCursor());

        if (!this.isSelectedForMoveOrAttack)
            this.downPane.setForTiles(selectedTiles);
    }

    public void goUp() {
        if (this.mainMap.getLayoutY() >= 0)
            return;
        this.mainMap.setLayoutY(this.mainMap.getLayoutY() + Tile.getTileSize());
        this.chooserRectangle.setLayoutY(this.chooserRectangle.getLayoutY() - 0.04 * Tile.getTileSize());
    }

    public void goLeft() {
        if (this.mainMap.getLayoutX() >= 0)
            return;
        this.mainMap.setLayoutX(this.mainMap.getLayoutX() + Tile.getTileSize());
        this.chooserRectangle.setLayoutX(this.chooserRectangle.getLayoutX() - 0.04 * Tile.getTileSize());
    }

    public void goDown() {
        if (this.mainMap.getLayoutY() <= -172 * 20)
            return;
        this.mainMap.setLayoutY(this.mainMap.getLayoutY() - Tile.getTileSize());
        this.chooserRectangle.setLayoutY(this.chooserRectangle.getLayoutY() + 0.04 * Tile.getTileSize());
    }

    public void goRight() {
        if (this.mainMap.getLayoutX() <= -134 * 20)
            return;
        this.mainMap.setLayoutX(this.mainMap.getLayoutX() - Tile.getTileSize());
        this.chooserRectangle.setLayoutX(this.chooserRectangle.getLayoutX() + 0.04 * Tile.getTileSize());
    }

    public Tile getTileByXY(double x, double y) {
        return this.map.getTileByXY(x,y);
    }

    public Government getCurrentGovernment() {
        return this.currentGovernment;
    }

    public AnchorPane getMainMap() {
        return mainMap;
    }

    public void updateDetails() { // todo : use this in all changes for gold & popularity
        this.goldLabel.setText(String.valueOf(currentGovernment.getGold()));
    }

}
