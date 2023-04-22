package controller;

import model.Model;
import model.User;
import view.enums.messages.MainMenuMessages;
import view.menus.MainMenu;

import java.util.regex.Matcher;

public class MainMenuController {
    private User currentUser;

    public void setCurrentUser() {
        this.currentUser = Model.getCurrentUser();
    }

    public MainMenuMessages enterProfileMenu() {
        return null;
    }

    public MainMenuMessages startGame(Matcher matcher) {
        return null;
    }

    public MainMenuMessages logout() {
        return MainMenuMessages.LOGOUT_SUCCESSFUL;
    }
}
