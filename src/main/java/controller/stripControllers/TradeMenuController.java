package controller.stripControllers;

import controller.MultiMenuFunctions;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.game.Game;
import model.game.Government;
import model.game.Item;
import model.game.ItemCategory;

public class TradeMenuController {
    private final Pane stripPane;
    private final int sizeOfImage;
    private static Game game;
    private Label amount;
    private Label forTrade;

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

    private void gotoGovernment(Government selectedGovernment) {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();

        int i = 0;
        for (Item item : Item.getAllItems()) {
            if (!item.getCategory().equals(ItemCategory.ANIMALS)) {
                ImageView imageView = MultiMenuFunctions.getImageView(item.getImageUrl(), 40);
                imageView.setLayoutX(50 + ((i % (Item.getAllItems().size() / 2)) * 90));
                if (i < Item.getAllItems().size() / 2) imageView.setLayoutY(10);
                else imageView.setLayoutY(60);

                imageView.setOnMouseClicked(mouseEvent -> goToItem(item, selectedGovernment));

                stripPane.getChildren().add(imageView);
                i++;
            }
        }

        addBackButton(1, null);
    }

    private void goToItem(Item item, Government selectedGovernment) {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();


        ImageView imageView = MultiMenuFunctions.getImageView(item.getImageUrl(), 65);

        imageView.setLayoutX(130);
        imageView.setLayoutY(15);

        stripPane.getChildren().add(imageView);

        addLabelsAndButtons(item);
        addBackButton(2, selectedGovernment);

    }

    private void addBackButton(int flag, Government selectedGovernment) {
        ImageView back = MultiMenuFunctions.getImageView("/Image/Button/back1.png", 30);
        back.setLayoutX(10);
        back.setLayoutY(60);

        stripPane.getChildren().add(back);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switch (flag) {
                    case 1 -> run();
                    case 2 -> gotoGovernment(selectedGovernment);
                }
            }
        });
    }

    private void addLabelsAndButtons(Item item) {
        amount = new Label(Integer.toString(game.getCurrentTurnGovernment().getItemAmount(item)));
        amount.setLayoutX(230);
        amount.setLayoutY(15);


    }

}
