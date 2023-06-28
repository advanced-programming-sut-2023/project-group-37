package controller.stripControllers;

import controller.MultiMenuFunctions;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.game.Tile;
import model.people.*;

import java.util.ArrayList;
import java.util.HashMap;

public class UnitMenuController {
    private final Pane stripPane;
    private final int sizeOfImages;
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

//        for (MilitaryMachineType militaryMachineType : MilitaryMachineType.values()) {
//            ImageView imageView = MultiMenuFunctions.getImageView(militaryMachineType.getDownPaneImage(), this.sizeOfImages);
//            this.militaryMachineTypeImages.put(militaryMachineType, imageView);
//        } todo
    }

    private ImageView getImageView(TroopType troopType) {
        return this.troopTypeImages.get(troopType);
    }

    private ImageView getImageView(MilitaryMachineType militaryMachineType) {
        return this.militaryMachineTypeImages.get(militaryMachineType);
    }

    public void run(ArrayList<Tile> selectedTiles) {
        ArrayList<MilitaryUnit> militaryUnits = new ArrayList<>();
        for (Tile tile : selectedTiles)
            militaryUnits.addAll(tile.getMilitaryUnits());

        HashMap<TroopType, Integer> troopTypeCounts = new HashMap<>();
        HashMap<MilitaryMachineType, Integer> militaryMachineTypeCounts = new HashMap<>();

        for (MilitaryUnit militaryUnit : militaryUnits) {
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

            Label label = new Label(String.valueOf(troopTypeCounts.get(militaryMachineType)));
            label.setLayoutY(30 + sizeOfImages);
            label.setLayoutX(115 + i * (sizeOfImages + 50) - sizeOfImages / 2f);

            this.stripPane.getChildren().add(label);
            this.stripPane.getChildren().add(imageView);

            i++;
        }
    }
}
