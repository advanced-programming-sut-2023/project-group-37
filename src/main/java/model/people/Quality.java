package model.people;

public enum Quality {
    LOW(0.5), MEDIUM(1), HIGH(2);

    private final double rate;

    Quality(double rate) {
        this.rate = rate;
    }

    public static int getHitpointsByQuality(Quality quality){
        // TODO: change value of 10!
        return (int)quality.rate * 10;
    }

    public static int getAttackingDamageByQuality(Quality quality){
        // TODO: change value of 10!
        return (int)quality.rate * 10;
    }

    public static int getDefencingDamageByQuality(Quality quality){
        // TODO: change value of 10!
        return (int)quality.rate * 10;
    }

    public static int getSpeedByQuality(Quality quality){
        // TODO: change value of 10!
        return (int)quality.rate * 10;
    }
}
