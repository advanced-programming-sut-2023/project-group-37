package view.enums.commands;

import model.buildings.enums.Buildings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum BuildingMenuCommands {

    DROP_BUILDING("\\s*dropbuilding(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+)))+\\s+-type\\s+(?<type>.+)\\s*"),
    SELECT_BUILDING("\\s*select\\s+building(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+)))+\\s*"),
    REPAIR("\\s*repair\\s*");

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
