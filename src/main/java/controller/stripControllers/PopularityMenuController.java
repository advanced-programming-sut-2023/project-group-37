package controller.stripControllers;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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

        setPopularity();
        setTaxes();
        setFearRate();

    }

    private void setFearRate() {
        Label Fear = new Label("Popularity : " + game.getCurrentTurnGovernment().getFearRate());

        Fear.setLayoutX(300);
        Fear.setLayoutY(15);
        Fear.setStyle("-fx-font-size: 20");
        Fear.setBackground(Background.fill(Color.WHITE));

        stripPane.getChildren().add(Fear);
    }

    private void setTaxes() {
        Label tax = new Label("Popularity : " + game.getCurrentTurnGovernment().getTaxRate());

        tax.setLayoutX(200);
        tax.setLayoutY(15);
        tax.setStyle("-fx-font-size: 20");
        tax.setBackground(Background.fill(Color.WHITE));

        stripPane.getChildren().add(tax);
    }

    private void setPopularity() {
        Label popularity = new Label("Popularity : " + game.getCurrentTurnGovernment().getPopularity());

        popularity.setLayoutX(100);
        popularity.setLayoutY(15);
        popularity.setStyle("-fx-font-size: 20");
        popularity.setBackground(Background.fill(Color.WHITE));

        stripPane.getChildren().add(popularity);
    }

}
