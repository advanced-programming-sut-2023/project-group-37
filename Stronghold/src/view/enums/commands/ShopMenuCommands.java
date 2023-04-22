package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ShopMenuCommands {

    SHOW_PRICE_LIST("\\s*show\\s+price\\s+list\\s*"),
    BUY_ITEM("\\s*buy(\\s+((-i(?<itemName>\".+\"|\\S+))|(-a(?<itemAmount>\\d+))))+\\s*"),
    SELL_ITEM("\\s*sell(\\s+((-i(?<itemName>\".+\"|\\S+))|(-a(?<itemAmount>\\d+))))+\\s*"),
    BACK_GAME_MENU("\\s*back\\s+to\\s+game\\s*");

    private final String regex;
    ShopMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, ShopMenuCommands shopMenuCommands) {
        Matcher matcher = Pattern.compile(shopMenuCommands.regex).matcher(command);

        if (matcher.matches())
            return matcher;
        return null;
    }
}
