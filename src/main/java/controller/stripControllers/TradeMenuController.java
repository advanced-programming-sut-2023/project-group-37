package controller.stripControllers;

import controller.MultiMenuFunctions;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.game.*;
import view.enums.PopUp;

public class TradeMenuController {
    private final Pane stripPane;
    private final int sizeOfImage;
    private static Game game;
    private Label amount;
    private Label forTrade;
    private int tradeAmount;
    private final Label donate = new Label("Donate");
    private final Label request = new Label("Request");

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

        addLabelsAndButtons(item, selectedGovernment);
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

    private void addLabelsAndButtons(Item item, Government selectedGovernment) {
        tradeAmount = 0;

        initializeLabels(item, selectedGovernment);

        ImageView plus = MultiMenuFunctions.getImageView("/Image/Button/plus.jpg", 40);
        plus.setLayoutX(400);
        plus.setLayoutY(15);

        stripPane.getChildren().add(plus);
        plus.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                increase(item);
            }
        });

        ImageView minus = MultiMenuFunctions.getImageView("/Image/Button/minus.jpg", 40);
        minus.setLayoutX(450);
        minus.setLayoutY(15);

        stripPane.getChildren().add(minus);

        minus.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                decrease(item);
            }
        });
    }

    private void increase(Item item) {
        tradeAmount++;
        forTrade.setText("Trade : " + tradeAmount);
    }

    private void decrease(Item item) {
        if (tradeAmount == 0) return;
        tradeAmount--;
        forTrade.setText("Trade : " + tradeAmount);
    }

    private void initializeLabels(Item item, Government selectedGovernment) {
        donate.setLayoutX(520);
        donate.setLayoutY(15);
        donate.setBackground(Background.fill(Color.GREEN));
        donate.setStyle("-fx-font-size: 20");
        stripPane.getChildren().add(donate);

        donate.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                donate(item, selectedGovernment);
            }
        });

        request.setLayoutX(520);
        request.setLayoutY(50);
        request.setBackground(Background.fill(Color.BROWN));
        request.setStyle("-fx-font-size: 20");
        request.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                request(item, selectedGovernment);
            }
        });
        stripPane.getChildren().add(request);

        amount = new Label("You have : " + game.getCurrentTurnGovernment().getItemAmount(item));
        amount.setLayoutX(230);
        amount.setLayoutY(30);
        amount.setStyle("-fx-font-size: 20");
        stripPane.getChildren().add(amount);

        forTrade = new Label("Trade : " + tradeAmount);
        forTrade.setLayoutX(230);
        forTrade.setLayoutY(5);
        forTrade.setStyle("-fx-font-size: 20");
        stripPane.getChildren().add(forTrade);
    }

    private void donate(Item item, Government selectedGovernment) {
        if (game.getCurrentTurnGovernment().getItemAmount(item) < tradeAmount) {
            PopUp.NOT_ENOUGH.show();
            return;
        }

        TextInputDialog textInputDialog = new TextInputDialog("Enter your message : ");
        textInputDialog.setHeaderText("Donate");
        textInputDialog.show();
        String message = textInputDialog.getContentText();

        new TradeRequest(item, tradeAmount, 0, message, game.getCurrentTurnGovernment(),
                selectedGovernment, game.getIndex());

    }

    private void request(Item item, Government selectedGovernment) {
        TextInputDialog price = new TextInputDialog("Enter your price : ");
        price.setHeaderText("Request");
        price.show();

        String message = "";

        if (!price.isShowing()) {
            TextInputDialog textInputDialog = new TextInputDialog("Enter your message : ");
            textInputDialog.setHeaderText("Request");
            textInputDialog.show();
            message = textInputDialog.getContentText();
        }
        new TradeRequest(item, tradeAmount, 10, message, game.getCurrentTurnGovernment(),
                selectedGovernment, game.getIndex());
    }
}
