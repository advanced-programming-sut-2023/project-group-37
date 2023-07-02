package controller.stripControllers;

import controller.MultiMenuFunctions;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.game.Game;
import model.game.Tile;
import model.people.*;
import view.enums.Message;
import connection.PopUp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class UnitMenuController {
    private static Game game;
    private final Pane stripPane;
    private final int sizeOfImages;
    private ArrayList<Tile> selectedTiles;
    private ArrayList<MilitaryUnit> militaryUnits;
    private final HashMap<TroopType, ImageView> troopTypeImages;
    private final HashMap<MilitaryMachineType, ImageView> militaryMachineTypeImages;

    public UnitMenuController(Pane stripPane) {
        this.stripPane = stripPane;
        this.sizeOfImages = 50;

        this.troopTypeImages = new HashMap<>();
        this.militaryMachineTypeImages = new HashMap<>();

        for (TroopType troopType : TroopType.values()) {
            ImageView imageView = MultiMenuFunctions.getImageView(troopType.getDownPaneImage(), this.sizeOfImages);

            this.troopTypeImages.put(troopType, imageView);
        }

        for (MilitaryMachineType militaryMachineType : MilitaryMachineType.values()) {
            ImageView imageView = MultiMenuFunctions.getImageView(militaryMachineType.getDownPaneImage(), this.sizeOfImages);
            this.militaryMachineTypeImages.put(militaryMachineType, imageView);
        }
    }

    public static void setGame(Game game) {
        UnitMenuController.game = game;
    }

    private ImageView getImageView(TroopType troopType) {
        return this.troopTypeImages.get(troopType);
    }

    private ImageView getImageView(MilitaryMachineType militaryMachineType) {
        return this.militaryMachineTypeImages.get(militaryMachineType);
    }

    public void run(ArrayList<Tile> selectedTiles) {
        this.militaryUnits = new ArrayList<>();
        for (Tile tile : selectedTiles) {
            for (MilitaryUnit militaryUnit : tile.getMilitaryUnits()) {
                if (militaryUnit.getLoyalty() == game.getCurrentTurnGovernment())
                    this.militaryUnits.add(militaryUnit);
            }
        }

        this.selectedTiles = selectedTiles;
        this.showUnitsInStripPane();
    }

    private void showUnitsInStripPane() {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();

        HashMap<TroopType, Integer> troopTypeCounts = new HashMap<>();
        HashMap<MilitaryMachineType, Integer> militaryMachineTypeCounts = new HashMap<>();

        for (MilitaryUnit militaryUnit : this.militaryUnits) {
            if (militaryUnit instanceof  Troop troop)
                troopTypeCounts.put(troop.getType(), troopTypeCounts.getOrDefault(troop.getType(), 0) + 1);

            else if (militaryUnit instanceof MilitaryMachine militaryMachine)
                militaryMachineTypeCounts.put(militaryMachine.getType(),
                        militaryMachineTypeCounts.getOrDefault(militaryMachine.getType(), 0) + 1);
        }

        int i = 0;
        for (TroopType troopType : troopTypeCounts.keySet()) {
            ImageView imageView = this.getImageView(troopType);
            if (imageView == null)
                continue;

            imageView.setLayoutX(70 + i * (sizeOfImages + 50));
            imageView.setLayoutY(20);

            imageView.setOnMouseClicked((MouseEvent mouseEvent) -> this.selectUnitWithType(troopType));

            Label label = new Label(String.valueOf(troopTypeCounts.get(troopType)));
            label.setLayoutY(30 + sizeOfImages);
            label.setLayoutX(115 + i * (sizeOfImages + 50) - sizeOfImages/2f);

            this.stripPane.getChildren().add(label);
            this.stripPane.getChildren().add(imageView);

            i++;
        }

        for (MilitaryMachineType militaryMachineType : militaryMachineTypeCounts.keySet()) {
            ImageView imageView = this.getImageView(militaryMachineType);
            if (imageView == null)
                continue;

            imageView.setLayoutX(70 + i * (sizeOfImages + 50));
            imageView.setLayoutY(20);

            imageView.setOnMouseClicked((MouseEvent mouseEvent) -> this.selectUnitWithType(militaryMachineType));

            Label label = new Label(String.valueOf(militaryMachineTypeCounts.get(militaryMachineType)));
            label.setLayoutY(30 + sizeOfImages);
            label.setLayoutX(115 + i * (sizeOfImages + 50) - sizeOfImages / 2f);

            this.stripPane.getChildren().add(label);
            this.stripPane.getChildren().add(imageView);

            i++;
        }
    }

    private void selectUnitWithType(TroopType troopType) {
        for (int i = this.militaryUnits.size() - 1; i > -1; i--) {
            if (militaryUnits.get(i) instanceof Troop troop) {
                if (troop.getType() != troopType)
                    militaryUnits.remove(i);
            }
            else this.militaryUnits.remove(i);
        }

        TextInputDialog textInputDialog = new TextInputDialog(String.valueOf(this.militaryUnits.size()));
        textInputDialog.setHeaderText("Number of units");
        textInputDialog.showAndWait();

        int count = militaryUnits.size();
        try {
            count = Integer.parseInt(textInputDialog.getEditor().getText());
        }
        catch (Exception ignored) {
        }

        if (militaryUnits.size() > count)
            this.militaryUnits.subList(count, militaryUnits.size()).clear();

        this.showUnitsInStripPane();
    }

    private void selectUnitWithType(MilitaryMachineType militaryMachineType) {
        for (int i = this.militaryUnits.size() - 1; i > -1; i--) {
            if (militaryUnits.get(i) instanceof MilitaryMachine militaryMachine) {
                if (militaryMachine.getType() != militaryMachineType)
                    militaryUnits.remove(i);
            }
            else this.militaryUnits.remove(i);
        }

        TextInputDialog textInputDialog = new TextInputDialog(String.valueOf(this.militaryUnits.size()));
        textInputDialog.setHeaderText("Number of units");
        textInputDialog.showAndWait();

        int count = militaryUnits.size();
        try {
            count = Integer.parseInt(textInputDialog.getEditor().getText());
        }
        catch (Exception ignored) {
        }
        if (militaryUnits.size() > count)
            this.militaryUnits.subList(count, militaryUnits.size()).clear();

        this.showUnitsInStripPane();
    }

    private boolean isUnitOfType(TroopType troopType) {
        for (MilitaryUnit militaryUnit : this.militaryUnits) {
            if (militaryUnit instanceof Troop troop) {
                if (troop.getType() != troopType)
                    return false;
            } else
                return false;
        }
        return true;
    }

    private boolean isUnitOfType(MilitaryMachineType militaryMachineType) {
        for (MilitaryUnit militaryUnit : this.militaryUnits) {
            if (militaryUnit instanceof MilitaryMachine militaryMachine) {
                if (militaryMachine.getType() != militaryMachineType)
                    return false;
            } else
                return false;
        }
        return true;
    }

    public PopUp move(Tile target) {
        if (this.militaryUnits == null || this.militaryUnits.size() == 0)
            return PopUp.NO_UNIT_SELECTED;

        LinkedList<Tile> routeCheck = MultiMenuFunctions.routeFinder(
                this.militaryUnits.get(0).getLocation(), target, game.getMap());
        if (routeCheck == null)
            return PopUp.CANT_MOVE_THERE;

        for (MilitaryUnit militaryUnit : this.militaryUnits) {
            LinkedList<Tile> route = MultiMenuFunctions.routeFinder(militaryUnit.getLocation(), target, game.getMap());
            militaryUnit.setRoute(route);
        }

        return PopUp.WILL_MOVE;
    }

    public PopUp attack(Tile target) {
        if (this.militaryUnits == null || this.militaryUnits.size() == 0)
            return PopUp.NO_UNIT_SELECTED;

        boolean isEnemy = false;
        if (target.getBuilding() != null)
            if (target.getBuilding().getLoyalty() != game.getCurrentTurnGovernment())
                isEnemy = true;

        if (!isEnemy) {
            if (target.getMilitaryUnits().size() != 0) {
                for (MilitaryUnit militaryUnit : target.getMilitaryUnits())
                    if (militaryUnit.getLoyalty() != game.getCurrentTurnGovernment()) {
                        isEnemy = true;
                        break;
                    }
            }
        }

        if (!isEnemy)
            return PopUp.NO_ENEMY;

        double maxRange = 0;
        for (MilitaryUnit militaryUnit : this.militaryUnits) {
            if (militaryUnit instanceof Troop troop) {
                if (maxRange < troop.getRange())
                    maxRange = troop.getRange();
            }
            else if (militaryUnit instanceof MilitaryMachine militaryMachine) {
                if (maxRange < militaryMachine.getRange())
                    maxRange = militaryMachine.getRange();
            }
        }

        double distance = MultiMenuFunctions.distance(this.militaryUnits.get(0).getLocation(), target);
        if (distance > maxRange)
            return PopUp.OUT_OF_RANGE;

        for (MilitaryUnit militaryUnit : this.militaryUnits) {
            if (militaryUnit.getRange() >= distance)
                militaryUnit.setTarget(target);
        }

        return PopUp.WILL_ATTACK;
    }

    public void deselect() {
        this.militaryUnits = null;
    }

    public Message stop() {
        for (MilitaryUnit militaryUnit : this.militaryUnits) {
            militaryUnit.stop();
        }
        return Message.UNIT_STOPPED;
    }

//    public String patrolUnit(int x1, int y1, int x2, int y2) {
////        Tile tile1 = this.currentGame.getMap().getTileByLocation(x1, y1);
////        Tile tile2 = this.currentGame.getMap().getTileByLocation(x2, y2);
////
////        if (tile1 == null || tile2 == null)
////            return Message.ADDRESS_OUT_OF_BOUNDS.toString();
////
////        if (tile1 != this.currentLocation) {
////            LinkedList<Tile> route = MultiMenuFunctions.routeFinder(this.currentLocation, tile1, this.currentGame.getMap());
////            for (MilitaryUnit militaryUnit : this.currentUnit)
////                militaryUnit.setRoute(route);
////        }
////
////        LinkedList<Tile> patrolRoute = MultiMenuFunctions.routeFinder(tile1, tile2, this.currentGame.getMap());
////        LinkedList<Tile> patrolRouteCopy ;
////
////        for (MilitaryUnit militaryUnit : this.currentUnit) {
////            patrolRouteCopy = new LinkedList<>(patrolRoute);
////            militaryUnit.setPatrol(patrolRouteCopy);
////        }
////
////        return "Patrol target set to :" + x1 + ", " + y1 + "and " + x2 + ", " +y2;
////    }
////
////
////    public String setUnitState(String state) {
////        MilitaryUnitStance stance = MilitaryUnitStance.getByState(state);
////        if (stance == null)
////            return Message.INVALID_STANCE.toString();
////
////        for (MilitaryUnit militaryUnit : this.currentUnit) {
////            militaryUnit.setStance(stance);
////        }
////        return Message.STANCE_IS_SET.toString();
////    }
////
////    public Message fillContainer(Matcher matcher) {
////        if (!this.isUnitOfType(TroopType.ENGINEER))
////            return Message.UNIT_NOT_ENGINEER;
////
////        Building building;
////        if ((building = this.currentGovernment.getUniqueBuilding(BuildingType.OIL_SMELTER)) == null)
////            return Message.NO_OIL_SMELTER;
////
////        // TODO:
////
////        return null;
////    }


//    public String pourOil(Matcher matcher) {
//
//        return null;
//    }
//
//    public Message digTunnel(Matcher matcher) {
//        if (matcher.group("x") == null || matcher.group("y") == null)
//            return Message.EMPTY_FIELD;
//
//        int x = Integer.parseInt(matcher.group("x"));
//        int y = Integer.parseInt(matcher.group("y"));
//
//        if (this.currentGame.getMap().getTileByLocation(x, y) == null)
//            return Message.ADDRESS_OUT_OF_BOUNDS;
//
//        if (!this.isUnitOfType(TroopType.TUNNELER))
//            return Message.UNIT_NOT_TUNNELER;
//
//        Tile location = this.currentGame.getMap().getTileByLocation(x, y);
//        if (location.getBuilding() == null)
//            return Message.CANNOT_DIG_TUNNEL_THERE;
//
//        else if (((DefensiveBuilding) location.getBuilding()).getDefensiveType() != DefensiveBuildingType.WALL &&
//                ((DefensiveBuilding) location.getBuilding()).getDefensiveType() != DefensiveBuildingType.LOOKOUT_TOWER &&
//                ((DefensiveBuilding) location.getBuilding()).getDefensiveType() != DefensiveBuildingType.PERIMETER_TOWER &&
//                ((DefensiveBuilding) location.getBuilding()).getDefensiveType() != DefensiveBuildingType.DEFENCE_TOWER)
//            return Message.CANNOT_DIG_TUNNEL_THERE;
//
//        if (MultiMenuFunctions.distance(this.currentLocation, location) > TroopType.TUNNELER.getRange() + 0.2)
//            return Message.TUNNEL_TOO_FAR_FROM_ENEMY;
//
//        this.currentLocation.setBuilding(new Building(this.currentGovernment, this.currentLocation,
//                BuildingType.TUNNEL_ENTRANCE));
//
//        for (MilitaryUnit unit : this.currentUnit)
//            unit.setTarget(location);
//
//        return Message.TUNNEL_DIG_SUCCESSFUL;
//    }
//
//    public Message buildEquipment(Matcher matcher) {
//        if (!this.isUnitOfType(TroopType.ENGINEER))
//            return Message.UNIT_NOT_ENGINEER;
//
//        MilitaryMachineType type;
//        if ((type = MilitaryMachineType.getMilitaryMachineTypeByName(MultiMenuFunctions.deleteQuotations(matcher.group("type")))) == null)
//            return Message.INVALID_MACHINE_TYPE;
//
//        if (this.currentLocation.getBuilding() != null &&
//                !(this.currentLocation.getBuilding() instanceof DefensiveBuilding &&
//                        type == MilitaryMachineType.FIRE_BALLISTA && (
//                        ((DefensiveBuilding) this.currentLocation.getBuilding()).getDefensiveType() ==
//                                DefensiveBuildingType.SQUARE_TOWER ||
//                                ((DefensiveBuilding) this.currentLocation.getBuilding()).getDefensiveType() ==
//                                        DefensiveBuildingType.ROUND_TOWER)))
//            return Message.CANNOT_BUILD_MACHINE_HERE;
//
//        if (this.currentLocation.getBuilding() == null)
//            this.currentLocation.setBuilding(new SiegeTent(this.currentGovernment, this.currentLocation, type));
//        else {
//            MilitaryMachine machine = new MilitaryMachine(this.currentGovernment, type, this.currentLocation);
//            this.currentLocation.getMilitaryUnits().add(machine);
//            this.currentGovernment.getMilitaryUnits().add(machine);
//            int counter = 0;
//            for (int i = this.currentUnit.size() - 1; i >= 0; i--) {
//                this.currentGovernment.getMilitaryUnits().remove(this.currentUnit.get(i));
//                this.currentLocation.getMilitaryUnits().remove(this.currentUnit.get(i));
//                machine.assignOperator((Troop) this.currentUnit.get(i));
//                counter++;
//                if (counter == 2)
//                    break;
//            }
//        }
//        return Message.CONSTRUCTING_SIEGE_EQUIPMENT;
//    }
//
//    public Message disbandUnit() {
//        for (int i = this.currentLocation.getMilitaryUnits().size() - 1; i >= 0; i--) {
//            MilitaryUnit unit = this.currentLocation.getMilitaryUnits().get(i);
//            this.currentGovernment.getMilitaryUnits().remove(unit);
//            this.currentLocation.getMilitaryUnits().remove(i);
//            this.currentGovernment.addPeasant(unit instanceof MilitaryMachine ?
//                    ((MilitaryMachine) unit).getType().getOperatorsNeeded() : 1);
//        }
//        return Message.DISBAND_SUCCESSFUL;
//    }
//
//    public Message digMoat(Matcher matcher) {
//        if (matcher.group("x") == null || matcher.group("y") == null)
//            return Message.EMPTY_FIELD;
//
//        int x = Integer.parseInt(matcher.group("x"));
//        int y = Integer.parseInt(matcher.group("y"));
//
//        Tile destination = this.currentGame.getMap().getTileByLocation(x, y);
//        if (destination == null)
//            return Message.ADDRESS_OUT_OF_BOUNDS;
//
//        for (MilitaryUnit unit : this.currentUnit)
//            if (unit instanceof MilitaryMachine || !((Troop) unit).getType().canDigMoat())
//                return Message.UNIT_CANNOT_DIG_MOAT;
//
//        Tile target = MultiMenuFunctions.getNearestPassableTileByLocation(destination, this.currentGame.getMap());
//        LinkedList<Tile> route;
//        if ((route = MultiMenuFunctions.routeFinder(this.currentLocation, target, this.currentGame.getMap())) == null)
//            return Message.NO_ROUTS_FOUND;
//        for (MilitaryUnit unit : this.currentUnit) {
//            unit.setRoute(route);
//            unit.setMoatTarget(destination);
//        }
//
//        return Message.SUCCESS;
//    }
//
//    public Message cancelDigMoat(Matcher matcher) {
//        if (matcher.group("x") == null || matcher.group("y") == null)
//            return Message.EMPTY_FIELD;
//
//        int x = Integer.parseInt(matcher.group("x"));
//        int y = Integer.parseInt(matcher.group("y"));
//
//        Tile destination = this.currentGame.getMap().getTileByLocation(x, y);
//        if (destination == null)
//            return Message.ADDRESS_OUT_OF_BOUNDS;
//
//        for (MilitaryUnit unit : this.currentUnit)
//            if (unit.hasMoatTarget()) {
//                unit.cancelMoatTarget();
//                unit.setRoute(null);
//            }
//
//        return Message.CANCEL_DIG_MOAT_SUCCESS;
//    }
//
//    public Message fillMoat(Matcher matcher) {
//        if (matcher.group("x") == null || matcher.group("y") == null)
//            return Message.EMPTY_FIELD;
//
//        int x = Integer.parseInt(matcher.group("x"));
//        int y = Integer.parseInt(matcher.group("y"));
//
//        Tile destination = this.currentGame.getMap().getTileByLocation(x, y);
//        if (destination == null)
//            return Message.ADDRESS_OUT_OF_BOUNDS;
//
//        for (MilitaryUnit unit : this.currentUnit)
//            if (unit instanceof MilitaryMachine || !((Troop) unit).getType().canDigMoat())
//                return Message.UNIT_CANNOT_DIG_MOAT;
//
//        Tile target = MultiMenuFunctions.getNearestPassableTileByLocation(destination, this.currentGame.getMap());
//        LinkedList<Tile> route;
//        if ((route = MultiMenuFunctions.routeFinder(this.currentLocation, target, this.currentGame.getMap())) == null)
//            return Message.NO_ROUTS_FOUND;
//
//        LinkedList<Tile> copyRoute;
//        for (MilitaryUnit unit : this.currentUnit) {
//            copyRoute = new LinkedList<>(route);
//            unit.setRoute(copyRoute);
//            unit.setMoatTarget(destination);
//        }
//
//        return Message.SUCCESS;
//    }
}
