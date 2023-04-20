package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeMenuCommands {

    TRADE_REQUEST("\\s*trade(\\s+((-t(?<resourceType>\".+\"|\\S+))|(-a(?<resourceAmount>\\d+))|" +
                          "(-p(?<price>\\d+))|(-m(?<message>\".+\"|\\S+))))+\\s*"),
    SHOW_TRADE_LIST("\\s*trade\\s+list\\s*"),
    TRADE_ACCEPT("\\s*trade\\s+accept(\\s+((-i(?<id>\\d+))|(-m(?<message>\".+\"|\\S+))))+\\s*"),
    SHOW_TRADE_HISTORY("\\s*trade\\s+history\\s*");

    private final String regex;
    TradeMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, TradeMenuCommands tradeMenuCommands) {
        Matcher matcher = Pattern.compile(tradeMenuCommands.regex).matcher(command);

        if (matcher.matches())
            return matcher;
        return null;
    }
}
