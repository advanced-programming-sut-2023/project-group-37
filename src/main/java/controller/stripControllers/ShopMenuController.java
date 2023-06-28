package controller.stripControllers;

import controller.MultiMenuFunctions;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.game.Government;
import model.game.Item;
import model.game.ItemCategory;

public class ShopMenuController {
    private Pane stripPane;
    private Government currentGovernment;

    public ShopMenuController(Pane stripPane) {
        this.stripPane = stripPane;
        currentGovernment = null;
    }

    public void run() {
        ImageView resources = MultiMenuFunctions.getImageView("/Image/Item/resources.png", 70);
        ImageView weapons = MultiMenuFunctions.getImageView("/Image/Item/weapons.png", 70);

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

        resources.setLayoutX(150);
        resources.setLayoutY(15);
        weapons.setLayoutX(250);
        weapons.setLayoutY(15);

        stripPane.getChildren().add(resources);
        stripPane.getChildren().add(weapons);

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

    private void goToItem(Item item) {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();

        ImageView imageView = MultiMenuFunctions.getImageView(item.getImageUrl(), 65);

        imageView.setLayoutX(100);
        imageView.setLayoutY(15);

        Label amount = new Label(Integer.toString(currentGovernment.getItemAmount(item)));
        amount.setLayoutX(100);
        amount.setLayoutY(10);


        stripPane.getChildren().add(imageView);
        stripPane.getChildren().add(amount);
    }
}
