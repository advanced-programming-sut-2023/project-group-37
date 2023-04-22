package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UnitMenuCommands {

    MOVE_UNIT("\\s*move\\s+unit\\s+to(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+)))+\\s*"),
    PATROL_UNIT("\\s*patrol\\s+unit(\\s+((-x1\\s+(?<x1>\\d+))|(-y1\\s+(?<y1>\\d+))|" +
            "(-x2\\s+(?<x2>\\d+))|(-y2\\s+(?<y2>\\d+)))+\\s*"),
    SET_UNIT("\\s*set(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+))|(-s\\s+(?<state>\".+\"|\\S+)))+\\s*"),
    ATTACK("\\s*attack(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+))|(?<isEarth>-e)))+\\s*"),//if isEarth is not null we have earth attack
    POUR_OIL("\\s*pour\\s+oil\\s+-d\\s+(?<direction>\".+\"|\\S+)\\s*"),
    DIG_TUNNEL("\\s*dig\\s+tunnel\\s+(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+)))+\\s*"),
    BUILD_EQUIPMENT("\\s*build\\s+-q\\s+(?<equipmentName>\".+\"|\\S+)\\s*"),
    DISBAND_UNIT("\\s*disband\\s*"),
    BACK_GAME_MENU("\\s*back\\s+to\\s+game\\s*");

    private final String regex;
    UnitMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, UnitMenuCommands unitMenuCommands) {
        Matcher matcher = Pattern.compile(unitMenuCommands.regex).matcher(command);

        if (matcher.matches())
            return matcher;
        return null;
    }
}
