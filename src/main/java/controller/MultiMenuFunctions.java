package controller;


import java.util.ArrayList;
import model.game.Tile;

public class MultiMenuFunctions {

    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    public static String deleteQuotations(String string) {

        if (string == null)
            return "";

        StringBuilder stringBuilder = new StringBuilder(string);
        if (string.matches("\".+\"")) {

            stringBuilder.deleteCharAt(0);
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);

            string = stringBuilder.toString();
        }
        return string;
    }

    public static ArrayList<Tile> routeFinder(Tile origin, Tile destination) {
        int rightDistance = destination.getX() - origin.getX();
        int upDistance = destination.getY() - origin.getY();

        return null;
        //todo
    }
}
