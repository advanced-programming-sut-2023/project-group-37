package controller;

import model.User;
import model.buildings.enums.Buildings;
import view.enums.messages.BuildingMenuMessages;

public class BuildingMenuController {
    private static User currentUser;
    private static Buildings building;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void setBuilding(Buildings building) {
        BuildingMenuController.building = building;
    }

    public BuildingMenuMessages repair() {
        return null;
    }
}
