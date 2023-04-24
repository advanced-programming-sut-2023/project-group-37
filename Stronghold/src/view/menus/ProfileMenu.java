package view.menus;

import controller.ProfileMenuController;
import view.enums.Results;
import view.enums.commands.ProfileMenuCommands;
import view.enums.messages.ProfileMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {

    private final ProfileMenuController controller = new ProfileMenuController();
    private final Scanner scanner;

    public ProfileMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public Results run() {
        String command;
        Matcher matcher;
        while (true) {
            command = scanner.nextLine();
            if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_USERNAME)) != null) {
                System.out.println(controller.changeUsername(matcher));
            } else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_NICKNAME)) != null) {
                System.out.println(controller.changeNickName(matcher));
            } else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_PASSWORD)) != null) {
                changePassword(matcher);
            } else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_EMAIL)) != null) {
                System.out.println(controller.changeEmail(matcher));
            } else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_SLOGAN)) != null) {
                System.out.println(controller.changeSlogan(matcher));
            } else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.REMOVE_SLOGAN)) != null) {
                System.out.println(controller.removeSlogan(matcher));
            } else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_HIGHSCORE) != null) {
                System.out.println(controller.showScore());
            } else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_RANK) != null) {
                System.out.println(controller.showRank());
            } else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_SLOGAN) != null) {
                System.out.println(controller.showSlogan());
            } else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_PROFILE) != null) {
                System.out.println(controller.showProfile());
            } else if (command.matches("\\s*enter\\s+main\\s+menu\\s*")) {
                return Results.ENTER_MAIN_MENU;
            } else {
                System.out.println("Invalid Command!");
            }
        }
    }

    private void changePassword(Matcher matcher) {
        ProfileMenuMessages message = controller.changePassword(matcher);
        System.out.println(message);

        if(message.equals(ProfileMenuMessages.ENTER_PASSWORD_AGAIN)){
            String password;
            flag : while (true){
                password = scanner.nextLine();
                message = controller.checkPasswordAgain(password);
                System.out.println(message);

                switch (message) {
                    case CHANGE_PASSWORD , CANCEL -> {break flag;}
                }
            }
        }
    }
}
