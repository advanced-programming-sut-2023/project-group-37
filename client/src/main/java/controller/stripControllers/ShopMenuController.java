package controller.stripControllers;

import controller.MapController;
import controller.MultiMenuFunctions;
import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.buildings.BuildingType;
import model.game.GameColor;
import model.game.Game;
import model.game.Item;
import model.game.ItemCategory;
import view.enums.Message;
import view.enums.PopUp;
import view.menus.RegisterMenu;

import java.util.Objects;

public class ShopMenuController {
    private final Pane stripPane;
    private TradeMenuController tradeMenuController;
    private Label amount;
    private Label gold;
    private static Game game;


    public ShopMenuController(Pane stripPane, TradeMenuController tradeMenuController) {
        this.stripPane = stripPane;
        this.tradeMenuController = tradeMenuController;
    }

    public static void setGame(Game game) {
        ShopMenuController.game = game;
    }

    public void run() {
        if (game.getCurrentTurnGovernment().getUniqueBuilding(BuildingType.MARKET) == null) {
            PopUp.MARKET_NOT_EXISTS.show();
            return;
        }

        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();

        ImageView resources = MultiMenuFunctions.getImageView("/Image/Button/resources.png", 70);
        ImageView weapons = MultiMenuFunctions.getImageView("/Image/Button/weapons.png", 70);
        ImageView foods = MultiMenuFunctions.getImageView("/Image/Button/food.jpg", 70);
        ImageView scale = MultiMenuFunctions.getImageView("/Image/Button/scale.png", 70);

        resources.setOnMouseClicked(mouseEvent -> resources());

        weapons.setOnMouseClicked(mouseEvent -> weapons());

        foods.setOnMouseClicked(mouseEvent -> foods());

        scale.setOnMouseClicked(mouseEvent -> enterTrade());

        resources.setLayoutX(150);
        resources.setLayoutY(15);
        weapons.setLayoutX(250);
        weapons.setLayoutY(15);
        foods.setLayoutX(350);
        foods.setLayoutY(15);
        scale.setLayoutX(450);
        scale.setLayoutY(15);

        stripPane.getChildren().add(resources);
        stripPane.getChildren().add(weapons);
        stripPane.getChildren().add(foods);
        stripPane.getChildren().add(scale);

    }

    private void resources() {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();

        int i = 0;
        for (Item item : Item.getAllItems()) {
            if (item.getCategory().equals(ItemCategory.RESOURCES)) {
                ImageView imageView = MultiMenuFunctions.getImageView(item.getImageUrl(), 60);
                imageView.setLayoutX(100 + (i * 90));
                imageView.setLayoutY(15);

                imageView.setOnMouseClicked(mouseEvent -> goToItem(item));

                stripPane.getChildren().add(imageView);
                i++;
            }
        }
        addBackButton();
    }

    private void weapons() {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();

        int i = 0;
        for (Item item : Item.getAllItems()) {
            if (item.getCategory().equals(ItemCategory.WEAPONS)) {
                ImageView imageView = MultiMenuFunctions.getImageView(item.getImageUrl(), 60);
                imageView.setLayoutX(100 + (i * 90));
                imageView.setLayoutY(15);

                imageView.setOnMouseClicked(mouseEvent -> goToItem(item));

                stripPane.getChildren().add(imageView);
                i++;
            }
        }
        addBackButton();
    }

    private void foods() {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();

        int i = 0;
        for (Item item : Item.getAllItems()) {
            if (item.getCategory().equals(ItemCategory.FOODS)) {
                ImageView imageView = MultiMenuFunctions.getImageView(item.getImageUrl(), 60);
                imageView.setLayoutX(100 + i * (120));
                imageView.setLayoutY(15);

                imageView.setOnMouseClicked(mouseEvent -> goToItem(item));

                stripPane.getChildren().add(imageView);
                i++;
            }
        }
        addBackButton();

    }

    private void enterTrade() {
        this.tradeMenuController.run();
    }

    private void goToItem(Item item) {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();

        ImageView imageView = MultiMenuFunctions.getImageView(item.getImageUrl(), 65);

        imageView.setLayoutX(130);
        imageView.setLayoutY(15);

        stripPane.getChildren().add(imageView);

        ImageView goldImage = MultiMenuFunctions.getImageView("/Image/Item/gold.png", 70);
        goldImage.setLayoutX(800);
        goldImage.setLayoutY(25);

        stripPane.getChildren().add(goldImage);

        addLabels(item);
        addBuySellButtons(item);
        addBackButton(item);
    }

