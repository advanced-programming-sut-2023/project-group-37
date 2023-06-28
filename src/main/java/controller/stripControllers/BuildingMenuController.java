package controller.stripControllers;

import controller.MultiMenuFunctions;
import controller.StripPaneController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.DefensiveBuilding;
import model.game.Government;
import model.game.Item;
import model.people.MilitaryMachineType;
import model.people.Troop;
import model.people.TroopType;
import view.enums.Message;

import java.util.HashMap;
import java.util.regex.Matcher;

public class BuildingMenuController {
    private final Pane stripPane;
    private final StripPaneController stripPaneController;
    private final int sizeOfImages;
    private final Government currentGovernment;
    private final HashMap<TroopType, ImageView> troopTypeImages;
    private final HashMap<MilitaryMachineType, ImageView> militaryMachineTypeImages;

    public BuildingMenuController(Pane stripPane, StripPaneController stripPaneController) {
        this.stripPane = stripPane;
        this.stripPaneController = stripPaneController;
        this.sizeOfImages = 50;
        this.currentGovernment = null;
        this.militaryMachineTypeImages = new HashMap<>();

        this.troopTypeImages = new HashMap<>();

        for (TroopType troopType : TroopType.values()) {
            ImageView imageView = MultiMenuFunctions.getImageView(troopType.getDownPaneImage(), this.sizeOfImages);

            this.troopTypeImages.put(troopType, imageView);
        }

//        for (MilitaryMachineType militaryMachineType : MilitaryMachineType.values()) {
//            ImageView imageView = MultiMenuFunctions.getImageView(militaryMachineType.getDownPaneImage(), this.sizeOfImages);
//            this.militaryMachineTypeImages.put(militaryMachineType, imageView);
//        } todo
    }

    public void run(Building building) {
        Label healthLabel = new Label("Health");
        ProgressBar healthBar = new ProgressBar(1);
        double health = building.getHitpoints() / (double) building.getMaxHitpoints();
        healthBar.setProgress(health);
        if (health < 0.33)
            healthBar.setStyle("-fx-progress-color: red");
        else if (health < 0.66)
            healthBar.setStyle("-fx-progress-color: yellow");
        else healthBar.setStyle("-fx-progress-color: green");

        healthLabel.setLayoutY(10);
        healthLabel.setLayoutX(10);
        healthBar.setLayoutY(10);
        healthBar.setLayoutX(60);

        this.stripPane.getChildren().add(healthLabel);
        this.stripPane.getChildren().add(healthBar);

        if (building instanceof DefensiveBuilding defensiveBuilding) {
            Button repairButton = new Button();
            repairButton.setGraphic(new ImageView()); // todo
            repairButton.setOnMouseClicked((MouseEvent mouseEvent) -> {
                Message message = this.repair(defensiveBuilding);
                if (message == Message.REPAIR_SUCCESS) {
                    // todo : image
                    repairButton.setDisable(true);
                }
            });
        }

        switch (building.getType()) {
            case BARRACKS -> uploadTroopImages(BuildingType.BARRACKS);
            case MERCENARY_POST -> uploadTroopImages(BuildingType.MERCENARY_POST);
            case ENGINEER_GUILD -> uploadTroopImages(BuildingType.ENGINEER_GUILD);
            case TUNNELER_GUILD -> uploadTroopImages(BuildingType.TUNNELER_GUILD);
            case MARKET -> this.stripPaneController.runShopMenu();
        }
    }

    private void uploadTroopImages(BuildingType buildingType) {
        int i = 0;
        for (TroopType troopType : TroopType.values()) {
            if (troopType.getTrainingCamp() == buildingType) {
                ImageView imageView = this.getImageView(troopType);
                imageView.setLayoutX(100 + i * (sizeOfImages + 50));
                imageView.setLayoutY(30);
                // todo : set on
                this.stripPane.getChildren().add(imageView);

                i++;
            }
        }
    }

    private ImageView getImageView(TroopType troopType) {
        return this.troopTypeImages.get(troopType);
    }

    private ImageView getImageView(MilitaryMachineType militaryMachineType) {
        return this.militaryMachineTypeImages.get(militaryMachineType);
    }

    public Message repair(DefensiveBuilding defensiveBuilding) {
        if (!defensiveBuilding.getType().isRepairable())
            return Message.BUILDING_NOT_REPAIRABLE;

        int stoneNeededToRepair = (int)
                (1 - (double) (defensiveBuilding.getHitpoints() / defensiveBuilding.getMaxHitpoints())) *
                defensiveBuilding.getType().getRawMaterialUsesForSecond();

        if (!this.currentGovernment.removeItem(Item.STONE, stoneNeededToRepair))
            return Message.NOT_ENOUGH_STONE;

        defensiveBuilding.setHitpoints(defensiveBuilding.getMaxHitpoints());
        return Message.REPAIR_SUCCESS;
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
