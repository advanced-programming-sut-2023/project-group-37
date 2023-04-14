package controller;

import model.Game;
import view.enums.messages.GameMenuMessages;
import java.util.regex.Matcher;

public class GameMenuController {
    private Game game;

    public GameMenuController(Game game) {
        this.game = game;
    }

    public GameMenuMessages enterMapMenu(Matcher matcher){
        return null;
    }
}
