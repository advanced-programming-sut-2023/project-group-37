package view.menus;

import controller.GameMenuController;
import view.enums.Results;
import view.enums.commands.GameMenuCommands;
import view.enums.messages.GameMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    private final GameMenuController controller = new GameMenuController();
    private final Scanner scanner;
    private GameMenuMessages message;

    public GameMenu(Scanner scanner){
        this.scanner = scanner;
    }

    public Results run() {
        //TODO: fill
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_MAP)) != null) {
                if (showMap(matcher))
                    return Results.ENTER_MAP_MENU;
            }

            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_POPULARITY) != null)
                System.out.println(controller.showPopularity());

            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_POPULARITY_FACTORS) != null)
                System.out.println(controller.showPopularityFactors());

            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_FOOD_LIST) != null)
                System.out.println(controller.showFoodList());

            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.FOOD_RATE)) != null)
                System.out.println(controller.setFoodRate(matcher));

            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.FOOD_RATE_SHOW) != null)
                System.out.println(controller.showFoodRate());

            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.TAX_RATE)) != null)
                System.out.println(controller.setTaxRate(matcher));

            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.TAX_RATE_SHOW) != null)
                System.out.println(controller.showTaxRate());

            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.FEAR_RATE)) != null)
                System.out.println(controller.setFearRate(matcher));

            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_FEAR_RATE) != null)
                System.out.println(controller.showFearRate());

            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.DROP_BUILDING)) != null)
                System.out.println(controller.dropBuilding(matcher));

            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.CREATE_UNIT)) != null)
                System.out.println(controller.createUnit(matcher));

            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SELECT_BUILDING)) != null) {
                if (selectBuilding(matcher))
                    return Results.ENTER_BUILDING_MENU;
            }

            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SELECT_UNIT)) != null) {
                if (selectUnit(matcher))
                    return Results.ENTER_UNIT_MENU;
            }

            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.ENTER_SHOP_MENU) != null)
            {
                System.out.println(controller.enterShopMenu());
                return Results.ENTER_SHOP_MENU;
            }

            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.ENTER_TRADE_MENU) != null) {
                System.out.println(controller.enterTradeMenu());
                return Results.ENTER_TRADE_MENU;
            }

            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SET_TEXTURE)) != null)
                System.out.println(controller.setTexture(matcher));

            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SET_RECTANGLE_TEXTURES)) != null)
                System.out.println(controller.setRectangleTextures(matcher));

            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.CLEAR_TEXTURE)) != null)
                System.out.println(controller.clearTexture(matcher));

            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.DROP_ROCK)) != null)
                System.out.println(controller.dropRock(matcher));

            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.DROP_TREE)) != null)
                System.out.println(controller.dropTree(matcher));

            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.END_GAME) != null)
                return Results.END_GAME;

            else System.out.println("Invalid command!");
        }

    }

    private boolean showMap(Matcher matcher) {
        message = controller.showMap(matcher);
        System.out.println(message);

        return message == GameMenuMessages.ENTERED_MAP_MENU;
    }

    private boolean selectBuilding(Matcher matcher) {
        message = controller.selectBuilding(matcher);
        System.out.println(message);

        return message == GameMenuMessages.ENTERED_BUILDING_MENU;
    }

    private boolean selectUnit(Matcher matcher) {
        message = controller.selectUnit(matcher);
        System.out.println(message);

        return message == GameMenuMessages.ENTERED_UNIT_MENU;
    }
}