package controller.stripControllers;

import controller.MultiMenuFunctions;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.game.Game;

public class PopularityMenuController {
    private static Game game;
    private final Pane stripPane;
    private Slider fearSlider;
    private ImageView fearFace;
    private ImageView taxFace;
    private ImageView religionFace;
    private ImageView foodFace;


    public PopularityMenuController(Pane stripPane) {
        this.stripPane = stripPane;
        fearSlider = new Slider();
        fearFace = new ImageView();
        taxFace = new ImageView();
        religionFace = new ImageView();
        foodFace = new ImageView();
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
        setReligion();
        setFood();

    }

    private void setFood() {
        Label food = new Label("Food rate : " + game.getCurrentTurnGovernment().getFoodRate());
        food.setLayoutX(380);//60
        food.setLayoutY(15);//15

        stripPane.getChildren().add(food);

        food.setStyle("-fx-font-size: 20");
        food.setBackground(Background.fill(Color.WHITE));

        foodFace.setLayoutX(410);
        foodFace.setLayoutY(60);
        stripPane.getChildren().add(foodFace);

        updateFace();
    }

    private void setReligion() {
        Label religion = new Label("Religion rate : " + game.getCurrentTurnGovernment().getReligionPopularityRate());
        religion.setLayoutX(200);
        religion.setLayoutY(15);

        stripPane.getChildren().add(religion);

        religion.setStyle("-fx-font-size: 20");
        religion.setBackground(Background.fill(Color.WHITE));

        religionFace.setLayoutX(250);
        religionFace.setLayoutY(60);
        stripPane.getChildren().add(religionFace);

        updateFace();
    }

    private void setFearRate() {
        Label fear = new Label("Fear rate : " + game.getCurrentTurnGovernment().getFearRate());

        fear.setLayoutX(625);
        fear.setLayoutY(15);
        fear.setStyle("-fx-font-size: 20");
        fear.setBackground(Background.fill(Color.WHITE));

        stripPane.getChildren().add(fear);

        updateFace();
        fearFace.setLayoutX(780);
        fearFace.setLayoutY(25);

        stripPane.getChildren().add(fearFace);

        fearSlider.setMin(-5);
        fearSlider.setMax(5);

        fearSlider.setLayoutX(620);
        fearSlider.setLayoutY(55);

        fearSlider.setShowTickLabels(true);
        fearSlider.setShowTickMarks(true);

        fearSlider.setStyle("-fx-font-size: 20");

        fearSlider.setValue(game.getCurrentTurnGovernment().getFearRate());
        stripPane.getChildren().add(fearSlider);

        fearSlider.valueProperty().addListener((observableValue, number, t1) -> {
            fearSlider.setValue(t1.intValue());
            game.getCurrentTurnGovernment().setFearRate((int) fearSlider.getValue());
            fear.setText("Fear rate : " + game.getCurrentTurnGovernment().getFearRate());
            updateFace();
        });

    }


    private void setTaxes() {
        Label tax = new Label("Tax rate : " + game.getCurrentTurnGovernment().getTaxRate());

        tax.setLayoutX(500);
        tax.setLayoutY(15);
        tax.setStyle("-fx-font-size: 20");
        tax.setBackground(Background.fill(Color.WHITE));

        stripPane.getChildren().add(tax);

        taxFace = MultiMenuFunctions.getImageView("/Image/Popularity Face/green-face.png", 30);
        taxFace.setLayoutX(550);
        taxFace.setLayoutY(60);
        stripPane.getChildren().add(taxFace);

        tax.setOnMouseClicked(mouseEvent -> {
            TextInputDialog textInputDialog = new TextInputDialog("Enter Tax Rate : ");
            textInputDialog.setHeaderText("Tax Rate");
            textInputDialog.showAndWait();
            try {
                int taxRate = Integer.parseInt(textInputDialog.getEditor().getText());
                game.getCurrentTurnGovernment().setTaxRate(taxRate);
                tax.setText("Tax rate : " + game.getCurrentTurnGovernment().getTaxRate());
                updateFace();
            } catch (NumberFormatException ignored) {

            }

        });

    }


    private void setPopularity() {
        Label popularity = new Label("Popularity : " + game.getCurrentTurnGovernment().getPopularity());

        popularity.setLayoutX(40);
        popularity.setLayoutY(15);
        popularity.setStyle("-fx-font-size: 20");
        popularity.setBackground(Background.fill(Color.WHITE));

        stripPane.getChildren().add(popularity);
    }

    private void updateFace() {
        if (game.getCurrentTurnGovernment().getFearRate() > 0)
            fearFace.setImage(MultiMenuFunctions.getImageView("/Image/Popularity Face/green-face.png", 30).getImage());
        else if (game.getCurrentTurnGovernment().getFearRate() < 0)
            fearFace.setImage(MultiMenuFunctions.getImageView("/Image/Popularity Face/red-face.png", 30).getImage());
        else
            fearFace.setImage(MultiMenuFunctions.getImageView("/Image/Popularity Face/yellow-face.png", 30).getImage());

        if (game.getCurrentTurnGovernment().getTaxRate() > 0)
            taxFace.setImage(MultiMenuFunctions.getImageView("/Image/Popularity Face/green-face.png", 30).getImage());
        else if (game.getCurrentTurnGovernment().getTaxRate() < 0)
            taxFace.setImage(MultiMenuFunctions.getImageView("/Image/Popularity Face/red-face.png", 30).getImage());
        else
            taxFace.setImage(MultiMenuFunctions.getImageView("/Image/Popularity Face/yellow-face.png", 30).getImage());

        if (game.getCurrentTurnGovernment().getReligionPopularityRate() > 0)
            religionFace.setImage(MultiMenuFunctions.getImageView("/Image/Popularity Face/green-face.png", 30).getImage());
        else if (game.getCurrentTurnGovernment().getReligionPopularityRate() < 0)
            religionFace.setImage(MultiMenuFunctions.getImageView("/Image/Popularity Face/red-face.png", 30).getImage());
        else
            religionFace.setImage(MultiMenuFunctions.getImageView("/Image/Popularity Face/yellow-face.png", 30).getImage());

        if (game.getCurrentTurnGovernment().getFoodRate() > 0)
            foodFace.setImage(MultiMenuFunctions.getImageView("/Image/Popularity Face/green-face.png", 30).getImage());
        else if (game.getCurrentTurnGovernment().getFoodRate() < 0)
            foodFace.setImage(MultiMenuFunctions.getImageView("/Image/Popularity Face/red-face.png", 30).getImage());
        else
            foodFace.setImage(MultiMenuFunctions.getImageView("/Image/Popularity Face/yellow-face.png", 30).getImage());
    }


}
