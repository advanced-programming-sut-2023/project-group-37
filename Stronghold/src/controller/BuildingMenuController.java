package controller;

import model.buildings.BuildingType;
import model.user.User;
import view.enums.messages.BuildingMenuMessages;

public class BuildingMenuController {
    private static User currentUser;
    private static BuildingType building;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void setBuilding(BuildingType building) {
        BuildingMenuController.building = building;
    }

    public BuildingMenuMessages repair() {
        return null;
    }
}
