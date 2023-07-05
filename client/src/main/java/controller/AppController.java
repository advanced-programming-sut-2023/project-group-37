package controller;

import connection.packet.PopUpPacket;
import connection.packet.registration.LoginPacket;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.user.User;
import view.enums.Message;
import view.enums.Result;
import view.menus.*;

public class AppController {
    private static AppController appController;
    public static LoginPacket stayLoggedInPacket;
    private final Stage stage;
    private final LoginMenu loginMenu;
    private final ForgotMenu forgotMenu;
    private final RegisterMenu registerMenu;
    private final MainMenu mainMenu;
    private final ProfileMenu1 profileMenu;
    private final StartGameMenu startGameMenu;
    private final GameMenu gameMenu;
    private final ChangeMenu changeMenu;
    private final ChangePasswordMenu changePasswordMenu;
    private final AvatarMenu avatarMenu;
    private boolean isStayLoggedIn;

    public AppController(Stage stage) {
        this.stage = stage;
        this.stage.setResizable(false);

        if (appController == null)
            appController = this;

        this.loginMenu = new LoginMenu();
        this.forgotMenu = new ForgotMenu();
        this.registerMenu = new RegisterMenu();
        this.mainMenu = new MainMenu();
        this.profileMenu = new ProfileMenu1();
        this.startGameMenu = new StartGameMenu();
        this.gameMenu = new GameMenu();
        this.changeMenu = new ChangeMenu();
        this.changePasswordMenu = new ChangePasswordMenu();
        this.avatarMenu = new AvatarMenu();
    }

    public static AppController getInstance() {
        return appController;
    }

    public void loadApp() throws Exception {
        User loggedInUser = User.loadStayLoggedIn();

        if (loggedInUser != null) {
            stayLoggedInPacket = new LoginPacket(
                    loggedInUser.getUsername(), loggedInUser.getHashedPassword());
            this.isStayLoggedIn = true;
            return;
        }
        this.isStayLoggedIn = false;
        stayLoggedInPacket = null;
        this.loginMenu.start(this.stage);
    }

    public boolean isStayLoggedIn() {
        return this.isStayLoggedIn;
    }

    public void runMenu(Result result) throws Exception {

        switch (result) {
            case ENTER_LOGIN_MENU -> this.loginMenu.start(this.stage);
            case ENTER_FORGOT_MENU -> this.forgotMenu.start(this.stage);
            case ENTER_REGISTER_MENU -> this.registerMenu.start(this.stage);
            case ENTER_PROFILE_MENU -> this.profileMenu.start(this.stage);
            case ENTER_MAIN_MENU -> this.mainMenu.start(this.stage);
            case ENTER_START_GAME_MENU -> this.startGameMenu.start(this.stage);
            case ENTER_GAME_MENU -> this.gameMenu.start(this.stage);
            case ENTER_CHANGE_MENU -> this.changeMenu.start(this.stage);
            case ENTER_CHANGE_PASSWORD_MENU -> this.changePasswordMenu.start(this.stage);
            case ENTER_AVATAR_MENU -> this.avatarMenu.start(this.stage);

            // todo : how to show game parts menu ??
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void handleAlert(PopUpPacket popUpPacket) {
        Platform.runLater(() -> {
            switch (popUpPacket.getMessage()) {
                case LOGIN_SUCCESSFUL, REMOVE_LOBBY, YOU_WIN -> {
                    try {
                        new ChatMenu().start(new Stage());
                        this.runMenu(Result.ENTER_MAIN_MENU);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                case REGISTER_SUCCESSFUL -> {
                    try {
                        this.runMenu(Result.ENTER_LOGIN_MENU);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

            }
            if (popUpPacket.isError())
                new Alert(Alert.AlertType.ERROR, popUpPacket.getMessage().toString()).show();
            else if (popUpPacket.getMessage() != Message.LOGIN_SUCCESSFUL)
                new Alert(Alert.AlertType.INFORMATION, popUpPacket.getMessage().toString()).show();
        });
    }

    public void createLobby() {
        Platform.runLater(() -> {
            try {
                new LobbyMenu().start(this.stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}