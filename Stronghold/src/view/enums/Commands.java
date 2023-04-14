package view.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    ;

    private final String regex;

    Commands(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.regex).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}