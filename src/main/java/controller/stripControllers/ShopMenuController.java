package controller.stripControllers;

import controller.MultiMenuFunctions;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.game.Game;
import model.game.Item;
import model.game.ItemCategory;

public class ShopMenuController {
    private Pane stripPane;
    private Game game;

    public ShopMenuController(Pane stripPane) {
        this.stripPane = stripPane;
    }

    public void run() {
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
    }

    private void goToItem(Item item) {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();

        ImageView imageView = MultiMenuFunctions.getImageView(item.getImageUrl(), 65);

        imageView.setLayoutX(100);
        imageView.setLayoutY(15);

        Label amount = new Label("null");
        amount.setLayoutX(150);
        amount.setLayoutY(15);


        stripPane.getChildren().add(imageView);
        stripPane.getChildren().add(amount);
    }
}
