module Stronghold {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.security.crypto;
    requires com.google.gson;

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
    opens model.user to com.google.gson;
    exports controller.viewControllers;
    opens controller.viewControllers to javafx.fxml;
    exports view.animation;
    exports e;
    opens e to javafx.fxml;
    exports connection;
    opens connection to javafx.fxml;
}