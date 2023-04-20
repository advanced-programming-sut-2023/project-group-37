package view.menus;

import controller.ProfileMenuController;
import view.enums.Results;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {

    private final ProfileMenuController controller = new ProfileMenuController();
    private final Scanner scanner;

    public ProfileMenu(Scanner scanner){
        this.scanner = scanner;
    }

    public Results run(Scanner scanner){
        //TODO: fill
        String command;
        Matcher matcher;
        return null;
    }
}