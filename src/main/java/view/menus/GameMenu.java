package view.menus;

import controller.AppController;
import controller.viewControllers.GameMenuController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.game.ItemCategory;
import model.game.Map;
import view.enums.Result;
import model.game.Tile;
import view.enums.Command;
import view.enums.Message;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu extends Application {
    private final AppController appController;
    private final GameMenuController gameMenuController;
    private String message;

    private final Scanner scanner;

    public GameMenu() {
        this.appController = AppController.getInstance();
        this.scanner = new Scanner(System.in);
        this.gameMenuController = GameMenuController.getInstance();
    }

    @Override
    public void start(Stage stage) throws Exception {
        GridPane mainPane = new GridPane();
        Pane gamePane = new Pane();

        Map.loadMaps();

        Tile[][] tiles = Map.getMaps().get(0).getField();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                mainPane.add(tiles[i][j], i, j);
            }
        }

//        for (int i = 50; i >= 0; i--) {
//            for (int j = 50; j >= 0; j--) {
//                gamePane.add(mainPane.getChildren().get((200 * i) + j), i, j);
//            }
//        }

        mainPane.setLayoutX(0);
        mainPane.setLayoutY(0);

        gamePane.setMaxHeight(700);
        gamePane.setMaxWidth(1000);

        gamePane.getChildren().add(mainPane);
        Scene scene = new Scene(gamePane);
        stage.setScene(scene);
        stage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), actionEvent -> {
            if (mainPane.getLayoutY() <= -180 * 20)
                return;
            mainPane.setLayoutY(mainPane.getLayoutY() - 20);
        }));
