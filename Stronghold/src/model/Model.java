package model;

public class Model {
    private static Map currentMap;

    public static Map getCurrentMap() {
        return currentMap;
    }

    public static void setCurrentMap(Map currentMap) {
        Model.currentMap = currentMap;
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

}
