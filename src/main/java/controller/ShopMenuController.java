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
        int amount = Integer.parseInt(matcher.group("itemAmount"));
        Item item = Item.getItemByName(matcher.group("itemName"));

        if (item == null)
            return Message.INVALID_ITEM_NAME.toString();

        if (amount < 1)
            return Message.INVALID_AMOUNT.toString();

        if(government.getGold() < amount * item.getBuyCost())
            return Message.NOT_ENOUGH_GOLD.toString();

        if (!government.addItem(item, amount))
            return Message.NOT_ENOUGH_SPACE.toString();

        return Message.BOUGHT_SUCCESSFUL.toString();

    }

    public String sell(Matcher matcher){
        int amount = Integer.parseInt(matcher.group("itemAmount"));
        Item item = Item.getItemByName(matcher.group("ItemName"));

        if (item == null)
            return Message.INVALID_ITEM_NAME.toString();

        if (amount < 1)
            return Message.INVALID_AMOUNT.toString();

        if (!government.removeItem(item, amount))
            return Message.NOT_ENOUGH_AMOUNT.toString();
        return Message.SOLD_SUCCESSFUL.toString();
    }
}
