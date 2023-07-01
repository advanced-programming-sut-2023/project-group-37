package controller.stripControllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.game.Game;

public class PopularityMenuController {
    private static Game game;
    private final Pane stripPane;
    private Slider fearSlider;

    public PopularityMenuController(Pane stripPane) {
        this.stripPane = stripPane;
        fearSlider = new Slider();
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
        Label fear = new Label("Fear rate : " + game.getCurrentTurnGovernment().getFearRate());

        fear.setLayoutX(600);
        fear.setLayoutY(15);
        fear.setStyle("-fx-font-size: 20");
        fear.setBackground(Background.fill(Color.WHITE));

        stripPane.getChildren().add(fear);

        fearSlider.setMin(-5);
        fearSlider.setMax(5);

        fearSlider.setLayoutX(620);
        fearSlider.setLayoutY(55);

        fearSlider.setShowTickLabels(true);
        fearSlider.setShowTickMarks(true);

        fearSlider.setStyle("-fx-font-size: 20");

        fearSlider.setValue(0);
        stripPane.getChildren().add(fearSlider);

        fearSlider.valueProperty().addListener((observableValue, number, t1) -> {
            fearSlider.setValue(t1.intValue());
            game.getCurrentTurnGovernment().setFearRate((int) fearSlider.getValue());
            fear.setText("Fear rate : " + game.getCurrentTurnGovernment().getFearRate());
        });

    }


    private void setTaxes() {
        Label tax = new Label("Tax rate : " + game.getCurrentTurnGovernment().getTaxRate());

        tax.setLayoutX(480);
        tax.setLayoutY(15);
        tax.setStyle("-fx-font-size: 20");
        tax.setBackground(Background.fill(Color.WHITE));


        stripPane.getChildren().add(tax);

        tax.setOnMouseClicked(mouseEvent -> {
            TextInputDialog textInputDialog = new TextInputDialog("Enter Tax Rate : ");
            textInputDialog.setHeaderText("Tax Rate");
            textInputDialog.showAndWait();
            try {
                int taxRate = Integer.parseInt(textInputDialog.getEditor().getText());
                game.getCurrentTurnGovernment().setTaxRate(taxRate);
                tax.setText("Tax rate : " + game.getCurrentTurnGovernment().getTaxRate());
            } catch (NumberFormatException ignored) {

            }

        });

    }


    private void setPopularity() {
        Label popularity = new Label("Popularity : " + game.getCurrentTurnGovernment().getPopularity());

        popularity.setLayoutX(300);
        popularity.setLayoutY(15);
        popularity.setStyle("-fx-font-size: 20");
        popularity.setBackground(Background.fill(Color.WHITE));

        stripPane.getChildren().add(popularity);
    }

}
