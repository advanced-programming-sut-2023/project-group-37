package view;

import connection.Connection;
import controller.AppController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        new Connection("localhost", 8080);
        new AppController(stage).loadApp();
    }
}