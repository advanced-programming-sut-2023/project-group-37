package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {

    LOGIN("\\s*user\\s+login(\\s+((-u\\s+(?<username>\".+\"|\\S+))|" +
            "(-p\\s+(?<password>\".+\"|\\S+))|(?<stayLoggedIn>--stay-logged-in)))+\\s*"),
    FORGOT_PASSWORD("\\s*forgot\\s+my\\s+password\\s+-u\\s+(?<username>\".+\"|\\S+)\\s*"),
    ENTER_REGISTER_MENU("\\s*enter\\s+register\\s+menu\\s*"),
    EXIT("\\s*exit\\s*");

    private final String regex;
    LoginMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, LoginMenuCommands loginMenuCommands) {
        Matcher matcher = Pattern.compile(loginMenuCommands.regex).matcher(command);

        if (matcher.matches())
            return matcher;
        return null;
    }
}
