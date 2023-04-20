package model.enums;

import java.util.ArrayList;

public enum Slogans {
    ;
    private final String slogan;
    private static final ArrayList<Slogans> allSlogans = new ArrayList<>();

    Slogans(String slogan) {
        this.slogan = slogan;
    }
    @Override
    public String toString() {
        return this.slogan;
    }
}