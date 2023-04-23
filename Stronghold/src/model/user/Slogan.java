package model.user;

import java.util.ArrayList;

public enum Slogan {
    ;
    private final String slogan;
    private static final ArrayList<Slogan> allSlogans = new ArrayList<>();

    Slogan(String slogan) {
        this.slogan = slogan;
    }

    @Override
    public String toString() {
        return this.slogan;
    }
}