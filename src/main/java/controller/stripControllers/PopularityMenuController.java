package controller.stripControllers;

import javafx.scene.layout.Pane;
import model.game.Game;

public class PopularityMenuController {
    private static Game game;
    private final Pane stripPane;

    public PopularityMenuController(Pane stripPane) {
        this.stripPane = stripPane;
    }

    public static void setGame(Game game) {
        PopularityMenuController.game = game;
    }

    public void run() {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();


    }
}
