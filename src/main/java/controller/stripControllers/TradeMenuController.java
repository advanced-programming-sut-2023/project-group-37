package controller.stripControllers;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.game.Game;
import model.game.Government;

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

        showGovernments();

    }

    private void showGovernments() {
        Label l = new Label("Governments : ");
        l.setStyle("-fx-font-size: 20");
        l.setLayoutX(20);
        l.setLayoutY(10);
        stripPane.getChildren().add(l);

        int i = 0;
        for (Government government : game.getGovernments()) {
            if (!government.equals(game.getCurrentTurnGovernment())) {
                Label label = new Label(government.getUser().getUsername());
                label.setLayoutX(170 + (i * 80));
                label.setLayoutY(30);
                label.setStyle("-fx-font-size: 20");
                label.setTextFill(Color.BLUE);
                stripPane.getChildren().add(label);
                label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        gotoGovernment(government);
                    }
                });
                i++;
            }
        }
    }

    private void gotoGovernment(Government government) {

    }
}