//        timeline.setOnFinished(actionEvent -> {
//
//            Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100), actionEvent1 -> {
//                rectangle.setWidth(rectangle.getWidth() - 20);
//                rectangle.setHeight(rectangle.getHeight() - 20);
//            }));
//            timeline1.setCycleCount(6);
//            timeline1.play();
//        });
        timeline.setCycleCount(-1);
        timeline.play();
    }

    public Result run() {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = Command.SHOW_MAP.getMatcher(command)) != null) {
                if (matcher.group("x") == null || matcher.group("y") == null) {
                    System.out.println(Message.EMPTY_FIELD);
                } else {
                    if (showMap(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")))) {
                        System.out.println(Message.ENTERED_MAP_MENU);
                        return Result.ENTER_MAP_MENU;
                    }
                }
            } else if (command.matches(Command.SHOW_POPULARITY.toString()))
                System.out.println(this.gameMenuController.showPopularity());
            else if (command.matches(Command.SHOW_POPULARITY_FACTORS.toString()))
                System.out.println(this.gameMenuController.showPopularityFactors());
            else if (command.matches(Command.SHOW_FOOD_LIST.toString()))
                System.out.println(this.gameMenuController.showItemList(ItemCategory.FOODS));
            else if (command.matches(Command.SHOW_RESOURCE_LIST.toString()))
                System.out.println(this.gameMenuController.showItemList(ItemCategory.RESOURCES));
            else if (command.matches(Command.SHOW_ARM_LIST.toString()))
                System.out.println(this.gameMenuController.showItemList(ItemCategory.WEAPONS));
            else if ((matcher = Command.FOOD_RATE.getMatcher(command)) != null)
                System.out.println(this.gameMenuController.setFoodRate(matcher));
            else if (command.matches(Command.FOOD_RATE_SHOW.toString()))
                System.out.println(this.gameMenuController.showFoodRate());
            else if ((matcher = Command.TAX_RATE.getMatcher(command)) != null)
                System.out.println(this.gameMenuController.setTaxRate(matcher));
            else if (command.matches(Command.TAX_RATE_SHOW.toString()))
                System.out.println(this.gameMenuController.showTaxRate());
            else if ((matcher = Command.FEAR_RATE.getMatcher(command)) != null)
                System.out.println(this.gameMenuController.setFearRate(matcher));
            else if (command.matches(Command.FEAR_RATE_SHOW.toString()))
                System.out.println(this.gameMenuController.showFearRate());
            else if ((matcher = Command.DROP_BUILDING.getMatcher(command)) != null)
                System.out.println(this.gameMenuController.dropBuilding(matcher));
            else if ((matcher = Command.DROP_GATEHOUSE.getMatcher(command)) != null)
                System.out.println(this.gameMenuController.dropBuilding(matcher));
            else if ((matcher = Command.DROP_UNIT.getMatcher(command)) != null) {
                System.out.println(gameMenuController.dropUnit(matcher));
            } else if ((matcher = Command.SELECT_BUILDING.getMatcher(command)) != null) {
                if (selectBuilding(matcher))
                    return Result.ENTER_BUILDING_MENU;
            } else if ((matcher = Command.SELECT_UNIT.getMatcher(command)) != null) {
                if (selectUnit(matcher))
                    return Result.ENTER_UNIT_MENU;
            } else if (command.matches(Command.ENTER_SHOP_MENU.toString())) {
                String message;
                System.out.println(message = this.gameMenuController.enterShopMenu());
                if (!message.equals(Message.MARKET_NOT_EXISTS.toString()))
                    return Result.ENTER_SHOP_MENU;
            } else if (command.matches(Command.ENTER_TRADE_MENU.toString())) {
                System.out.println(Message.ENTERED_TRADE_MENU);
                System.out.println(gameMenuController.enterTradeMenu());
                return Result.ENTER_TRADE_MENU;
            } else if ((matcher = Command.SET_TEXTURE.getMatcher(command)) != null)
                System.out.println(this.gameMenuController.setTexture(matcher));
            else if ((matcher = Command.SET_RECTANGLE_TEXTURES.getMatcher(command)) != null)
                System.out.println(this.gameMenuController.setRectangleTextures(matcher));

            else if ((matcher = Command.CLEAR_TEXTURE.getMatcher(command)) != null)
                System.out.println(this.gameMenuController.clearTexture(matcher));

            else if ((matcher = Command.DROP_ROCK.getMatcher(command)) != null)
                System.out.println(this.gameMenuController.dropRock(matcher));

            else if ((matcher = Command.DROP_TREE.getMatcher(command)) != null)
                System.out.println(this.gameMenuController.dropTree(matcher));

            else if (command.matches(Command.SHOW_INFO.toString()))
                System.out.println(gameMenuController.showInfo());

            else if (command.matches(Command.NEXT_TURN.toString())) {
                if (goNextTurn())
                    return Result.END_GAME;
            } else if (command.matches(Command.END_GAME.toString())) {
                System.out.println(gameMenuController.endGame());
                return Result.END_GAME;
            } else
                System.out.println(Message.INVALID_COMMAND);
        }
    }

    private boolean showMap(int x, int y) {
        if (gameMenuController.getMap().getTileByLocation(x, y) == null) {
            System.out.println(Message.ADDRESS_OUT_OF_BOUNDS);
            return false;
        }

        Tile[][] tiles = this.gameMenuController.showMap(x, y);

        return MapMenu.showMapWithTiles(tiles);
    }

    private boolean selectBuilding(Matcher matcher) {
        this.message = this.gameMenuController.selectBuilding(matcher);
        System.out.println(this.message);
        return message.contains(Message.ENTERED_BUILDING_MENU.toString());
    }

    private boolean selectUnit(Matcher matcher) {
        if (matcher.group("x") == null || matcher.group("y") == null) {
            this.message = Message.EMPTY_FIELD.toString();
            return false;
        }

        this.message = this.gameMenuController.selectUnit(Integer.parseInt(matcher.group("x")),
                Integer.parseInt(matcher.group("y")));
        System.out.println(this.message);

        return Message.UNIT_SELECTED.equals(message);
    }

    private boolean goNextTurn() {
        String message = gameMenuController.goNextTurn();
        System.out.println(message);
        return message.contains("ended");
    }
}