package controller;


import java.io.File;
import java.util.*;

import controller.viewControllers.GameMenuController;
import controller.viewControllers.MapController;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.buildings.Building;
import model.buildings.DefensiveBuilding;
import model.game.Texture;
import model.game.Tile;
import model.game.Map;
import model.people.MilitaryUnit;
import view.enums.Error;
import view.menus.LoginMenu;

public class MultiMenuFunctions {

    public static boolean checkUsernameNotOK(String username) {
        return !username.matches("[A-Za-z0-9_]+") || username.length() < 4;
    }

    public static boolean checkPasswordNotOK(String password) {
        return !(password.matches(".*[A-Z].*") && password.matches(".*[a-z].*") &&
                password.matches(".*[0-9].*") && password.length() > 5 && password.matches(".*[^a-zA-Z0-9]+.*"));
    }

    public static boolean checkEmailNotOK(String email) {
        return !email.matches("[A-Za-z0-9_.]+@[a-zA-Z0-9_]+\\.[A-Za-z0-9_.]+");
    }

    public static void setBackground(Pane pane, String filePath) {
        pane.setBackground(new Background(new BackgroundImage(new Image(Objects.requireNonNull(
                LoginMenu.class.getResource("/Image/Background/" + filePath)).toExternalForm(),
                pane.getPrefWidth(), pane.getPrefHeight() + 10, false, false), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
    }

    public static void setTileImage(Tile tile, Image image) {
        MapController.getInstance().getMainMap().add(new ImageView(new Image(image.getUrl(),
                20, 20, false, false)), tile.getLocationX(), tile.getLocationY());
    }

    public static void initializePasswordFields(TextField passwordShow, PasswordField passwordField,
                                                Label passwordError, CheckBox showPassword) {

        passwordField.textProperty().addListener((observable, oldText, newText) -> {
                passwordShow.setText(newText);
                if (newText.isEmpty())
                    passwordError.setText(Error.NECESSARY_FIELD.toString());
                else passwordError.setText("");
        });

        passwordShow.textProperty().addListener((observable, oldText, newText) ->
                passwordField.setText(newText));

        passwordShow.managedProperty().bind(showPassword.selectedProperty());
        passwordShow.visibleProperty().bind(showPassword.selectedProperty());

        passwordField.managedProperty().bind(showPassword.selectedProperty().not());
        passwordField.visibleProperty().bind(showPassword.selectedProperty().not());
    }

    public static void initializePasswordFieldsWithConfirm(
            TextField passwordShow, TextField passwordConfirmShow,
            PasswordField passwordField, PasswordField passwordConfirmField, Label passwordError,
            Label passwordConfirmError, CheckBox showPassword) {

        passwordShow.textProperty().addListener((observable, oldText, newText) ->
                passwordField.setText(newText));

        passwordField.textProperty().addListener((observable, oldText, newText) -> {
            passwordShow.setText(newText);

            if (MultiMenuFunctions.checkPasswordNotOK(newText))
                passwordError.setText(Error.WEAK_PASSWORD.toString());

            else if (newText.isEmpty())
                passwordError.setText(Error.NECESSARY_FIELD.toString());

            else passwordError.setText("");
        });

        passwordConfirmShow.textProperty().addListener((observable, oldText, newText) ->
                passwordConfirmField.setText(newText));

        passwordConfirmField.textProperty().addListener((observable, oldText, newText) -> {
            passwordConfirmShow.setText(newText);

            if (!passwordField.getText().equals(newText))
                passwordConfirmError.setText(Error.INCOMPATIBLE_PASSWORDS.toString());

            else if (newText.isEmpty())
                passwordConfirmError.setText(Error.NECESSARY_FIELD.toString());

            else passwordConfirmError.setText("");
        });

        passwordShow.managedProperty().bind(showPassword.selectedProperty());
        passwordShow.visibleProperty().bind(showPassword.selectedProperty());

        passwordField.managedProperty().bind(showPassword.selectedProperty().not());
        passwordField.visibleProperty().bind(showPassword.selectedProperty().not());

        passwordConfirmShow.managedProperty().bind(showPassword.selectedProperty());
        passwordConfirmShow.visibleProperty().bind(showPassword.selectedProperty());

        passwordConfirmField.managedProperty().bind(showPassword.selectedProperty().not());
        passwordConfirmField.visibleProperty().bind(showPassword.selectedProperty().not());
    }

    public static ArrayList<File> getAllImageFilesFromFolder(File directory) {
        //Get all the files from the folder
        File[] allFiles = directory.listFiles();
        if (allFiles == null || allFiles.length == 0) {
            throw new RuntimeException("No files present in the directory: " + directory.getAbsolutePath());
        }

        //Set the required image extensions here.
        List<String> supportedImageExtensions = Arrays.asList("jpg", "png", "gif", "webp");

        //Filter out only image files
        ArrayList<File> acceptedImages = new ArrayList<>();
        for (File file : allFiles) {
            //Parse the file extension
            String fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            //Check if the extension is listed in the supportedImageExtensions
            if (supportedImageExtensions.stream().anyMatch(fileExtension::equalsIgnoreCase)) {
                //Add the image to the filtered list
                acceptedImages.add(file);
            }
        }

        //Return the filtered images
        return acceptedImages;
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

    // Route finding:

    private static ArrayList<Tile> setNextNumber(ArrayList<Tile> targets, int nextNumber, boolean[][] tilePassability, Map map) {
        ArrayList<Tile> result = new ArrayList<>();
        Tile nextTile;
        int x, y;

        for (Tile tile : targets) {
            x = tile.getLocationX();
            y = tile.getLocationY();

            nextTile = map.getTileByLocation(x + 1, y);
            if (nextTile != null) {
                if (nextTile.number == 0 && tilePassability[x + 1][y]) {
                    nextTile.number = nextNumber;
                    result.add(nextTile);
                }
            }
            nextTile = map.getTileByLocation(x - 1, y);
            if (nextTile != null) {
                if (nextTile.number == 0 && tilePassability[x - 1][y]) {
                    nextTile.number = nextNumber;
                    result.add(nextTile);
                }
            }
            nextTile = map.getTileByLocation(x, y + 1);
            if (nextTile != null) {
                if (nextTile.number == 0 && tilePassability[x][y + 1]) {
                    nextTile.number = nextNumber;
                    result.add(nextTile);
                }
            }
            nextTile = map.getTileByLocation(x, y - 1);
            if (nextTile != null) {
                if (nextTile.number == 0 && tilePassability[x][y - 1]) {
                    nextTile.number = nextNumber;
                    result.add(nextTile);
                }
            }
        }
        return result;
    }

    private static boolean isNeighbor(Tile neighbor, Tile tile) {
        if (tile.getLocationX() - neighbor.getLocationX() == 1 || neighbor.getLocationX() - tile.getLocationX() == 1)
            return tile.getLocationY() == neighbor.getLocationY();

        if (tile.getLocationY() - neighbor.getLocationY() == 1 || neighbor.getLocationY() - tile.getLocationY() == 1)
            return tile.getLocationX() == neighbor.getLocationX();

        return false;
    }

    private static Tile getNeighbor(Tile tile, ArrayList<Tile> targets) {
        for (Tile neighbor : targets) {

            if (isNeighbor(neighbor, tile))
                return neighbor;
        }
        System.out.println(tile.getLocationX() + " " + tile.getLocationY() + " " + tile.number);
        return null;
    }

    public static LinkedList<Tile> routeFinder(Tile origin, Tile destination, Map map) {
        map.resetNumbers();
        boolean[][] tilePassability = map.getTilesPassability();

        if (!destination.isPassable())
            return routeFinder2(origin, destination, map);

        ArrayList<ArrayList<Tile>> targets = new ArrayList<>();
        targets.add(new ArrayList<>());
        targets.get(0).add(origin);

        int number = 1;
        while (targets.get(number - 1).size() > 0 && !targets.get(number - 1).contains(destination)) {
            targets.add(setNextNumber(targets.get(targets.size() - 1), number, tilePassability, map));
            number++;
        }

        if (targets.get(targets.size() - 1).size() == 0) {
            return routeFinder2(origin, destination, map);
        }

        return getFinalRoute(origin, destination, targets);
    }

    private static LinkedList<Tile> routeFinder2(Tile origin, Tile destination, Map map) {
        boolean canUnitsGoUp = true;

        for (MilitaryUnit militaryUnit : origin.getMilitaryUnits()) {
            if (!militaryUnit.canGoUp()) {
                canUnitsGoUp = false;
                break;
            }
        }
        if (!canUnitsGoUp)
            return null;

        map.resetNumbers();
        boolean[][] tilePassability = map.getTilesPassability();
        Building building;
        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                if ((building = map.getTileByLocation(i, j).getBuilding()) != null) {
                    if (building instanceof DefensiveBuilding) {
                        tilePassability[i][j] = ((DefensiveBuilding) building).canBeReached();
                    }
                }
            }
        }
        if (!tilePassability[destination.getLocationX()][destination.getLocationY()])
            return null;

        ArrayList<ArrayList<Tile>> targets = new ArrayList<>();
        targets.add(new ArrayList<>());
        targets.get(0).add(origin);

        int number = 1;
        while (targets.get(number - 1).size() > 0 && !targets.get(number - 1).contains(destination)) {
            targets.add(setNextNumber(targets.get(targets.size() - 1), number, tilePassability, map));
            number++;
        }
        if (targets.get(targets.size() - 1).size() == 0)
            return null;

        return getFinalRoute(origin, destination, targets);
    }

    private static LinkedList<Tile> getFinalRoute(Tile origin, Tile destination, ArrayList<ArrayList<Tile>> targets) {
        LinkedList<Tile> result = new LinkedList<>();
        result.add(0, origin);
        result.add(1, destination);

        Tile preTile;
        Tile tile = destination;
        int index = targets.size() - 2;
        while (index > 0) {
            preTile = getNeighbor(tile, targets.get(index));
            result.add(1, preTile);
            tile = preTile;
            index--;
        }

        return result;
    }

    public static double distance(Tile tile1, Tile tile2) {
        int x1 = tile1.getLocationX(), y1 = tile1.getLocationY(), x2 = tile2.getLocationX(), y2 = tile2.getLocationY();
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public static Tile getMiddle(Tile tile1, Tile tile2, Map map) {
        int x = (tile2.getLocationX() - tile1.getLocationX()) / 2;
        int y = (tile2.getLocationY() - tile1.getLocationY()) / 2;
        return map.getTileByLocation(x, y);
    }

    public static Tile getNearestPassableTileByLocation(Tile tile, Map map) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (map.getPassabilityByLocation(tile.getLocationX() + i, tile.getLocationY() + j))
                    return map.getTileByLocation(tile.getLocationX() + i, tile.getLocationY() + j);
            }
        }
        return tile;
    }

    public static Tile getNearestMoatTileByLocation(Tile tile, Map map) {
        Tile target;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((target = map.getTileByLocation(tile.getLocationX() + i, tile.getLocationY() + j)).getTexture() == Texture.MOAT)
                    return target;
            }
        }
        return tile;
    }
}
