package controller;

import model.user.User;

public class ProfileMenuController {
    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
