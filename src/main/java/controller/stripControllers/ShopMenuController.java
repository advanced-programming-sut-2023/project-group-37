package controller.stripControllers;

import controller.MultiMenuFunctions;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.game.Color;
import model.game.Game;
import model.game.Item;
import model.game.ItemCategory;
import view.enums.Message;
import view.menus.RegisterMenu;

import java.util.Objects;

public class ShopMenuController {
    private Pane stripPane;
    private Label amount;
    private static Game game;


    public ShopMenuController(Pane stripPane) {
        this.stripPane = stripPane;
    }

    public static void setGame(Game game) {
        ShopMenuController.game = game;
    }

    public void run() {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();

        ImageView resources = MultiMenuFunctions.getImageView("/Image/Button/resources.png", 70);
        ImageView weapons = MultiMenuFunctions.getImageView("/Image/Button/weapons.png", 70);
        ImageView foods = MultiMenuFunctions.getImageView("/Image/Button/food.jpg", 70);

        resources.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                resources();
            }
        });

        weapons.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                weapons();
            }
        });

        foods.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                foods();
            }
        });

        resources.setLayoutX(150);
        resources.setLayoutY(15);
        weapons.setLayoutX(250);
        weapons.setLayoutY(15);
        foods.setLayoutX(350);
        foods.setLayoutY(15);

        stripPane.getChildren().add(resources);
        stripPane.getChildren().add(weapons);
        stripPane.getChildren().add(foods);

    }

    private void resources() {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();

        int i = 0;
        for (Item item : Item.getAllItems()) {
            if (item.getCategory().equals(ItemCategory.RESOURCES)) {
                ImageView imageView = MultiMenuFunctions.getImageView(item.getImageUrl(), 60);
                imageView.setLayoutX(100 + (i * 75));
                imageView.setLayoutY(15);

                imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        goToItem(item);
                    }
                });

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
                imageView.setLayoutX(100 + (i * 75));
                imageView.setLayoutY(15);

                imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        goToItem(item);
                    }
                });

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
                imageView.setLayoutX(100 + (i * 75));
                imageView.setLayoutY(15);

                imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        goToItem(item);
                    }
                });

                stripPane.getChildren().add(imageView);
                i++;
            }
        }
        addBackButton();

    }

    private void goToItem(Item item) {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();

        ImageView imageView = MultiMenuFunctions.getImageView(item.getImageUrl(), 65);

        imageView.setLayoutX(130);
        imageView.setLayoutY(15);

        this.amount = new Label(Integer.toString(game.getCurrentTurnGovernment().getItemAmount(item)));
        amount.setStyle("-fx-font-size: 20");

        if (Integer.toString(game.getCurrentTurnGovernment().getItemAmount(item)).equals("0"))
            amount.setTextFill(Color.RED.getColor());
        else amount.setTextFill(Color.GREEN.getColor());

        amount.setLayoutX(230);
        amount.setLayoutY(15);


        stripPane.getChildren().add(imageView);
        stripPane.getChildren().add(amount);

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
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switch (item.getCategory()) {
                    case FOODS -> foods();
                    case WEAPONS -> weapons();
                    case RESOURCES -> resources();
                }
            }
        });
    }

    private void addBuySellButtons(Item item) {
        ImageView buy = new ImageView(new Image(Objects.requireNonNull(RegisterMenu.class.getResource("/Image/Button/buy.png"))
                .toExternalForm(), 324 * 0.7, 63 * 0.7, false, false));
        buy.setLayoutX(330);
        buy.setLayoutY(10);

        buy.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                buy(item);
            }
        });

        ImageView sell = new ImageView(new Image(Objects.requireNonNull(RegisterMenu.class.getResource("/Image/Button/sell.png"))
                .toExternalForm(), 324 * 0.7, 63 * 0.7, false, false));
        sell.setLayoutX(330);
        sell.setLayoutY(50);

        sell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                sell(item);
            }
        });

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

    private void updateLabel(Item item) {
        this.amount.setText(Integer.toString(game.getCurrentTurnGovernment().getItemAmount(item)));
    }
}
