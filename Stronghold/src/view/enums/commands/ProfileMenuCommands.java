package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    CHANGE_USERNAME("\\s*profile\\s+change\\s+-u\\s+((?<username>\\S+)|(\"?<username>.+\"))\\s*");


    private final String regex;
    ProfileMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, ProfileMenuCommands profileMenuCommands) {
        Matcher matcher = Pattern.compile(profileMenuCommands.regex).matcher(command);

        if (matcher.matches())
            return matcher;
        return null;
    }
}
