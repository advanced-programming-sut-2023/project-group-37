package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MapMenuCommands {
    MOVE_THE_MAP("map(\\s+(((?<up>up)(\\s+(?<upDistance>\\d+))?)|((?<down>down)(\\s+(?<downDistance>\\d+))?)|" +
            "((?<left>left)(\\s+(?<leftDistance>\\d+))?)|((?<right>right)(\\s+(?<rightDistance>\\d+))?)))+\\s*"),
    SHOW_DETAILS("\\s*show\\s+details(\\s+((-x(?<x>\\d+))|(-y(?<y>\\d+))))+\\s*"),
    BACK_GAME_MENU("\\s*back\\s+to\\s+game\\s*");


    private final String regex;
    MapMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, MapMenuCommands mapMenuCommands) {
        Matcher matcher = Pattern.compile(mapMenuCommands.regex).matcher(command);

        if (matcher.matches())
            return matcher;
        return null;
    }
}