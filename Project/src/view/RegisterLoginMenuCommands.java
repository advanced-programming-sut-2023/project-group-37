package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RegisterLoginMenuCommands {

    REGISTER("\\s*user\\s+create((\\s+-u\\s+(?<username>\\S+))|(\\s+-u\\s+\"(?<username>.+)\")|" +
            "(\\s+-p\\s+(?<password>\\S+)\\s+(?<confirmPassword>\\S+))|" +
            "(\\s+-p\\s+\"(?<password>.+)\"\\s+\"(?<confirmPassword>.+)\")|(\\s+-e\\s+(?<email>\\S+))|" +
            "(\\s+-e\\s+\"(?<email>.+)\")|(\\s+-s\\s+(?<slogan>\\S+))|(\\s+-s\\s+\"(?<slogan>.+)\"))+\\s*"),//TODO  e draw max

    LOGIN("\\s*user\\s+login\\s+((\\s+-u\\s+(?<username>\\S+))|(\\s+-u\\s+\"(?<username>.+)\")|" +
            "(\\s+-p\\s+(?<password>\\S+))|(\\s+-p\\s+\"(?<password>.+)\"))+\\s*"),

    FORGOT_PASSWORD("\\s*forgot\\s+my\\s+password\\s*"),

    LOGOUT("\\s*user\\s+logout\\s*");


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
