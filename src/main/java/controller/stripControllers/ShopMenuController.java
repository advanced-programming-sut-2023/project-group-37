package controller.stripControllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import view.menus.RegisterMenu;

import java.util.Objects;

public class ShopMenuController {
    private Pane stripPane;

    public ShopMenuController(Pane stripPane) {
        this.stripPane = stripPane;
    }

    public void run() {
        ImageView resources = new ImageView(new Image(Objects.requireNonNull(RegisterMenu.class.getResource
                ("/Image/Item/resources.png")).toExternalForm()));
        ImageView weapons = new ImageView(new Image(Objects.requireNonNull(RegisterMenu.class.getResource
                ("/Image/Item/weapons.png")).toExternalForm()));
//        resources.setFitHeight();
//        resources.setFitWidth();

        stripPane.getChildren().add(resources);
        stripPane.getChildren().add(weapons);

    }
}
