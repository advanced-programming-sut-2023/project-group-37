import view.RegisterLoginMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RegisterLoginMenu registerLoginMenu = new RegisterLoginMenu(new Scanner(System.in));
        registerLoginMenu.run();
    }
}
