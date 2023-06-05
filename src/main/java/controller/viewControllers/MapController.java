package controller.viewControllers;

import model.buildings.DefensiveBuilding;
import model.game.Game;
import model.game.Government;
import model.game.Map;
import model.game.Tile;
import model.people.MilitaryMachine;
import model.people.MilitaryUnit;
import model.people.Troop;
import view.enums.Message;

import java.util.regex.Matcher;

public class MapController {
    private static final MapController MAP_CONTROLLER;
    private Game game;
    private Map map;
    private Government currentGovernment;
    private int currentX;
    private int currentY;

    static {
        MAP_CONTROLLER = new MapController();
    }

    private MapController() {

    }

    public static MapController getInstance() {
        return MAP_CONTROLLER;
    }

    public void setCurrentGovernment(Government currentGovernment) {
        this.currentGovernment = currentGovernment;
    }

    public void setGame(Game game) {
        this.game = game;
        this.map = game.getMap();
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

        if(tile.hasBuilding()) {
            if (tile.getBuilding().getLoyalty() == currentGovernment) {
                if (tile.getBuilding() instanceof DefensiveBuilding defensiveBuilding)
                    details.append("you have the building (").append(defensiveBuilding.getDefensiveType().getName()).append(") here!\n");

                else
                    details.append("You have the building (").append(tile.getBuilding().getType().getName()).append(") here!\n");
            }
        }

        else
            details.append("You have no buildings here!\n");

        if(tile.getMilitaryUnits().size() == 0)
            details.append("You have no troops here!\n");
        else{
            int troopCounter = 1;
            int machineCounter = 1;
            for (MilitaryUnit militaryUnit : tile.getMilitaryUnits()) {
                if(militaryUnit instanceof Troop) {
                    if (militaryUnit.getLoyalty() == currentGovernment) {
                        details.append("Troop ").append(troopCounter).append(": ").append(((Troop) militaryUnit).getType().name()).append("\n");
                        troopCounter++;
                    }
                }
                else if(militaryUnit instanceof MilitaryMachine) {
                    if (militaryUnit.getLoyalty() == currentGovernment) {
                        details.append("Machine ").append(machineCounter).append(": ").append(((MilitaryMachine) militaryUnit).getType().name()).append("\n");
                        machineCounter++;
                    }
                }
            }
        }

        return details.toString().trim();

    }

}