    private void addBackButton() {
        ImageView back = MultiMenuFunctions.getImageView("/Image/Button/back1.png", 45);
        back.setLayoutX(30);
        back.setLayoutY(50);

        stripPane.getChildren().add(back);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                run();
            }
        });
    }

    private void addBackButton(Item item) {
        ImageView back = MultiMenuFunctions.getImageView("/Image/Button/back1.png", 45);
        back.setLayoutX(30);
        back.setLayoutY(50);

        stripPane.getChildren().add(back);
        back.setOnMouseClicked(mouseEvent -> {
            switch (item.getCategory()) {
                case FOODS -> foods();
                case WEAPONS -> weapons();
                case RESOURCES -> resources();
            }
        });
    }

    private void addBuySellButtons(Item item) {
        ImageView buy = new ImageView(new Image(Objects.requireNonNull(RegisterMenu.class.getResource("/Image/Button/buy.png"))
                .toExternalForm(), 324 * 0.7, 63 * 0.7, false, false));
        buy.setLayoutX(330);
        buy.setLayoutY(8);

        buy.setOnMouseEntered((MouseEvent mouseEvent) -> MapController.getInstance().getDownPane().setCursor(ImageCursor.HAND));
        buy.setOnMouseExited((MouseEvent mouseEvent) -> MapController.getInstance().getDownPane().setCursor(ImageCursor.DEFAULT));
        buy.setOnMouseClicked(mouseEvent -> buy(item));

        ImageView sell = new ImageView(new Image(Objects.requireNonNull(RegisterMenu.class.getResource("/Image/Button/sell.png"))
                .toExternalForm(), 324 * 0.7, 63 * 0.7, false, false));

        sell.setOnMouseEntered((MouseEvent mouseEvent) -> MapController.getInstance().getDownPane().setCursor(ImageCursor.HAND));
        sell.setOnMouseExited((MouseEvent mouseEvent) -> MapController.getInstance().getDownPane().setCursor(ImageCursor.DEFAULT));

        sell.setLayoutX(330);
        sell.setLayoutY(60);

        sell.setOnMouseClicked(mouseEvent -> sell(item));

        stripPane.getChildren().add(buy);
        stripPane.getChildren().add(sell);
    }

    private void buy(Item item) {
        if (game.getCurrentTurnGovernment().getGold() < item.getBuyCost()) {
            new Alert(Alert.AlertType.ERROR, Message.NOT_ENOUGH_GOLD.toString()).show();
            return;
        }

        if (game.getCurrentTurnGovernment().getFreeSpace(game.getCurrentTurnGovernment().getTargetRepository(item)) == 0) {
            new Alert(Alert.AlertType.ERROR, Message.NOT_ENOUGH_SPACE.toString()).show();
            return;
        }

        game.getCurrentTurnGovernment().buyItem(item, 1);
        updateLabel(item);
    }

    private void sell(Item item) {
        if (game.getCurrentTurnGovernment().getItemAmount(item) == 0) {
            new Alert(Alert.AlertType.ERROR, Message.NOT_ENOUGH_AMOUNT.toString()).show();
            return;
        }
        game.getCurrentTurnGovernment().sellItem(item, 1);
        updateLabel(item);
    }

    private void addLabels(Item item) {
        this.amount = new Label(Integer.toString(game.getCurrentTurnGovernment().getItemAmount(item)));
        amount.setStyle("-fx-font-size: 30");

        if (Integer.toString(game.getCurrentTurnGovernment().getItemAmount(item)).equals("0"))
            amount.setTextFill(GameColor.RED.getColor());
        else amount.setTextFill(GameColor.GREEN.getColor());

        amount.setLayoutX(230);
        amount.setLayoutY(15);

        stripPane.getChildren().add(amount);

        Label buyPrice = new Label(Integer.toString(item.getBuyCost()));
        buyPrice.setStyle("-fx-font-size: 20");
        buyPrice.setLayoutX(580);
        buyPrice.setLayoutY(15);

        stripPane.getChildren().add(buyPrice);
        buyPrice.toFront();


        Label sellPrice = new Label(Integer.toString(item.getSellCost()));
        sellPrice.setStyle("-fx-font-size: 20");
        sellPrice.setLayoutX(580);
        sellPrice.setLayoutY(60);

        stripPane.getChildren().add(sellPrice);
        sellPrice.toFront();


        this.gold = new Label(Integer.toString(game.getCurrentTurnGovernment().getGold()));
        gold.setStyle("-fx-font-size: 30");
        gold.setLayoutX(700);
        gold.setLayoutY(30);
        gold.setTextFill(GameColor.GREY.getColor());

        stripPane.getChildren().add(gold);
        gold.toFront();

    }

    private void updateLabel(Item item) {
        this.amount.setText(Integer.toString(game.getCurrentTurnGovernment().getItemAmount(item)));
        this.gold.setText(Integer.toString(game.getCurrentTurnGovernment().getGold()));
        MapController.getInstance().updateDetails();
    }
}
