package view.menus;

import controller.AppController;
import controller.viewControllers.MainMenuController;
import controller.MultiMenuFunctions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.user.User;
import view.enums.Result;
import view.enums.Command;
import view.enums.Message;

import java.net.URL;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu extends Application {
    private final AppController appController;
    private final MainMenuController mainMenuController;
    private final Scanner scanner;

    public MainMenu() {
        this.appController = AppController.getInstance();
        this.scanner = new Scanner(System.in);
        this.mainMenuController = MainMenuController.getInstance();
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/MainMenu.fxml");
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(url));

        MultiMenuFunctions.setBackground(anchorPane, "main-menu-background.jpg");

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    public Result run() {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if (command.matches(Command.ENTER_PROFILE_MENU.toString())) {
                System.out.println(this.mainMenuController.enterProfileMenu());
                return Result.ENTER_PROFILE_MENU;
            } else if ((matcher = Command.START_GAME.getMatcher(command)) != null) {
                if (startGame(matcher))
                    return Result.ENTER_GAME_MENU;
            } else if (command.matches(Command.EXIT.toString())) {
                return Result.EXIT;
            } else
                System.out.println(Message.INVALID_COMMAND);
        }
    }

    private boolean startGame(Matcher matcher) {
        String[] parts = MultiMenuFunctions.deleteQuotations(matcher.group("users")).trim().split("\\s*&\\s*");
        String[] usernames = new String[parts.length];
        String[] numbers = new String[parts.length];
        String[] split;

        try {
            for (int index = 0; index < parts.length; index++) {
                split = parts[index].split("\\s+");
                usernames[index] = split[0];
                numbers[index] = split[1];
            }
        } catch (Exception ex) {
            System.out.println(Message.TERRITORY_NOT_ASSIGNED);
            return false;
        }

        String message = this.mainMenuController.startGame(usernames, numbers, matcher.group("turns"),
                MultiMenuFunctions.deleteQuotations(matcher.group("mapName")));

        System.out.println(message);
        return message.equals(Message.GAME_STARTED.toString());
    }

    public void enterProfileMenu() throws Exception {
        appController.runMenu(Result.ENTER_PROFILE_MENU);
    }

    public void exit() {
        User.updateDatabase();
        System.exit(0);
    }

    public void logout() throws Exception {
        this.mainMenuController.logout();
        appController.runMenu(Result.ENTER_LOGIN_MENU);
    }

    public void startGame() throws Exception {
        appController.runMenu(Result.ENTER_START_GAME_MENU);
    }
}