package controller;

import model.buildings.BuildingType;
import model.user.User;
import view.enums.Message;

public class BuildingMenuController {
    private static User currentUser;
    private static BuildingType building;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void setBuilding(BuildingType building) {
        BuildingMenuController.building = building;
    }

    public Message repair() {
        return null;
    }
}
