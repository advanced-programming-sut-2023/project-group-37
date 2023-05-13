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
        return government.buyItem(item, amount);
    }

    public boolean removeItem(Item item, int amount) {
        return government.sellItem(item, amount);
    }

    public String buy(Matcher matcher) {
        int amount = Integer.parseInt(matcher.group("itemAmount"));
        Item item = Item.getItemByName(matcher.group("itemName"));

        if (item == null)
            return Message.INVALID_ITEM_NAME.toString();

        if (amount < 1)
            return Message.INVALID_AMOUNT.toString();

        if (government.getGold() < amount * item.getBuyCost())
            return Message.NOT_ENOUGH_GOLD.toString();

        if (!addItem(item, amount))
            return Message.NOT_ENOUGH_SPACE.toString();

        return Message.BOUGHT_SUCCESSFUL.toString();

    }

    public String sell(Matcher matcher) {
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

    public String showAllItems(){
        StringBuilder list = new StringBuilder("Here's all things you can buy :");
        for (Item item : Item.getAllItems()) {
            list.append(item.getName()).append("price :").append(item.getBuyCost()).append("(sell price :")
                    .append(item.getSellCost()).append(")\n");
        }
        return list.toString().trim();
    }
}
