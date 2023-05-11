import controller.Controller;
import model.game.GenerateMap;
import model.game.Map;

public class Main {
    public static void main(String[] args) { new Controller().run();
        System.out.println(Map.getMaps().size());
        System.out.println(Map.getMaps().get(0).getName());
    }
}