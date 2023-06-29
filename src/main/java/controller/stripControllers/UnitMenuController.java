package controller.stripControllers;

import controller.MultiMenuFunctions;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.game.Game;
import model.game.Government;
import model.game.Tile;
import model.people.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class UnitMenuController {
    private static Game game;
    private static Government currentGovernment;
    private final Pane stripPane;
    private final int sizeOfImages;
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
        UnitMenuController.currentGovernment = game.getCurrentTurnGovernment();
    }

    private ImageView getImageView(TroopType troopType) {
        return this.troopTypeImages.get(troopType);
    }

    private ImageView getImageView(MilitaryMachineType militaryMachineType) {
        return this.militaryMachineTypeImages.get(militaryMachineType);
    }

    public void run(ArrayList<Tile> selectedTiles) {
        this.militaryUnits = new ArrayList<>();
        for (Tile tile : selectedTiles)
            this.militaryUnits.addAll(tile.getMilitaryUnits());

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
        this.showUnitsInStripPane();
    }

    public void Move(Tile target) {
        for (MilitaryUnit militaryUnit : this.militaryUnits) {
            LinkedList<Tile> route = MultiMenuFunctions.routeFinder(militaryUnit.getLocation(), target, game.getMap());
            militaryUnit.setRoute(route);
        }
    }

    public void attack(Tile target) {

    }
}
