package model.game;

public enum Color {
    RED("\033[41m"),
    CYAN("\033[46m"),
    BLUE("\033[44m"),
    YELLOW("\033[43m"),
    GREEN("\033[42m"),
    WHITE("\033[47m"),
    BROWN("\033[45m"),
    BLACK("\033[40m");

    private final String string;

    Color(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return this.string;
    }
}
