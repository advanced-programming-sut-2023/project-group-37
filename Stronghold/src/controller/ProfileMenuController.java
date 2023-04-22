package controller;

import model.Model;
import model.User;

public class ProfileMenuController {
    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
