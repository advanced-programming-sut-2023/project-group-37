package controller;

import model.User;

import java.util.regex.Matcher;

public class ShopMenuController {
    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public String showPriceList(){
        return null;
    }
    public String buy(Matcher matcher){
        return null;
    }
    public String sell(Matcher matcher){
        return null;
    }
}
