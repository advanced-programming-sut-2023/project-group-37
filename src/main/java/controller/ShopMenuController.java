package controller;

import model.game.Government;
import model.game.Item;
import view.enums.Message;

import java.util.regex.Matcher;

public class ShopMenuController {
    private Government government;

    public void setGovernment(Government government) {
        this.government = government;
    }

    public boolean addItem(Item item, int amount) {
        switch (item.getCategory()) {
            case FOODS -> {return government.addToTargetRepository(government.getGranary(), item, amount);}
            case WEAPONS -> {return government.addToTargetRepository(government.getArmory(), item, amount);}
            case RESOURCES -> {return government.addToTargetRepository(government.getStockpile(), item, amount);}
        }
        return false;
    }

    public boolean removeItem(Item item, int amount) {
        switch (item.getCategory()) {
            case FOODS -> {return government.removeFromTargetRepository(government.getGranary(), item, amount);}
            case WEAPONS -> {return government.removeFromTargetRepository(government.getArmory(), item, amount);}
            case RESOURCES -> {return government.removeFromTargetRepository(government.getStockpile(), item, amount);}
        }
        return false;
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

        if (!addItem(item, amount))
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

        if (!removeItem(item, amount))
            return Message.NOT_ENOUGH_AMOUNT.toString();
        return Message.SOLD_SUCCESSFUL.toString();
    }
}
