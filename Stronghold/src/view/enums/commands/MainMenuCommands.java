package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {

    ENTER_PROFILE_MENU("\\s*enter\\s+profile\\s+menu\\s*"),
    START_GAME(""),//todo
    LOGOUT("\\s*logout\\s*");

    private final String regex;
    MainMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, MainMenuCommands mainMenuCommands) {
        Matcher matcher = Pattern.compile(mainMenuCommands.regex).matcher(command);

        if (matcher.matches())
            return matcher;
        return null;
    }
}
