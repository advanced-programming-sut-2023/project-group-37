package view.enums.commands;

import model.buildings.enums.Buildings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum BuildingMenuCommands {

    REPAIR("\\s*repair\\s*"),
    BACK_GAME_MENU("\\s*back\\s+to\\s+game\\s*");

    private final String regex;
    BuildingMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, BuildingMenuCommands buildingMenuCommands) {
        Matcher matcher = Pattern.compile(buildingMenuCommands.regex).matcher(command);

        if (matcher.matches())
            return matcher;
        return null;
    }
}
