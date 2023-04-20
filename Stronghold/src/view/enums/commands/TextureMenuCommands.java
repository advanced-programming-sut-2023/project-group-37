package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TextureMenuCommands {

    SET_TEXTURE("\\s*settexture\\s+(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+))|(-t\\s+(?<type>\".+\"|\\S+)))+\\s*"),
    SET_RECTANGLE_TEXTURES("\\s*settexture\\s+(\\s+((-x1\\s+(?<x1>\\d+))|(-y1\\s+(?<y1>\\d+))|" +
            "(-x2\\s+(?<x2>\\d+))|(-y2\\s+(?<y2>\\d+))|(-t\\s+(?<type>\".+\"|\\S+)))+\\s*"),
    CLEAR_TEXTURE("\\s*clear(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+)))+\\s*"),
    DROP_ROCK("\\s*drop\\s+rock\\s+(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+))|(-d(?<direction>\".+\"|\\S+)))+\\s*"),
    DROP_TREE("\\s*droptree(\\s+((-x\\s+(?<x>\\d+))|(-y\\s+(?<y>\\d+))|(-t(?<type>\".+\"|\\S+)))+\\s*");

    private final String regex;
    TextureMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, TextureMenuCommands textureMenuCommands) {
        Matcher matcher = Pattern.compile(command).matcher(textureMenuCommands.regex);
        if (matcher.matches())
            return matcher;

        return null;
    }
}
