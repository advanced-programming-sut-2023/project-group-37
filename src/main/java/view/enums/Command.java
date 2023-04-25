package view.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Command {

    // MultiMenu:
    EXIT("\\s*exit\\s*"),
    CANCEL("\\s*cancel\\s*"),

    // SignupMenu:
    REGISTER("\\s*create\\s+user(\\s+((-u\\s+(?<username>\".+\"|\\S+))|" +
            "(-p\\s+(?<password>\".+\"|\\S+)\\s+(?<passwordConfirm>\".+\"|\\S+))|" +
            "(-e\\s+(?<email>\".+\"|\\S+))|(-s\\s+(?<slogan>\".+\"|\\S+))|" +
            "(-n\\s+(?<nickName>\".+\"|\\S+))))*\\s*"),
    REGISTER_RANDOM_PASSWORD("\\s*create\\s+user(\\s+((-u\\s+(?<username>\".+\"|\\S+))|" +
            "(-p\\s+(?<password>\".+\"|\\S+))|" +
            "(-e\\s+(?<email>\".+\"|\\S+))|(-s\\s+(?<slogan>\".+\"|\\S+))|" +
            "(-n\\s+(?<nickName>\".+\"|\\S+))))*\\s*"),
    PICK_QUESTION("\\s*question\\s+pick(\\s+((-q\\s+(?<questionNumber>\\d+))|" +
            "(-a\\s+(?<answer>\".+\"|\\S+))|(-c\\s+(?<answerConfirm>\".+\"|\\S+))))*\\s*"),
    //TODO: back
    ENTER_LOGIN_MENU("\\s*enter\\s+login\\s+menu\\s*"),

    // LoginMenu:
    LOGIN("\\s*user\\s+login(\\s+((-u\\s+(?<username>\".+\"|\\S+))|" +
                  "(-p\\s+(?<password>\".+\"|\\S+))|(?<stayLoggedIn>--stay-logged-in)))*\\s*"),
    FORGOT_PASSWORD("\\s*forgot\\s+my\\s+password\\s+-u\\s+(?<username>\".+\"|\\S+)\\s*"),
    ENTER_REGISTER_MENU("\\s*enter\\s+register\\s+menu\\s*"),

    // MainMenu:
    ENTER_PROFILE_MENU("\\s*enter\\s+profile\\s+menu\\s*"),
    // TODO: fill
    START_GAME(""),
    LOGOUT("\\s*logout\\s*"),

    // ProfileMenu:
    CHANGE_USERNAME("\\s*profile\\s+change\\s+-u\\s+(?<username>\".+\"|\\S+)\\s*"),
    CHANGE_NICKNAME("\\s*profile\\s+change\\s+-n\\s+(?<nickname>\".+\"|\\S+)\\s*"),
    CHANGE_PASSWORD("\\s*profile\\s+change\\s+password\\s+-o\\s+(?<oldPassword>\".+\"|\\S+)\\s+-n\\s+(?<newPassword>\".+\"|\\S+)\\s*"),
    CHANGE_EMAIL("\\s*profile\\s+change\\s+-e\\s+(?<email>\".+\"|\\S+)\\s*"),
    CHANGE_SLOGAN("\\s*profile\\s+change\\s+slogan\\s+-s\\s+(?<slogan>\".+\"|\\S+)\\s*"),
    REMOVE_SLOGAN("\\s*profile\\s+remove\\s+slogan\\s*"),
    DISPLAY_HIGHSCORE("\\s*profile\\s+display\\s+highscore\\s*"),
    DISPLAY_RANK("\\s*profile\\s+display\\s+rank\\s*"),
    DISPLAY_SLOGAN("\\s*profile\\s+display\\s+slogan\\s*"),
    DISPLAY_PROFILE("\\s*profile\\s+display\\s*"),

    // GameMenu:
    SHOW_MAP("\\s*show\\s+map(\\s+((-x(?<x>\\d+))|(-y(?<y>\\d+))))+\\s*"),
    SHOW_POPULARITY("\\s*show\\s+popularity\\s*"),
    SHOW_POPULARITY_FACTORS("\\s*show\\s+popularity\\s+factors\\s*"),
    SHOW_FOOD_LIST("\\s*show\\s+food\\s+list\\s*"),
    FOOD_RATE("\\s*food\\s+rate\\s+-r\\s+(?<rateNumber>\\d+)\\s*"),
    FOOD_RATE_SHOW("\\s*food\\s+rate\\s+show\\s*"),
    TAX_RATE("\\s*tax\\s+rate\\s+-r\\s+(?<rateNumber>\\d+)\\s*"),
    TAX_RATE_SHOW("\\s*tax\\s+rate\\s+show\\s*"),
    FEAR_RATE("\\s*fear\\s+rate\\s+-r\\s+(?<rateNumber>\\d+)\\s*"),
    SHOW_FEAR_RATE("\\s*show\\s+fear\\s+rate\\s*"),
    DROP_BUILDING("\\s*dropbuilding(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+)))+\\s+-type\\s+(?<type>.+)\\s*"),
    CREATE_UNIT("\\s*createunit(\\s+((-t\\s+(?<type>/\".+\"|\\S+))|(-c\\s+(?<count>\\d+)))\\s*"),

    SELECT_BUILDING("\\s*select\\s+building(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+)))+\\s*"),
    SELECT_UNIT("\\s*select\\s+unit(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+)))+\\s*"),
    ENTER_TRADE_MENU("\\s*enter\\s+trade\\s+menu\\s*"),
    ENTER_SHOP_MENU("\\s*enter\\s+shop\\s+menu\\s*"),

    SET_TEXTURE("\\s*settexture\\s+(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+))|(-t\\s+(?<type>\".+\"|\\S+)))+\\s*"),
    SET_RECTANGLE_TEXTURES("\\s*settexture\\s+(\\s+((-x1\\s+(?<x1>\\d+))|(-y1\\s+(?<y1>\\d+))|" +
            "(-x2\\s+(?<x2>\\d+))|(-y2\\s+(?<y2>\\d+))|(-t\\s+(?<type>\".+\"|\\S+)))+\\s*"),
    CLEAR_TEXTURE("\\s*clear(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+)))+\\s*"),
    DROP_ROCK("\\s*drop\\s+rock\\s+(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+))|(-d(?<direction>\".+\"|\\S+)))+\\s*"),
    DROP_TREE("\\s*droptree(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+))|(-t(?<type>\".+\"|\\S+)))+\\s*"),
    // TODO: fill
    END_GAME(""),

    // MapMenu:
    MOVE_THE_MAP("map(\\s+(((?<up>up)(\\s+(?<upDistance>\\d+))?)|((?<down>down)(\\s+(?<downDistance>\\d+))?)|" +
            "((?<left>left)(\\s+(?<leftDistance>\\d+))?)|((?<right>right)(\\s+(?<rightDistance>\\d+))?)))+\\s*"),
    SHOW_DETAILS("\\s*show\\s+details(\\s+((-x(?<x>\\d+))|(-y(?<y>\\d+))))+\\s*"),
//    BACK_GAME_MENU("\\s*back\\s+to\\s+game\\s*"),

    // ShopMenu:
    SHOW_PRICE_LIST("\\s*show\\s+price\\s+list\\s*"),
    BUY_ITEM("\\s*buy(\\s+((-i(?<itemName>\".+\"|\\S+))|(-a(?<itemAmount>\\d+))))+\\s*"),
    SELL_ITEM("\\s*sell(\\s+((-i(?<itemName>\".+\"|\\S+))|(-a(?<itemAmount>\\d+))))+\\s*"),
    // TODO: handle back
//    BACK_GAME_MENU("\\s*back\\s+to\\s+game\\s*"),

    // TradeMenu:
    REQUEST_TRADE("\\s*trade(\\s+((-t(?<resourceType>\".+\"|\\S+))|(-a(?<resourceAmount>\\d+))|" +
            "(-p(?<price>\\d+))|(-m(?<message>\".+\"|\\S+))))+\\s*"),
    SHOW_TRADE_LIST("\\s*trade\\s+list\\s*"),
    ACCEPT_TRADE("\\s*trade\\s+accept(\\s+((-i(?<id>\\d+))|(-m(?<message>\".+\"|\\S+))))+\\s*"),
    SHOW_TRADE_HISTORY("\\s*trade\\s+history\\s*"),
    BACK_GAME_MENU("\\s*back\\s+to\\s+game\\s*"),

    // UnitMenu:
    MOVE_UNIT("\\s*move\\s+unit\\s+to(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+)))+\\s*"),
    PATROL_UNIT("\\s*patrol\\s+unit(\\s+((-x1\\s+(?<x1>\\d+))|(-y1\\s+(?<y1>\\d+))|" +
                        "(-x2\\s+(?<x2>\\d+))|(-y2\\s+(?<y2>\\d+)))+\\s*"),
    SET_UNIT("\\s*set(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+))|(-s\\s+(?<state>\".+\"|\\S+)))+\\s*"),
    ATTACK("\\s*attack(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+))|(?<isEarth>-e)))+\\s*"),//if isEarth is not null we have earth attack
    POUR_OIL("\\s*pour\\s+oil\\s+-d\\s+(?<direction>\".+\"|\\S+)\\s*"),
    DIG_TUNNEL("\\s*dig\\s+tunnel\\s+(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+)))+\\s*"),
    BUILD_EQUIPMENT("\\s*build\\s+-q\\s+(?<equipmentName>\".+\"|\\S+)\\s*"),
    DISBAND_UNIT("\\s*disband\\s*"),

    // BuildingMenu:
    REPAIR("\\s*repair\\s*");

    private final String regex;

    Command(String regex){
        this.regex = regex;
    }

    public Matcher getMatcher(String input){
        Matcher matcher = Pattern.compile(this.regex).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
