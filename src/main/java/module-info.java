module Stronghold {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.security.crypto;
    requires gson;

    exports view;
    exports model.buildings;
    exports model.game;
    exports model.people;
    exports model.utils;

    exports controller;
    opens controller to javafx.fxml;
    exports view.enums;
    opens view.enums to javafx.fxml;
    exports view.menus;
    opens view.menus to javafx.fxml;
    exports model.user;
    opens model.user to gson;
    exports controller.viewControllers;
    opens controller.viewControllers to javafx.fxml;
}