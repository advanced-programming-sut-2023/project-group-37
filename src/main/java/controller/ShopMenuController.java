package controller;

import model.game.Government;
import model.game.Item;
import view.enums.Message;

import java.util.regex.Matcher;

public class ShopMenuController {
    private static Government government;

    public static void setGovernment(Government government) {
        ShopMenuController.government = government;
    }

    public String showPriceList(){
        return null;
    }
    public String buy(Matcher matcher){
        int Amount = Integer.parseInt(matcher.group("itemAmount"));
        Item item = Item.getItemByName(matcher.group("itemName"));

        if (item == null)
            return Message.INVALID_ITEM_NAME.toString();

        int cost = Amount * item.getBuyCost();

    }
    public String sell(Matcher matcher){
        return null;
    }
}
