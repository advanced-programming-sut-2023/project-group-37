package controller;

import model.Game;
import view.enums.messages.GameMenuMessages;

import javax.swing.plaf.InsetsUIResource;
import java.util.regex.Matcher;

public class GameMenuController {
    private Game game;
    
    public GameMenuController(Game game) {
        this.game = game;
    }

    public GameMenuMessages enterMapMenu(Matcher matcher){
        return null;
    }
    public GameMenuMessages ShowPopularityFactors(Matcher matcher){
        return null;
    }
    public String showPopularity(Matcher matcher){
        return null;
    }
    public GameMenuMessages showFoodList(Matcher matcher){
        return null;
    }
    public String setFoodRate(Matcher matcher){
        return null;
    }
    public String ShowFoodRate(Matcher matcher){
        return null;
    }
    public String setTaxRate(Matcher matcher){
        return null;
    }
    public String ShowTaxRate(Matcher matcher){
        return null;
    }
    public String setFearRate(Matcher matcher){
        return null;
    }
    public String ShowFearRate(Matcher matcher){
        return null;
    }
    public String dropBuilding(Matcher matcher){
        return null;
    }
    public String selectBuilding(Matcher matcher){
        return null;
    }
    public String createUnit(Matcher matcher){
        return null;
    }
    public String repair(Matcher matcher){
        return null;
    }
    public String selectUnit(Matcher matcher){
        return null;
    }
    public String setTexture(Matcher matcher){
        return null;
    }
    public String clearBlock(Matcher matcher){
        return null;
    }
    public String dropTree(Matcher matcher){
        return null;
    }
    public String dropRock(Matcher matcher){
        return null;
    }

}
