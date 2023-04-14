package controller;

import view.menus.GameMenu;
import view.menus.ProfileMenu;
import view.menus.RegisterLoginMenu;

import java.util.Scanner;

public class Controller {
    private static final Scanner scanner = new Scanner(System.in);
    private final ProfileMenu profileMenu = new ProfileMenu(scanner);
    private final RegisterLoginMenu registerLoginMenu = new RegisterLoginMenu(scanner);


    public void run(){

    }
}
