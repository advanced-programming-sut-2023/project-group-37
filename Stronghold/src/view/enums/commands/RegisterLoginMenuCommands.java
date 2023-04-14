package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RegisterLoginMenuCommands {
    REGISTER("\\s*user\\s+create(\\s+(\\s+-u\\s+(?<username>\\S+))|(-u\\s+\"(?<username>.+)\")|" +
            "(-p\\s+(?<password>\\S+)\\s+(?<passwordConfirm>\\S+))|" +
            "(-p\\s+\"(?<password>.+)\"\\s+\"(?<passwordConfirm>.+)\")|(-e\\s+(?<email>\\S+))|" +
            "(-e\\s+\"(?<email>.+)\")|(-s\\s+(?<slogan>\\S+))|(-s\\s+\"(?<slogan>.+)\"))+\\s*"),
    LOGIN(""),
    FORGOT_PASSWORD(""),
    LOGOUT(""),
    PICK_QUESTION("");


    private final String regex;
    RegisterLoginMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, RegisterLoginMenuCommands registerLoginMenuCommands) {
        Matcher matcher = Pattern.compile(registerLoginMenuCommands.regex).matcher(command);

        if (matcher.matches())
            return matcher;
        return null;
    }
}
