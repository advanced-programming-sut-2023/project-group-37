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
    FEAR_RATE("\\s*fear\\s+rate\\s+-r\\s+(?<rateNumber>\\d+)\\s*");

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