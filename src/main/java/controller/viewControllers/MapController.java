package controller.viewControllers;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.buildings.DefensiveBuilding;
import model.game.Game;
import model.game.Government;
import model.game.Map;
import model.game.Tile;
import model.people.MilitaryMachine;
import model.people.MilitaryUnit;
import model.people.Troop;
import view.enums.Message;

import java.util.regex.Matcher;

public class MapController {
    private static final MapController MAP_CONTROLLER;
    private Game game;
    private Map map;
    private GridPane mainPane;
    private Government currentGovernment;
    private int currentX;
    private int currentY;

    static {
        MAP_CONTROLLER = new MapController();
    }

    private MapController() {

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
        Tile[][] tiles = Map.getMaps().get(0).getField(); // todo : wtf ?

        this.mainPane = new GridPane();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                this.mainPane.add(tiles[i][j], i, j);
            }
        }

        this.mainPane.setLayoutX(0);
        this.mainPane.setLayoutY(0);

        gamePane.setMaxHeight(740); // 37 tiles
        gamePane.setMaxWidth(1300); // 65 tiles

        gamePane.getChildren().add(this.mainPane);

        this.mainPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyName = keyEvent.getCode().getName();

                switch (keyName) {
                    case "Left" -> goLeft();
                    case "Right" -> goRight();
                    case "Up" -> goUp();
                    case "Down" -> goDown();
                }
            }
        });

        final double[] startX = new double[1];
        final double[] startY = new double[1];
        this.mainPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                startX[0] = mouseEvent.getSceneX();
                startY[0] = mouseEvent.getSceneY();
            }
        });

        this.mainPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double x = mouseEvent.getSceneX();
                double y = mouseEvent.getSceneY();
                if (x > startX[0]) goLeft();
                else if (x < startX[0]) goRight();
                else if (y < startY[0]) goDown();
                else if (y > startY[0]) goUp();

            }
        });
    }


    public void goTo(int x, int y) {
        if (x < 32 || y < 18 || x > this.map.getSize() - 34 || y > this.map.getSize() - 20)
            return;

        this.mainPane.setLayoutX((32 - x) * 20);
        this.mainPane.setLayoutY((18 - y) * 20);
    }

    public void goUp() {
        if (this.mainPane.getLayoutY() == 0)
            return;
        this.mainPane.setLayoutY(this.mainPane.getLayoutY() + 20);
    }

    public void goLeft() {
        if (this.mainPane.getLayoutX() == 0)
            return;
        this.mainPane.setLayoutX(this.mainPane.getLayoutX() + 20);
    }

    public void goDown() {
        if (this.mainPane.getLayoutY() == -162 * 20)
            return;
        this.mainPane.setLayoutY(this.mainPane.getLayoutY() - 20);
    }

    public void goRight() {
        if (this.mainPane.getLayoutX() == -134 * 20)
            return;
        this.mainPane.setLayoutX(this.mainPane.getLayoutX() - 20);
    }

    public String showDetails(Matcher matcher) {
        if (matcher.group("x") == null || matcher.group("y") == null)
            return Message.EMPTY_FIELD.toString();

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Tile tile;

        if ((tile = map.getTileByLocation(x, y)) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        StringBuilder details = new StringBuilder();
        details.append("The texture: ").append(tile.getTexture().name()).append("\n");

        if (tile.hasBuilding()) {
            if (tile.getBuilding().getLoyalty() == currentGovernment) {
                if (tile.getBuilding() instanceof DefensiveBuilding defensiveBuilding)
                    details.append("you have the building (").append(defensiveBuilding.getDefensiveType().getName()).append(") here!\n");

                else
                    details.append("You have the building (").append(tile.getBuilding().getType().getName()).append(") here!\n");
            }
        } else
            details.append("You have no buildings here!\n");

        if (tile.getMilitaryUnits().size() == 0)
            details.append("You have no troops here!\n");
        else {
            int troopCounter = 1;
            int machineCounter = 1;
            for (MilitaryUnit militaryUnit : tile.getMilitaryUnits()) {
                if (militaryUnit instanceof Troop) {
                    if (militaryUnit.getLoyalty() == currentGovernment) {
                        details.append("Troop ").append(troopCounter).append(": ").append(((Troop) militaryUnit).getType().name()).append("\n");
                        troopCounter++;
                    }
                } else if (militaryUnit instanceof MilitaryMachine) {
                    if (militaryUnit.getLoyalty() == currentGovernment) {
                        details.append("Machine ").append(machineCounter).append(": ").append(((MilitaryMachine) militaryUnit).getType().name()).append("\n");
                        machineCounter++;
                    }
                }
            }
        }

        return details.toString().trim();

    }

}

