package view;

import controller.AppController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        new AppController(stage).loadApp();
    }
}