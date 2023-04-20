package controller;

import view.menus.GameMenu;
import view.menus.ProfileMenu;
import view.menus.RegisterMenu;

import java.util.Scanner;

public class Controller {
    private static final Scanner scanner = new Scanner(System.in);
    private final ProfileMenu profileMenu = new ProfileMenu(scanner);
    private final RegisterMenu registerMenu = new RegisterMenu(scanner);


    public void run(){
        registerMenu.run();
    }

    public static String deleteQuotations(String string) {

        if (string == null)
            return "";

        StringBuilder stringBuilder = new StringBuilder(string);
        if (string.matches("\".+\"")) {

            stringBuilder.deleteCharAt(0);
            stringBuilder.deleteCharAt(stringBuilder.length()-1);

            string = stringBuilder.toString();
        }
        return string;
    }
}