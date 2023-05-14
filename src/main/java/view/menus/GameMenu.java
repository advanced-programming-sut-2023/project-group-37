package view.menus;

import controller.GameMenuController;
import controller.MultiMenuFunctions;
import model.game.ItemCategory;
import view.enums.Result;
import model.game.Tile;
import view.enums.Command;
import view.enums.Message;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    private final GameMenuController controller;
    private String message;

    private final Scanner scanner;

    public GameMenu(Scanner scanner, GameMenuController gameMenuController) {
        this.scanner = scanner;
        this.controller = gameMenuController;
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
                System.out.println(this.controller.showPopularity());
            else if (command.matches(Command.SHOW_POPULARITY_FACTORS.toString()))
                System.out.println(this.controller.showPopularityFactors());
            else if (command.matches(Command.SHOW_FOOD_LIST.toString()))
                System.out.println(this.controller.showItemList(ItemCategory.FOODS));
            else if (command.matches(Command.SHOW_RESOURCE_LIST.toString()))
                System.out.println(this.controller.showItemList(ItemCategory.RESOURCES));
            else if (command.matches(Command.SHOW_ARM_LIST.toString()))
                System.out.println(this.controller.showItemList(ItemCategory.WEAPONS));
            else if ((matcher = Command.FOOD_RATE.getMatcher(command)) != null)
                System.out.println(this.controller.setFoodRate(matcher));
            else if (command.matches(Command.FOOD_RATE_SHOW.toString()))
                System.out.println(this.controller.showFoodRate());
            else if ((matcher = Command.TAX_RATE.getMatcher(command)) != null)
                System.out.println(this.controller.setTaxRate(matcher));
            else if (command.matches(Command.TAX_RATE_SHOW.toString()))
                System.out.println(this.controller.showTaxRate());
            else if ((matcher = Command.FEAR_RATE.getMatcher(command)) != null)
                System.out.println(this.controller.setFearRate(matcher));
            else if (command.matches(Command.FEAR_RATE_SHOW.toString()))
                System.out.println(this.controller.showFearRate());
            else if ((matcher = Command.DROP_BUILDING.getMatcher(command)) != null)
                System.out.println(this.controller.dropBuilding(matcher));
            else if ((matcher = Command.DROP_GATEHOUSE.getMatcher(command)) != null)
                System.out.println(this.controller.dropBuilding(matcher));
            else if ((matcher = Command.DROP_UNIT.getMatcher(command)) != null) {
                System.out.println(controller.dropUnit(matcher));
            } else if ((matcher = Command.SELECT_BUILDING.getMatcher(command)) != null) {
                if (selectBuilding(matcher))
                    return Result.ENTER_BUILDING_MENU;
            } else if ((matcher = Command.SELECT_UNIT.getMatcher(command)) != null) {
                if (selectUnit(matcher))
                    return Result.ENTER_UNIT_MENU;
            } else if (command.matches(Command.ENTER_SHOP_MENU.toString())) {
                String message;
                System.out.println(message = this.controller.enterShopMenu());
                if (!message.equals(Message.MARKET_NOT_EXISTS.toString()))
                    return Result.ENTER_SHOP_MENU;
            } else if (command.matches(Command.ENTER_TRADE_MENU.toString())) {
                System.out.println(Message.ENTERED_TRADE_MENU);
                System.out.println(controller.enterTradeMenu());
                return Result.ENTER_TRADE_MENU;
            } else if ((matcher = Command.SET_TEXTURE.getMatcher(command)) != null)
                System.out.println(this.controller.setTexture(matcher));
            else if ((matcher = Command.SET_RECTANGLE_TEXTURES.getMatcher(command)) != null)
                System.out.println(this.controller.setRectangleTextures(matcher));

            else if ((matcher = Command.CLEAR_TEXTURE.getMatcher(command)) != null)
                System.out.println(this.controller.clearTexture(matcher));

            else if ((matcher = Command.DROP_ROCK.getMatcher(command)) != null)
                System.out.println(this.controller.dropRock(matcher));

            else if ((matcher = Command.DROP_TREE.getMatcher(command)) != null)
                System.out.println(this.controller.dropTree(matcher));

            else if (command.matches(Command.SHOW_INFO.toString()))
                System.out.println(controller.showInfo());

            else if (command.matches(Command.NEXT_TURN.toString())) {
                if (goNextTurn())
                    return Result.END_GAME;
            } else if (command.matches(Command.END_GAME.toString())) {
                System.out.println(controller.endGame());
                return Result.END_GAME;
            } else
                System.out.println(Message.INVALID_COMMAND);
        }
    }

    private boolean showMap(int x, int y) {
        if (controller.getMap().getTileByLocation(x, y) == null) {
            System.out.println(Message.ADDRESS_OUT_OF_BOUNDS);
            return false;
        }

        Tile[][] tiles = this.controller.showMap(x, y);

        return MapMenu.showMapWithTiles(tiles);
    }

    private boolean selectBuilding(Matcher matcher) {
        this.message = this.controller.selectBuilding(matcher);
        System.out.println(this.message);
        return message.contains(Message.ENTERED_BUILDING_MENU.toString());
    }

    private boolean selectUnit(Matcher matcher) {
        if (matcher.group("x") == null || matcher.group("y") == null) {
            this.message = Message.EMPTY_FIELD.toString();
            return false;
        }

        this.message = this.controller.selectUnit(Integer.parseInt(matcher.group("x")),
                Integer.parseInt(matcher.group("y")));
        System.out.println(this.message);

        return Message.UNIT_SELECTED.equals(message);
    }

    private boolean goNextTurn() {
        String message = controller.goNextTurn();
        System.out.println(message);
        return message.contains("ended");
    }
}