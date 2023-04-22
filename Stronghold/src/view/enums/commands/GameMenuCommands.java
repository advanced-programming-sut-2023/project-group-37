package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {
    SHOW_MAP("\\s*show\\s+map(\\s+((-x(?<x>\\d+))|(-y(?<y>\\d+))))+\\s*"),
    SHOW_POPULARITY("\\s*show\\s+popularity\\s*"),
    SHOW_POPULARITY_FACTORS("\\s*show\\s+popularity\\s+factors\\s*"),
    SHOW_FOOD_LIST("\\s*show\\s+food\\s+list\\s*"),
    FOOD_RATE("\\s*food\\s+rate\\s+-r\\s+(?<rateNumber>\\d+)\\s*"),
    FOOD_RATE_SHOW("\\s*food\\s+rate\\s+show\\s*"),
    TAX_RATE("\\s*tax\\s+rate\\s+-r\\s+(?<rateNumber>\\d+)\\s*"),
    TAX_RATE_SHOW("\\s*tax\\s+rate\\s+show\\s*"),
    FEAR_RATE("\\s*fear\\s+rate\\s+-r\\s+(?<rateNumber>\\d+)\\s*"),
    SHOW_FEAR_RATE("\\s*show\\s+fear\\s+rate\\s*"),
    DROP_BUILDING("\\s*dropbuilding(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+)))+\\s+-type\\s+(?<type>.+)\\s*"),
    CREATE_UNIT("\\s*createunit(\\s+((-t\\s+(?<type>/\".+\"|\\S+))|(-c\\s+(?<count>\\d+)))\\s*"),

    //Next menu commands
    SELECT_BUILDING("\\s*select\\s+building(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+)))+\\s*"),
    SELECT_UNIT("\\s*select\\s+unit(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+)))+\\s*"),
    ENTER_TRADE_MENU("\\s*enter\\s+trade\\s+menu\\s*"),
    ENTER_SHOP_MENU("\\s*enter\\s+shop\\s+menu\\s*"),

    //Texture commands
    SET_TEXTURE("\\s*settexture\\s+(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+))|(-t\\s+(?<type>\".+\"|\\S+)))+\\s*"),
    SET_RECTANGLE_TEXTURES("\\s*settexture\\s+(\\s+((-x1\\s+(?<x1>\\d+))|(-y1\\s+(?<y1>\\d+))|" +
            "(-x2\\s+(?<x2>\\d+))|(-y2\\s+(?<y2>\\d+))|(-t\\s+(?<type>\".+\"|\\S+)))+\\s*"),
    CLEAR_TEXTURE("\\s*clear(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+)))+\\s*"),
    DROP_ROCK("\\s*drop\\s+rock\\s+(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+))|(-d(?<direction>\".+\"|\\S+)))+\\s*"),
    DROP_TREE("\\s*droptree(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+))|(-t(?<type>\".+\"|\\S+)))+\\s*"),
    END_GAME(""); //todo

    private final String regex;
    GameMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, GameMenuCommands gameMenuCommands) {
        Matcher matcher = Pattern.compile(gameMenuCommands.regex).matcher(command);

        if (matcher.matches())
            return matcher;
        return null;
    }
}