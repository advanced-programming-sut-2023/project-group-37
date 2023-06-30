package controller.stripControllers;

import controller.MapController;
import controller.MultiMenuFunctions;
import controller.StripPaneController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.DefensiveBuilding;
import model.game.Game;
import model.game.Government;
import model.game.Item;
import model.people.MilitaryMachineType;
import model.people.Troop;
import model.people.TroopType;
import view.enums.Message;
import view.enums.PopUp;

import java.util.HashMap;
import java.util.Objects;

public class BuildingMenuController {
    private static Game game;
    private final Pane stripPane;
    private final StripPaneController stripPaneController;
    private final int sizeOfImages;
    private Building currentBuilding;
    private final MapController mapController;
    private final HashMap<TroopType, ImageView> troopTypeImages;
    private final HashMap<MilitaryMachineType, ImageView> militaryMachineTypeImages;

    public BuildingMenuController(Pane stripPane, StripPaneController stripPaneController) {
        this.stripPane = stripPane;
        this.stripPaneController = stripPaneController;
        this.sizeOfImages = 50;
        this.mapController = MapController.getInstance();
        this.militaryMachineTypeImages = new HashMap<>();

        this.troopTypeImages = new HashMap<>();

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
        BuildingMenuController.game = game;
    }

    public void run(Building building) {
        this.currentBuilding = building;

        Label nameLabel = new Label();
        nameLabel.setTextFill(Color.DARKRED);
        nameLabel.setFont(new Font(20));
        Label healthLabel = new Label("Health:");
        healthLabel.setTextFill(Color.GREEN);
        ProgressBar healthBar = new ProgressBar(1);
        double health = building.getHitpoints() / (double) building.getMaxHitpoints();
        healthBar.setProgress(health);

        nameLabel.setLayoutY(2);
        nameLabel.setLayoutX(10);
        healthLabel.setLayoutY(10);
        healthLabel.setLayoutX(160);
        healthBar.setLayoutY(10);
        healthBar.setLayoutX(210);

        this.stripPane.getChildren().add(nameLabel);
        this.stripPane.getChildren().add(healthLabel);
        this.stripPane.getChildren().add(healthBar);

        if (building instanceof DefensiveBuilding defensiveBuilding) {
            nameLabel.setText(defensiveBuilding.getDefensiveType().getName());
            Button repairButton = new Button("Repair");
            repairButton.setLayoutY(40);
            repairButton.setLayoutX(10);

            repairButton.setDisable(defensiveBuilding.getMaxHitpoints() == defensiveBuilding.getHitpoints());
            repairButton.setOnMouseClicked((MouseEvent mouseEvent) -> {
                Message message = this.repair(defensiveBuilding);
                if (message == Message.REPAIR_SUCCESS) {
                    repairButton.setDisable(true);
                }
            });
            this.stripPane.getChildren().add(repairButton);
        }

        else {
            nameLabel.setText(building.getType().getName());
            switch (building.getType()) {
                case BARRACKS -> uploadTroopImages(BuildingType.BARRACKS);
                case MERCENARY_POST -> uploadTroopImages(BuildingType.MERCENARY_POST);
                case ENGINEER_GUILD -> uploadTroopImages(BuildingType.ENGINEER_GUILD);
                case TUNNELER_GUILD -> uploadTroopImages(BuildingType.TUNNELER_GUILD);
                case MARKET -> this.stripPaneController.runShopMenu();
            }
        }
    }

    private void uploadTroopImages(BuildingType buildingType) {
        int i = 0;
        for (TroopType troopType : TroopType.values()) {
            if (troopType.getTrainingCamp() == buildingType) {
                ImageView imageView = this.getImageView(troopType);
                imageView.setOnMouseClicked((MouseEvent mouseEvent) -> {
                    PopUp popUp = this.createUnit(troopType);
                    if (popUp != null)
                        popUp.show();
                });
                imageView.setLayoutX(100 + i * (sizeOfImages + 50));
                imageView.setLayoutY(30);
                // todo : set on
                this.stripPane.getChildren().add(imageView);

                Label cost = new Label(String.valueOf(troopType.getCost()));
                cost.setLayoutY(30 + sizeOfImages);
                cost.setLayoutX(100 + i * (sizeOfImages + 50));
                this.stripPane.getChildren().add(cost);

                ImageView goldImageView = MultiMenuFunctions.getImageView("/Image/Item/gold.png", 20);

                goldImageView.setLayoutY(cost.getLayoutY());
                goldImageView.setLayoutX(cost.getLayoutX() + 20);
                this.stripPane.getChildren().add(goldImageView);

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
        int stoneNeededToRepair = (int)
                (1 - (double) (defensiveBuilding.getHitpoints() / defensiveBuilding.getMaxHitpoints())) *
                defensiveBuilding.getType().getRawMaterialUsesForSecond();

        if (game.getCurrentTurnGovernment().removeItem(Item.STONE, stoneNeededToRepair))
            return Message.NOT_ENOUGH_STONE;

        MapController.getInstance().updateDetails();

        defensiveBuilding.setHitpoints(defensiveBuilding.getMaxHitpoints());
        return Message.REPAIR_SUCCESS;
    }

    public PopUp createUnit(TroopType troopType) {
        int goldCost = troopType.getCost();
        Item armor = troopType.getArmor(), weapon = troopType.getWeapon();

        if (goldCost > game.getCurrentTurnGovernment().getGold())
            return PopUp.NOT_ENOUGH_GOLD;

        if (armor != null) {
            if (1 > game.getCurrentTurnGovernment().getItemAmount(armor))
                return PopUp.NOT_ENOUGH_RESOURCE;
        }

        if (weapon != null) {
            if (1 > game.getCurrentTurnGovernment().getItemAmount(weapon))
                return PopUp.NOT_ENOUGH_RESOURCE;
        }

        game.getCurrentTurnGovernment().removeItem(armor, 1);
        game.getCurrentTurnGovernment().removeItem(weapon, 1);
        game.getCurrentTurnGovernment().setGold(game.getCurrentTurnGovernment().getGold() - goldCost);

        Troop troop = new Troop(game.getCurrentTurnGovernment(), troopType,
                this.mapController.getTileByLocation(currentBuilding.getLocation().getLocationX(),
                currentBuilding.getLocation().getLocationY() + 2));
        game.getCurrentTurnGovernment().getMilitaryUnits().add(troop);

        this.mapController.getTileByLocation(currentBuilding.getLocation().getLocationX(),
                currentBuilding.getLocation().getLocationY() + 2).updateImage();

        MapController.getInstance().updateDetails();
        return null;
    }
}
