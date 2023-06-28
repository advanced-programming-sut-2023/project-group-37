package controller.stripControllers;

import controller.MultiMenuFunctions;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ShopMenuController {
    private Pane stripPane;

    public ShopMenuController(Pane stripPane) {
        this.stripPane = stripPane;
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

    }

    private void weapons() {

    }
}
