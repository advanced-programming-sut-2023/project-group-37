package view.menus;

import controller.GameMenuController;
import view.enums.Result;
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

            if ((matcher = Command.SHOW_MAP.getMatcher(command)) != null)
                if (showMap(matcher))
                    return Result.ENTER_MAP_MENU;
                else if (Command.SHOW_POPULARITY.getMatcher(command) != null)
                    System.out.println(this.controller.showPopularity());
                else if (Command.SHOW_POPULARITY_FACTORS.getMatcher(command) != null)
                    System.out.println(this.controller.showPopularityFactors());
                else if (Command.SHOW_FOOD_LIST.getMatcher(command) != null)
                    System.out.println(this.controller.showFoodList());
                else if ((matcher = Command.FOOD_RATE.getMatcher(command)) != null)
                    System.out.println(this.controller.setFoodRate(matcher));
                else if (Command.FOOD_RATE_SHOW.getMatcher(command) != null)
                    System.out.println(this.controller.showFoodRate());
                else if ((matcher = Command.TAX_RATE.getMatcher(command)) != null)
                    System.out.println(this.controller.setTaxRate(matcher));
                else if (Command.TAX_RATE_SHOW.getMatcher(command) != null)
                    System.out.println(this.controller.showTaxRate());
                else if ((matcher = Command.FEAR_RATE.getMatcher(command)) != null)
                    System.out.println(this.controller.setFearRate(matcher));
                else if (Command.SHOW_FEAR_RATE.getMatcher(command) != null)
                    System.out.println(this.controller.showFearRate());
                else if ((matcher = Command.DROP_BUILDING.getMatcher(command)) != null)
                    System.out.println(this.controller.dropBuilding(matcher));
                else if ((matcher = Command.CREATE_UNIT.getMatcher(command)) != null)
                    System.out.println(this.controller.createUnit(matcher));
                else if ((matcher = Command.SELECT_BUILDING.getMatcher(command)) != null) {
                    if (selectBuilding(matcher))
                        return Result.ENTER_BUILDING_MENU;

                } else if ((matcher = Command.SELECT_UNIT.getMatcher(command)) != null) {
                    if (selectUnit(matcher))
                        return Result.ENTER_UNIT_MENU;

                } else if (Command.ENTER_SHOP_MENU.getMatcher(command) != null) {
                    System.out.println(Message.ENTERED_SHOP_MENU);
                    return Result.ENTER_SHOP_MENU;

                } else if (Command.ENTER_TRADE_MENU.getMatcher(command) != null) {
                    System.out.println(Message.ENTERED_TRADE_MENU);
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
                else if (Command.END_GAME.getMatcher(command) != null)
                    return Result.END_GAME;
                else
                    System.out.println(Message.INVALID_COMMAND);
        }
    }

    private boolean showMap(Matcher matcher) {
        this.message = this.controller.showMap(Integer.parseInt(matcher.group("x")),
                Integer.parseInt(matcher.group("y")));

        System.out.println(this.message);
        return Message.ENTERED_MAP_MENU.equals(message);
    }

    private boolean selectBuilding(Matcher matcher) {
        this.message = this.controller.selectBuilding(matcher);
        System.out.println(this.message);

        return Message.ENTERED_BUILDING_MENU.equals(message);
    }

    private boolean selectUnit(Matcher matcher) {
        this.message = this.controller.selectUnit(matcher);
        System.out.println(this.message);

        return Message.ENTERED_UNIT_MENU.equals(message);
    }
}