module Stronghold {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.security.crypto;
    requires com.google.gson;

    exports connection;
    exports connection.packet;
    exports view;
    exports model.buildings;
    exports model.game;
    exports model.people;
    exports model.utils;
    exports model.chat;

    exports controller;
    opens controller to javafx.fxml;
    exports view.enums;
    exports view.menus;
    opens view.menus to javafx.fxml;
    exports model.user;
    opens model.user to com.google.gson;
    exports controller.viewControllers;
    opens controller.viewControllers to javafx.fxml;
    exports view.animation;
    opens connection to com.google.gson;
    opens connection.packet to com.google.gson;
    opens view.enums to com.google.gson, javafx.fxml;
    opens model.chat to com.google.gson;
}