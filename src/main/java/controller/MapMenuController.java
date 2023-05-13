package controller;

import model.game.Game;
import model.game.Government;
import model.game.Map;
import model.game.Tile;
import model.people.MilitaryMachine;
import model.people.MilitaryUnit;
import model.people.Troop;
import view.enums.Message;

import java.util.regex.Matcher;

public class MapMenuController {
    private Game game;
    private Map map;
    private Government government;
    private int currentX;
    private int currentY;

    public void setGovernment(Government government) {
        this.government = government;
    }

    public void setGame(Game game) {
        this.game = game;
        this.map = game.getMap();
    }

    public Tile[][] showMap(int x, int y) {
        currentX = x;
        currentY = y;
        map.setTilesState();
        int size = map.getSize();

        int minX = x - 30;
        int maxX = x + 30;
        int minY = y - 30;
        int maxY = y + 30;

        if (minX < 0)
            minX = 0;
        if (maxX > size - 1)
            maxX = size - 1;
        if (minY < 0)
            minY = 0;
        if (maxY > size - 1)
            maxY = size - 1;

        Tile[][] result = new Tile[maxX - minX][maxY - minY];

        for (int i = minX; i < maxX; i++) {
            for (int j = minY; j < maxY; j++) {
                result[i - minX][j - minY] = map.getTileByLocation(i, j);
            }
        }
        return result;
    }

    public Tile[][] moveMap(Matcher matcher) {
        int up = 0, down = 0, left = 0, right = 0;
        if (matcher.group("up") != null) {
            if (matcher.group("upDistance") == null)
                up = 1;
            else up = Integer.parseInt(matcher.group("upDistance"));
        }
        if (matcher.group("down") != null) {
            if (matcher.group("downDistance") == null)
                down = 1;
            else down = Integer.parseInt(matcher.group("downDistance"));
        }
        if (matcher.group("left") != null) {
            if (matcher.group("leftDistance") == null)
                left = 1;
            else left = Integer.parseInt(matcher.group("leftDistance"));
        }
        if (matcher.group("right") != null) {
            if (matcher.group("rightDistance") == null)
                right = 1;
            else right = Integer.parseInt(matcher.group("rightDistance"));
        }
        int x = currentX + right - left, y = currentY + up - down;
        if (map.getTileByLocation(x, y) == null)
            return null;

        return showMap(x, y);
    }

    public String showDetails(Matcher matcher) {
        if (matcher.group("x") == null || matcher.group("y") == null)
            return Message.EMPTY_FIELD.toString();

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Tile tile;

        if ((tile = map.getTileByLocation(x, y)) == null)
            return Message.ADDRESS_OUT_OF_BOUNDS.toString();

        StringBuilder details = new StringBuilder();
        details.append("The texture: ").append(tile.getTexture().name()).append("\n");

        if(tile.getBuilding() != null)
            details.append("You have the building (").append(tile.getBuilding().getType().getName()).append(") here!\n");
        else
            details.append("You have no buildings here!\n");

        if(tile.getMilitaryUnits().size() == 0)
            details.append("You have no troops here!\n");
        else{
            int troopCounter = 1;
            int machineCounter = 1;
            for (MilitaryUnit militaryUnit : tile.getMilitaryUnits()) {
                if(militaryUnit instanceof Troop) {
                    details.append("Troop ").append(troopCounter).append(": ").append(((Troop) militaryUnit).getType().name()).append("\n");
                    troopCounter++;
                }
                else if(militaryUnit instanceof MilitaryMachine){
                    details.append("Machine ").append(machineCounter).append(": ").append(((MilitaryMachine) militaryUnit).getType().name()).append("\n");
                    machineCounter++;
                }
            }
        }

        return details.toString().trim();

    }

}
