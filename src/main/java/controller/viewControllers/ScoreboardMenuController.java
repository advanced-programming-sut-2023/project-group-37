package controller.viewControllers;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import model.user.User;

import java.util.ArrayList;
import java.util.Comparator;

public class ScoreboardMenuController {
    private VBox scoreboardUsers;
    private VBox scoreboardRanks;
    private VBox scoreboardHighscores;
    private VBox scoreboardAvatars;

    public void setScoreboardUsers(VBox scoreboardUsers) {
        this.scoreboardUsers = scoreboardUsers;
    }

    public void setScoreboardRanks(VBox scoreboardRanks) {
        this.scoreboardRanks = scoreboardRanks;
    }

    public void setScoreboardHighscores(VBox scoreboardHighscores) {
        this.scoreboardHighscores = scoreboardHighscores;
    }

    public void setScoreboardAvatars(VBox scoreboardAvatars) {
        this.scoreboardAvatars = scoreboardAvatars;
    }

    public void showScoreboard() {
        ArrayList<User> users = new ArrayList<>(User.getUsers());
        sortUsersByHighscore(users);
        int i = 1;
        for (User user : users) {
            addUserToScoreboard(user, i);
            i++;
        }
    }

    private void sortUsersByHighscore(ArrayList<User> allUsers) {
        allUsers.sort(Comparator.comparingInt(User::getHighScore));
    }

    private void addUserToScoreboard(User user, int i) {
        Label rank = new Label("" + i);
        rank.setPrefHeight(20);
        rank.setPrefWidth(scoreboardRanks.getPrefWidth());
        rank.setTextAlignment(TextAlignment.CENTER);
        rank.setAlignment(Pos.CENTER);
        rank.setStyle("-fx-font-size: 20");
        scoreboardRanks.getChildren().add(rank);

        Label username = new Label(user.getUsername());
        username.setPrefHeight(20);
        username.setPrefWidth(scoreboardUsers.getPrefWidth());
        username.setTextAlignment(TextAlignment.CENTER);
        username.setAlignment(Pos.CENTER);
        username.setStyle("-fx-font-size: 20");
        scoreboardUsers.getChildren().add(username);

        Label highscore = new Label("" + user.getHighScore());
        highscore.setPrefHeight(20);
        highscore.setPrefWidth(scoreboardHighscores.getPrefWidth());
        highscore.setTextAlignment(TextAlignment.CENTER);
        highscore.setAlignment(Pos.CENTER);
        highscore.setStyle("-fx-font-size: 20");
        scoreboardHighscores.getChildren().add(highscore);

        Circle avatar = new Circle(15);
        avatar.setFill(new ImagePattern(user.getAvatar()));
        scoreboardAvatars.getChildren().add(avatar);


        if (i == 1) {
            rank.setBackground(Background.fill(Color.GOLD));
            username.setBackground(Background.fill(Color.GOLD));
            highscore.setBackground(Background.fill(Color.GOLD));
        } else if (i == 2) {
            rank.setBackground(Background.fill(Color.SILVER));
            username.setBackground(Background.fill(Color.SILVER));
            highscore.setBackground(Background.fill(Color.SILVER));
        } else if (i == 3) {
            rank.setBackground(Background.fill(Color.BROWN));
            username.setBackground(Background.fill(Color.BROWN));
            highscore.setBackground(Background.fill(Color.BROWN));
        } else {
            rank.setBackground(Background.fill(Color.WHITE));
            username.setBackground(Background.fill(Color.WHITE));
            highscore.setBackground(Background.fill(Color.WHITE));
        }
    }
}
