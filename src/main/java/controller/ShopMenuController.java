package controller;

import model.game.Government;
import model.game.Item;
import view.enums.Message;

import java.util.regex.Matcher;

public class ShopMenuController {
    private static final ShopMenuController shopMenuController;
    private Government government;

    static {
        shopMenuController = new ShopMenuController();
    }

    private ShopMenuController() {

    }

    public static ShopMenuController getInstance() {
        return shopMenuController;
    }

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
        Item item = Item.getItemByName(MultiMenuFunctions.deleteQuotations(matcher.group("itemName")));

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
        Item item = Item.getItemByName(MultiMenuFunctions.deleteQuotations(matcher.group("itemName")));

        if (item == null)
            return Message.INVALID_ITEM_NAME.toString();

        if (amount < 1)
            return Message.INVALID_AMOUNT.toString();

        if (!removeItem(item, amount))
            return Message.NOT_ENOUGH_AMOUNT.toString();
        return Message.SOLD_SUCCESSFUL.toString();
    }

    public String showAllItems(){
        StringBuilder list = new StringBuilder("Here's all the things you can sell/buy:\n");
        for (Item item : Item.getAllItems())
            list.append(item.getName()).append(":\t").append(item.getBuyCost()).append("\tsell:\t")
                    .append(item.getSellCost()).append("\n");
        return list.toString().trim();
    }
}
