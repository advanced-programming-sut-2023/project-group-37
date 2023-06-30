package controller;

import javafx.scene.ImageCursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.game.Game;
import model.game.Government;
import model.game.Map;
import model.game.Tile;
import model.graphic.CursorType;
import model.graphic.DownPane;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


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
    private Label attackingLabel;
    private double cursorRight;
    private double cursorDown;
    private boolean isOnAttack;
    private boolean isOnMove;

    static {
        MAP_CONTROLLER = new MapController();
    }

    public Game getGame() {
        return game;
    }

    private MapController() {
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
        this.map = this.game.getMap();
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
        this.map.updateImages();

        this.mainMap.setOnKeyPressed(keyEvent -> {
            String keyName = keyEvent.getCode().getName();

            switch (keyName) {
                case "Left" -> {
                    goLeft();
                    goLeft();
                    goLeft();
                }
                case "Right" -> {
                    goRight();
                    goRight();
                    goRight();
                }
                case "Up" -> {
                    goUp();
                    goUp();
                    goUp();
                }
                case "Down" -> {
                    goDown();
                    goDown();
                    goDown();
                }
                case "Z" -> {
                    if (Tile.zoom()) {
                        MultiMenuFunctions.removeAllImages();
                        this.map.updateSizes();
                    }
                }
                case "X" -> {
                    if (Tile.zoomOut()) {
                        MultiMenuFunctions.removeAllImages();
                        this.map.updateSizes();
                    }

                }
                case "M" -> {
                    this.setCursorOn(CursorType.SELECT_MOVE_DESTINATION);
                    this.isOnMove = true;
                    this.isOnAttack = false;
                }
                case "A" -> {
                    this.setCursorOn(CursorType.SELECT_ATTACK_DESTINATION);
                    this.isOnAttack = true;
                    this.isOnMove = false;
                }
                case "T" -> this.downPane.setForTradeMenu();
                case "S" -> this.downPane.setForShopMenu();
            }
        });

        AtomicBoolean isNotDragged = new AtomicBoolean(true);

        final double[] startX = new double[1];
        final double[] startY = new double[1];
        this.mainMap.setOnMousePressed(mouseEvent -> {
            startX[0] = mouseEvent.getX();
            startY[0] = mouseEvent.getY();
        });

        AtomicReference<ArrayList<Tile>> rectangleTiles = new AtomicReference<>();

        this.mainMap.setOnMouseDragged(mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();

            rectangleTiles.set(this.map.getRectangleTilesByXY(startX[0] + this.cursorRight,
                    startY[0] + this.cursorDown, x + this.cursorRight,
                    y + this.cursorDown));

            Tile firstTile = this.map.getTileByXY(startX[0] + this.cursorRight,
                    startY[0] + this.cursorDown);
            Tile secondTile = this.map.getTileByXY(x, y);

            for (Tile rectangleTile : rectangleTiles.get()) {
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

        this.mainMap.setOnMouseReleased((MouseEvent mouseEvent) -> this.setSelectedTiles(rectangleTiles.get()));

        this.mainMap.setOnMouseClicked(mouseEvent -> {
            if (isNotDragged.get()) {
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();

                Tile tile = this.map.getTileByXY(x + this.cursorRight,
                        y + this.cursorDown);

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
        this.goldLabel = new Label(String.valueOf(currentGovernment.getGold()));
        this.popularityLabel = new Label(String.valueOf(currentGovernment.getPopularity()));

        this.goldLabel.setLayoutX(940);
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
        this.cursorRight = 20;
        this.cursorDown = 14;
    }

    private void setSelectedTiles(ArrayList<Tile> selectedTiles) {
        this.mainMap.setCursor(ImageCursor.DEFAULT);

        if (selectedTiles == null)
            return;

        if (this.isOnAttack) {
            if (selectedTiles.size() == 1) {
                if (this.downPane.attack(selectedTiles.get(0)))
                    this.showAttacking();
            }
        } else if (this.isOnMove) {
            if (selectedTiles.size() == 1)
                this.downPane.move(selectedTiles.get(0));
        } else this.downPane.setForTiles(selectedTiles);

        this.cursorRight = 0;
        this.cursorDown = 0;
        this.isOnMove = false;
        this.isOnAttack = false;
    }

    private void showAttacking() {
        if (this.attackingLabel != null)
            this.downPane.getChildren().remove(this.attackingLabel);

        this.attackingLabel = new Label("Attacking");
        this.attackingLabel.setTextFill(Color.RED);
        this.attackingLabel.setFont(new Font(30));

        this.attackingLabel.setLayoutX(950);
        this.attackingLabel.setLayoutY(15);
        this.downPane.getChildren().add(this.attackingLabel);
    }

    public void stopShowingAttack() {
        this.downPane.getChildren().remove(this.attackingLabel);
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
        return this.map.getTileByXY(x - mainMap.getLayoutX(), y - mainMap.getLayoutY());
    }

    public Tile getTileByLocation(int x, int y) {
        return this.map.getTileByLocation(x, y);
    }

    public Government getCurrentGovernment() {
        return this.currentGovernment;
    }

    public AnchorPane getMainMap() {
        return mainMap;
    }

    public DownPane getDownPane() {
        return downPane;
    }

    public void updateDetails() {
        this.goldLabel.setText(String.valueOf(currentGovernment.getGold()));
        this.popularityLabel.setText(String.valueOf(currentGovernment.getPopularity()));
    }

}
