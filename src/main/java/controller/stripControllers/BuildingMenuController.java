package controller.stripControllers;

import controller.MultiMenuFunctions;
import javafx.scene.layout.Pane;
import model.buildings.Building;
import model.game.Item;
import model.people.Troop;
import model.people.TroopType;
import view.enums.Message;

import java.util.regex.Matcher;

public class BuildingMenuController {
    private final Pane stripPane;
    private final int sizeOfImage;

    public BuildingMenuController(Pane stripPane) {
        this.stripPane = stripPane;
        this.sizeOfImage = 60;
    }

    public void run(Building building) {

    }

//    public String createUnit(Matcher matcher) {
//        if (matcher.group("type") == null || matcher.group("count") == null)
//            return Message.EMPTY_FIELD.toString();
//
//        int count = Integer.parseInt(matcher.group("count"));
//
//        // todo : what about machines ?
//
//        TroopType troopType = TroopType.getTroopTypeByName(MultiMenuFunctions.deleteQuotations(matcher.group("type")));
//        if (troopType == null)
//            return Message.TYPE_NOT_EXISTS.toString();
//
//        if (this.currentBuilding.getType() != troopType.getTrainingCamp())
//            return Message.INCORRECT_BUILDING.toString();
//
//        if (count < 0)
//            return Message.INVALID_AMOUNT.toString();
//
//        // check amounts
//        int goldCost = troopType.getCost() * count;
//        Item armor = troopType.getArmor(), weapon = troopType.getWeapon();
//
//        if (goldCost > currentGovernment.getGold())
//            return Message.NOT_ENOUGH_GOLD.toString();
//
//        if (armor != null) {
//            if (count > currentGovernment.getItemAmount(armor))
//                return Message.NOT_ENOUGH_RESOURCE.toString();
//        }
//
//        if (weapon != null) {
//            if (count > currentGovernment.getItemAmount(weapon))
//                return Message.NOT_ENOUGH_RESOURCE.toString();
//        }
//
//        currentGovernment.removeItem(armor, count);
//        currentGovernment.removeItem(weapon, count);
//        currentGovernment.setGold(currentGovernment.getGold() - goldCost);
//
//        Troop troop;
//        for (int i = 0; i < count; i++) {
//            troop = new Troop(this.currentGovernment, troopType, currentBuilding.getLocation());
//            this.currentGovernment.getMilitaryUnits().add(troop);
//            currentBuilding.getLocation().addMilitaryUnit(troop);
//        }
//        return Message.CREATE_UNIT_SUCCESSFUL.toString();
//    }
}
