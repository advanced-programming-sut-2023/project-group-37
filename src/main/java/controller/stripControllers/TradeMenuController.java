package controller.stripControllers;

import javafx.scene.layout.Pane;
import model.game.Game;
import view.menus.GameMenu;

public class TradeMenuController {
    private final Pane stripPane;
    private final int sizeOfImage;
    private static Game game;

    public TradeMenuController(Pane stripPane) {
        this.stripPane = stripPane;
        this.sizeOfImage = 0;
    }

    public static void setGame(Game game) {
        TradeMenuController.game = game;
    }

    public void run() {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();


    }
}
