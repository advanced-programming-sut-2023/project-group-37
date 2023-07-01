package controller.stripControllers;

import controller.MultiMenuFunctions;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.game.*;
import view.enums.Message;
import view.enums.PopUp;

import java.util.ArrayList;

public class TradeMenuController {
    private static TradeMenuController tradeMenuController;
    private final Pane stripPane;
    private static Game game;
    private Label amount;
    private Label forTrade;
    private int tradeAmount;
    private final Label donate = new Label("Donate");
    private final Label request = new Label("Request");

    public TradeMenuController(Pane stripPane) {
        this.stripPane = stripPane;
        tradeMenuController = this;

    }

    public static TradeMenuController getInstance() {
        return tradeMenuController;
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
        l.setBackground(Background.fill(Color.SILVER));
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
                label.setBackground(Background.fill(Color.GOLD));
                label.setTextFill(Color.BLUE);
                stripPane.getChildren().add(label);
                label.setOnMouseClicked(mouseEvent -> gotoGovernment(government));
                i++;
            }
        }

        Label label = new Label("Show Trades");
        label.setStyle("-fx-font-size: 20");
        label.setBackground(Background.fill(Color.GRAY));
        label.setLayoutX(20);
        label.setLayoutY(60);

        label.setOnMouseClicked((MouseEvent mouseEvent) -> showTrades());

        stripPane.getChildren().add(label);

    }

    private void showTrades() {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();

        Label sent = new Label("Sent");
        Label received = new Label("Received");
        Label newTrades = new Label("New Trades");

        sent.setStyle("-fx-font-size: 20");
        sent.setBackground(Background.fill(Color.GREEN));
        sent.setLayoutX(150);
        sent.setLayoutY(15);
        sent.setPrefWidth(received.getPrefWidth());

        sent.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                showSentTrades();
            }
        });

        stripPane.getChildren().add(sent);

        received.setStyle("-fx-font-size: 20");
        received.setBackground(Background.fill(Color.GREEN));
        received.setLayoutX(250);
        received.setLayoutY(15);

        received.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                showReceivedTrades();
            }
        });

        stripPane.getChildren().add(received);

        newTrades.setStyle("-fx-font-size: 20");
        newTrades.setBackground(Background.fill(Color.GREEN));
        newTrades.setLayoutX(350);
        newTrades.setLayoutY(15);
        newTrades.setPrefWidth(received.getPrefWidth());

        newTrades.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                showNewTrades();
            }
        });

        stripPane.getChildren().add(newTrades);

    }

    private void showSentTrades() {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();

        Label trades = new Label(createSentTrades());
        trades.setLayoutX(10);
        trades.setLayoutY(10);
        trades.setStyle("-fx-font-size: 15");

        stripPane.getChildren().add(trades);

    }

    private void showReceivedTrades() {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();

        Label trades = new Label(createReceivedHistory());
        trades.setLayoutX(10);
        trades.setLayoutY(10);
        trades.setStyle("-fx-font-size: 15");

        stripPane.getChildren().add(trades);

    }

    private void showNewTrades() {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();

        Label trades = new Label(createNewTrades());
        trades.setLayoutX(10);
        trades.setLayoutY(10);
        trades.setStyle("-fx-font-size: 15");

        stripPane.getChildren().add(trades);

        Label acceptButton = new Label("Accept");
        acceptButton.setLayoutX(600);
        acceptButton.setLayoutY(10);
        acceptButton.setBackground(Background.fill(Color.GREEN));
        acceptButton.setStyle("-fx-font-size: 20");

        acceptButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                acceptTrade();
            }
        });

        stripPane.getChildren().add(acceptButton);

    }

    private void acceptTrade() {
        TextInputDialog textInputDialog = new TextInputDialog("Enter id : ");
        textInputDialog.setHeaderText("Accept Trade");
        textInputDialog.showAndWait();
        int id = 0;
        try {
            id = Integer.parseInt(textInputDialog.getEditor().getText());
        } catch (NumberFormatException ignored) {
        }

        final String[] reply = new String[1];

        int finalId = id;
        TextInputDialog t = new TextInputDialog("Enter your Reply : ");
        t.setHeaderText("Trade Reply");
        t.showAndWait();
        reply[0] = t.getEditor().getText();

        ArrayList<TradeRequest> requests = TradeRequest.getRequestsByReceiver(game.getCurrentTurnGovernment());
        if (requests.size() < finalId || finalId < 1) {
            PopUp.INVALID_ID.show();
            return;
        }
        TradeRequest request = requests.get(finalId - 1);
        if (!request.doTrade(reply[0])) {
            PopUp.TRADE_FAILED.show();
            return;
        }
        new Alert(Alert.AlertType.INFORMATION, Message.TRADE_SUCCESS.toString()).show();

    }

    public String createNewTrades() {
        StringBuilder newTrades = new StringBuilder();
        newTrades.append("Here's the trades you have received in last turn:\n");
        int id = 1;
        for (TradeRequest request : TradeRequest.getRequestsByReceiver(game.getCurrentTurnGovernment())) {
            if ((request.getTime() == (game.getIndex() - 1)) && !request.isDone()) {
                newTrades.append("id: ").append(id).append(", Sender: ").append(request.getSender().getUser().getUsername())
                        .append(", Type: ").append(request.getItem().getName()).append(", Amount: ")
                        .append(request.getItemAmount())
                        .append(", Price: ").append(request.getPrice()).append("\n").append("Their Message: ")
                        .append(request.getSenderMessage()).append("\n");
            }
            id++;
        }

        return newTrades.toString().trim();
    }

    private void gotoGovernment(Government selectedGovernment) {
        if (this.stripPane.getChildren().size() > 0)
            this.stripPane.getChildren().subList(0, this.stripPane.getChildren().size()).clear();

        int i = 0;
        for (Item item : Item.getAllItems()) {
            if (!item.getCategory().equals(ItemCategory.ANIMALS)) {
                ImageView imageView = MultiMenuFunctions.getImageView(item.getImageUrl(), 40);
                imageView.setLayoutX(50 + ((i % (Item.getAllItems().size() / 2.0)) * 90));
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
        back.setOnMouseClicked(mouseEvent -> {
            switch (flag) {
                case 1 -> run();
                case 2 -> gotoGovernment(selectedGovernment);
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
        plus.setOnMouseClicked(mouseEvent -> increase());

        ImageView minus = MultiMenuFunctions.getImageView("/Image/Button/minus.jpg", 40);
        minus.setLayoutX(450);
        minus.setLayoutY(15);

        stripPane.getChildren().add(minus);

        minus.setOnMouseClicked(mouseEvent -> decrease());
    }

    private void increase() {
        tradeAmount++;
        forTrade.setText("Trade : " + tradeAmount);
    }

    private void decrease() {
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

        donate.setOnMouseClicked(mouseEvent -> donate(item, selectedGovernment));

        request.setLayoutX(520);
        request.setLayoutY(50);
        request.setBackground(Background.fill(Color.BROWN));
        request.setStyle("-fx-font-size: 20");
        request.setOnMouseClicked(mouseEvent -> request(item, selectedGovernment));
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
        textInputDialog.showAndWait();
        String message = textInputDialog.getEditor().getText();

        new TradeRequest(item, tradeAmount, 0, message, game.getCurrentTurnGovernment(),
                selectedGovernment, game.getIndex());

    }

    private void request(Item item, Government selectedGovernment) {
        TextInputDialog price = new TextInputDialog("Enter your price : ");
        price.setHeaderText("Request");
        price.showAndWait();


        int priceAmount = Integer.parseInt(price.getEditor().getText());
        final String[] message = {""};

        TextInputDialog textInputDialog = new TextInputDialog("Enter your message : ");
        textInputDialog.setHeaderText("Request");
        textInputDialog.showAndWait();
        message[0] = textInputDialog.getEditor().getText();

        new TradeRequest(item, tradeAmount, priceAmount, message[0], game.getCurrentTurnGovernment(),
                selectedGovernment, game.getIndex());


    }

    private String createReceivedHistory() {
        StringBuilder message = new StringBuilder();
        message.append("Here's the trades you received and replied:\n");
        for (TradeRequest request : TradeRequest.getRequestsByReceiver(game.getCurrentTurnGovernment())) {
            if (request.isDone()) {
                message.append("Sender: ").append(request.getSender().getUser().getUsername())
                        .append(", Type: ").append(request.getItem().getName()).append(", Amount: ")
                        .append(request.getItemAmount())
                        .append(", Price: ").append(request.getPrice()).append("\n").append("Their Message: ")
                        .append(request.getSenderMessage()).append("You replied: ")
                        .append(request.getReceiverMessage()).append("\n");
            }

        }

        return message.toString().trim();
    }

    private String createSentTrades() {
        StringBuilder message = new StringBuilder();
        message.append("Here's the trades you've sent:\n");
        for (TradeRequest request : TradeRequest.getRequestsBySender(game.getCurrentTurnGovernment())) {
            message.append("Receiver: ").append(request.getReceiver().getUser().getUsername())
                    .append(", Type: ").append(request.getItem().getName()).append(", Amount: ")
                    .append(request.getItemAmount())
                    .append(", Price: ").append(request.getPrice()).append("\n").append("Your Message: ")
                    .append(request.getSenderMessage()).append("\n");

            if (request.isDone())
                message.append("--isDone ").append("Their reply: ").append(request.getReceiverMessage()).append("\n");
        }
        return message.toString().trim();
    }
}
