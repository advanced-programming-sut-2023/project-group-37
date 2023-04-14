package view.enums.commands;

import view.enums.messages.GameMenuMessages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {
    SHOW_POPULARITY("\\s*show\\s+popularity\\s*");


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
