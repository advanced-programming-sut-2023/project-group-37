package model.enums;

import model.Model;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

public enum Slogans {
    SLOGAN_1("I shall have my revenge, in this life or the next"),
    SLOGAN_2("Give yourself more time to think about your next move"),
    SLOGAN_3("Try not to repeat the same moves as your opponent"),
    SLOGAN_4("Nothing is impossible, it just a matter of perspective"),
    SLOGAN_5("I most hung soon for souls"),
    SLOGAN_6("In every universe, there is a hero and a villain"),
    SLOGAN_7("I'm the best ; F the rest..."),
    SLOGAN_8("Now, Who thinks his arms are long enough to slap box?"),
    SLOGAN_9(""),
    SLOGAN_10("");

    private final String slogan;
    private static final ArrayList<Slogans> allSlogans = new ArrayList<>();

    Slogans(String slogan) {
        this.slogan = slogan;
    }

    static {
        allSlogans.addAll(Arrays.asList(Slogans.values()));
    }

    @Override
    public String toString() {
        return this.slogan;
    }

    public static Slogans getRandomSlogan() {
        SecureRandom random = new SecureRandom();

        int randomIndex = random.nextInt(allSlogans.size());
        return allSlogans.get(randomIndex);
    }
}